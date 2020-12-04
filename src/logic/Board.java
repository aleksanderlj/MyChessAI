package logic;

import AI.Evaluation;
import com.sun.prism.shader.AlphaOne_Color_AlphaTest_Loader;
import logic.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

    Piece[][] board;
    List<Move> moveHistory = new ArrayList<>(); // TODO Make linkedlist?
    int maxBlackPieces = 64; // I dont know if these help a whole lot honestly
    int maxWhitePieces = 64;

    public Board() {
        this.board = new Piece[8][8];
    }

    public Board(Board boardObject) {
        Piece[][] newBoard = new Piece[8][8];
        for (int x = 0; x < newBoard.length; x++) {
            for (int y = 0; y < newBoard.length; y++) {
                newBoard[x][y] = Piece.clone(boardObject.board[x][y]);
                if (newBoard[x][y] != null){
                    if(newBoard[x][y].getAllegiance() == Allegiance.WHITE){
                        maxWhitePieces++;
                    } else {
                        maxBlackPieces++;
                    }
                }
            }
        }

        this.board = newBoard;
        this.moveHistory = new ArrayList<>(moveHistory);
    }

    public void initialize() {
        List<Piece> arr = new ArrayList<>();

        for (int n = 0; n < 8; n++) {
            arr.add(new Pawn(n, 1, Allegiance.WHITE));
            arr.add(new Pawn(n, 6, Allegiance.BLACK));
        }

        // White
        arr.add(new Rook(0, 0, Allegiance.WHITE));
        arr.add(new Rook(7, 0, Allegiance.WHITE));
        arr.add(new Knight(1, 0, Allegiance.WHITE));
        arr.add(new Knight(6, 0, Allegiance.WHITE));
        arr.add(new Bishop(2, 0, Allegiance.WHITE));
        arr.add(new Bishop(5, 0, Allegiance.WHITE));
        arr.add(new Queen(3, 0, Allegiance.WHITE));
        arr.add(new King(4, 0, Allegiance.WHITE));

        // Black
        arr.add(new Rook(0, 7, Allegiance.BLACK));
        arr.add(new Rook(7, 7, Allegiance.BLACK));
        arr.add(new Knight(1, 7, Allegiance.BLACK));
        arr.add(new Knight(6, 7, Allegiance.BLACK));
        arr.add(new Bishop(2, 7, Allegiance.BLACK));
        arr.add(new Bishop(5, 7, Allegiance.BLACK));
        arr.add(new Queen(3, 7, Allegiance.BLACK));
        arr.add(new King(4, 7, Allegiance.BLACK));

        for (Piece p : arr) {
            placePiece(p);
        }

        maxBlackPieces = 16;
        maxWhitePieces = 16;

        System.out.println("--------------");
        visualizeState();
    }

    //TODO
    // --Castling
    // --Pawn promotion
    // --En passant
    public boolean executeMove(Move m) {
        boolean gameEnder = false;
        if(getPiece(m.destinationLocation) instanceof King){
            gameEnder = true;
        }

        Piece p = board[m.currentLocation[0]][m.currentLocation[1]];
        board[m.currentLocation[0]][m.currentLocation[1]] = null;

        board[m.destinationLocation[0]][m.destinationLocation[1]] = p;

        p.setPosition(m.destinationLocation); //TODO This line slows it down a lot
        checkPawnPromotion(p); // TODO This will mess with Reverse Move

        if(m.specialMove == SpecialMove.CASTLING){
            if(m.destinationLocation[0] > 4){
                Piece rook = board[7][m.destinationLocation[1]];
                board[5][m.destinationLocation[1]] = rook;
                board[7][m.destinationLocation[1]] = null;
                rook.setPosition(5, m.destinationLocation[1]);
            } else {
                Piece rook = board[0][m.destinationLocation[1]];
                board[3][m.destinationLocation[1]] = rook;
                board[0][m.destinationLocation[1]] = null;
                rook.setPosition(3, m.destinationLocation[1]);
            }
        }

        if(m.specialMove == SpecialMove.EN_PASSANT){
            if(m.destinationLocation[1] == 5){
                board[m.destinationLocation[0]][m.destinationLocation[1]-1] = null;
            } else if(m.destinationLocation[1] == 2){
                board[m.destinationLocation[0]][m.destinationLocation[1]+1] = null;
            }
        }
        moveHistory.add(m);

        return gameEnder;
    }

    // TODO will crash if reverse is used with no moves
    // TODO bugged. Doesn't restore piece that was attacked
    // TODO Castling, pawn promotion (+attack), en passant etc.
    public void reverseMove() {
        /*
        Move m = moveHistory.get(moveHistory.size() - 1);

        if(m.isAttack()) {
            board[m.destinationLocation[0]][m.destinationLocation[1]] = m.attackedPiece;
        } else {
            board[m.destinationLocation[0]][m.destinationLocation[1]] = null;
        }
        board[m.currentLocation[0]][m.currentLocation[1]] = m.piece;

        moveHistory.remove(moveHistory.size() - 1);

         */
    }

    private Piece checkPawnPromotion(Piece p) {
        if (p instanceof Pawn) {
            if (p.getAllegiance() == Allegiance.WHITE && p.y() == 7) {
                p = new Queen(p.x(), p.y(), Allegiance.WHITE);
                board[p.x()][p.y()] = p;
            } else if (p.getAllegiance() == Allegiance.BLACK && p.y() == 0) {
                p = new Queen(p.x(), p.y(), Allegiance.BLACK);
                board[p.x()][p.y()] = p;
            }
        }
        return p;
    }

    public Piece getSquare(int x, int y) {
        return board[x][y];
    }

    public void placePiece(Piece piece) {
        board[piece.x()][piece.y()] = piece;
    }

    public void visualizeState() {
        StringBuilder s = new StringBuilder();
        for (int y = board.length - 1; y >= 0; y--) {
            s.append(ANSI.GRAY + y + ANSI.RESET + " " + (y + 1) + " ");
            for (int x = 0; x < board[0].length; x++) {
                if (board[x][y] != null) {
                    s.append(board[x][y] + "  ");
                } else {
                    s.append(".  ");
                }
            }
            s.append("\n");
        }
        s.append(
                "    A  B  C  D  E  F  G  H\n" +
                        ANSI.GRAY +
                        "    0  1  2  3  4  5  6  7\n" +
                        ANSI.RESET);
        System.out.println(s);
    }

    public void visualizeLegalMoves(Piece piece) {
        List<Move> moves = piece.calculateLegalMoves(this);

        StringBuilder s = new StringBuilder();
        for (int y = board.length - 1; y >= 0; y--) {
            s.append(y + " ");
            for (int x = 0; x < board[0].length; x++) {

                boolean b = false;
                for (Move m : moves) {
                    if (m.destinationLocation[0] == x && m.destinationLocation[1] == y) {
                        s.append("+  ");
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    s.append(".  ");
                }
            }
            s.append("\n");
        }
        s.append("  0  1  2  3  4  5  6  7\n");
        System.out.println(s);
    }

    public void visualizeSpecialMoves(Allegiance allegiance, SpecialMove specialMove) {
        List<Move> moves = getAllMoves(allegiance);

        StringBuilder s = new StringBuilder();
        for (int y = board.length - 1; y >= 0; y--) {
            s.append(y + " ");
            for (int x = 0; x < board[0].length; x++) {

                boolean b = false;
                for (Move m : moves) {
                    if(m.getSpecialMove() == specialMove) {
                        if (m.destinationLocation[0] == x && m.destinationLocation[1] == y) {
                            s.append("+  ");
                            b = true;
                            break;
                        }
                    }
                }
                if (!b) {
                    s.append(".  ");
                }
            }
            s.append("\n");
        }
        s.append("  0  1  2  3  4  5  6  7\n");
        System.out.println(s);
    }

    public Piece[][] getBoard() {
        return board;
    }

    // TODO this will slow down eveything if called and then iterated through. You should just iterate through board[][] only optimally
    public List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<>();

        for (int n = 0; n < board.length; n++) {
            for (int i = 0; i < board[0].length; i++) {
                if (board[n][i] != null) {
                    pieces.add(board[n][i]);
                }
            }
        }

        return pieces;
    }

    public List<Piece> getAllPiecesByAllegiance(Allegiance allegiance) {
        List<Piece> pieces = new ArrayList<>();

        for (int n = 0; n < board.length; n++) {
            for (int i = 0; i < board[0].length; i++) {
                if (board[n][i] != null && board[n][i].getAllegiance() == allegiance) {
                    pieces.add(board[n][i]);
                }
            }
        }

        return pieces;
    }

    public List<Move> getAllAttackingMoves(Allegiance allegiance) {
        List<Move> moves = new ArrayList<>();

        for (int n = 0; n < board.length; n++) {
            for (int i = 0; i < board[0].length; i++) {
                if (board[n][i] != null && board[n][i].getAllegiance() == allegiance) {
                    for(Move m : board[n][i].calculateLegalMoves(this)){
                        if(m.isAttack()){
                            moves.add(m);
                        }
                    }
                }
            }
        }

        return moves;
    }

    public List<Move> getAllMovesSansCastling(Allegiance allegiance) {
        List<Move> moves = new ArrayList<>();
        final int MAX_PIECES = getMaxPieceCount(allegiance);
        int curPieces = 0;

        for (int n = 0; n < board.length; n++) {
            for (int i = 0; i < board[0].length; i++) {
                if(curPieces >= MAX_PIECES){
                    break;
                }

                if (board[n][i] != null && board[n][i].getAllegiance() == allegiance) {
                    moves.addAll(board[n][i].calculateLegalMoves(this));
                    curPieces++;
                }
            }
        }

        if(!moveHistory.isEmpty()) {
            addEnPassantMoves(moves);
        }

        return moves;
    }

    public List<Move> getAllMoves(Allegiance allegiance) {
        List<Move> moves = getAllMovesSansCastling(allegiance);

        addCastlingMoves(moves, allegiance);

        return moves;
    }

    // Lazy and should not be used by AI
    public List<Move> removeCheckMoves(List<Move> moves, Allegiance allegiance){
        Allegiance opponent = Evaluation.getOppositeAllegiance(allegiance);
        List<Move> illegalMoves = new ArrayList<>();
        for(Move m : moves) {
            Board tempBoard = new Board(this);
            boolean go = tempBoard.executeMove(m);
            if(isCheck(tempBoard.getAllMoves(opponent))){
                illegalMoves.add(m);
            }
        }
        moves.removeAll(illegalMoves);
        return moves;
    }

    public boolean isCheck(List<Move> moves){
        for(Move m : moves){
            if(m.isAttack() && m.specialMove == SpecialMove.KING_CAPTURE){
                return true;
            }
        }
        return false;
    }

    private void addEnPassantMoves(List<Move> moves){
        List<Move> enPassantMoves = new ArrayList<>();
        Move m = moveHistory.get(moveHistory.size()-1);
        Piece p = getPiece(m.getDestinationLocation());

        if(p.getAllegiance() == Allegiance.WHITE){
            if(p instanceof Pawn &&
                    m.getCurrentLocation()[1] == 1 &&
                    m.destinationLocation[1] == 3
            ){
                if(isEnemyPawn(p.x()+1, p.y(), p.getAllegiance())){
                    Move newMove = new Move(new int[]{p.x()+1, p.y()}, new int[]{p.x(), p.y()-1}, board[p.x()+1][p.y()], true, p);
                    newMove.setSpecialMove(SpecialMove.EN_PASSANT);
                    enPassantMoves.add(newMove);
                }

                if(isEnemyPawn(p.x()-1, p.y(), p.getAllegiance())){
                    Move newMove = new Move(new int[]{p.x()-1, p.y()}, new int[]{p.x(), p.y()-1}, board[p.x()-1][p.y()], true, p);
                    newMove.setSpecialMove(SpecialMove.EN_PASSANT);
                    enPassantMoves.add(newMove);
                }
            }
        } else {
            if(p instanceof Pawn &&
                    m.getCurrentLocation()[1] == 6 &&
                    m.destinationLocation[1] == 4
            ){
                if(isEnemyPawn(p.x()+1, p.y(), p.getAllegiance())){
                    Move newMove = new Move(new int[]{p.x()+1, p.y()}, new int[]{p.x(), p.y()+1}, board[p.x()+1][p.y()], true, p);
                    newMove.setSpecialMove(SpecialMove.EN_PASSANT);
                    enPassantMoves.add(newMove);
                }

                if(isEnemyPawn(p.x()-1, p.y(), p.getAllegiance())){
                    Move newMove = new Move(new int[]{p.x()-1, p.y()}, new int[]{p.x(), p.y()+1}, board[p.x()-1][p.y()], true, p);
                    newMove.setSpecialMove(SpecialMove.EN_PASSANT);
                    enPassantMoves.add(newMove);
                }
            }
        }

        moves.addAll(enPassantMoves);
    }

    private boolean isEnemyPawn(int x, int y, Allegiance allegiance){
        if(isOutOfBounds(x, y)){
            return false;
        } else {
            Piece p = board[x][y];
            return p != null && p.getAllegiance() != allegiance && p instanceof Pawn;
        }
    }

    private boolean isOutOfBounds(int x, int y){
        return !(x >= 0 && x < 8 && y >=0 && y < 8);
    }


    private void addCastlingMoves(List<Move> moves, Allegiance allegiance) {
        List<Move> castlingMoves = new ArrayList<>();
        List<Move> enemyMoves = null;

        if (allegiance == Allegiance.WHITE) {
            if (board[4][0] != null &&
                    board[4][0] instanceof King &&
                    board[4][0].getAllegiance() == Allegiance.WHITE &&
                    !hasPieceMoved(board[4][0]) &&
                    !isSpaceAttacked(4, 0, enemyMoves, allegiance)) {

                if (board[5][0] == null &&
                        board[6][0] == null &&
                        board[7][0] != null &&
                        board[7][0] instanceof Rook &&
                        board[7][0].getAllegiance() == Allegiance.WHITE &&
                        !hasPieceMoved(board[7][0]) &&
                        !isSpaceAttacked(5, 0, enemyMoves, allegiance) &&
                        !isSpaceAttacked(6, 0, enemyMoves, allegiance)) {

                    Move m = new Move(new int[]{4, 0}, new int[]{6, 0}, board[4][0], false, null);
                    m.setSpecialMove(SpecialMove.CASTLING);
                    castlingMoves.add(m);
                }

                if (board[3][0] == null &&
                        board[2][0] == null &&
                        board[1][0] == null &&
                        board[0][0] != null &&
                        board[0][0] instanceof Rook &&
                        board[0][0].getAllegiance() == Allegiance.WHITE &&
                        !hasPieceMoved(board[0][0]) &&
                        !isSpaceAttacked(3, 0, enemyMoves, allegiance) &&
                        !isSpaceAttacked(2, 0, enemyMoves, allegiance)) {

                    Move m = new Move(new int[]{4, 0}, new int[]{2, 0}, board[4][0], false, null);
                    m.setSpecialMove(SpecialMove.CASTLING);
                    castlingMoves.add(m);
                }
            }
        } else {
            if (board[4][7] != null &&
                    board[4][7] instanceof King &&
                    board[4][7].getAllegiance() == Allegiance.BLACK &&
                    !hasPieceMoved(board[4][7]) &&
                    !isSpaceAttacked(4, 7, enemyMoves, allegiance)) {

                if (board[5][7] == null &&
                        board[6][7] == null &&
                        board[7][7] != null &&
                        board[7][7] instanceof Rook &&
                        board[7][7].getAllegiance() == Allegiance.BLACK &&
                        !hasPieceMoved(board[7][7]) &&
                        !isSpaceAttacked(5, 7, enemyMoves, allegiance) &&
                        !isSpaceAttacked(6, 7, enemyMoves, allegiance)) {

                    Move m = new Move(new int[]{4, 7}, new int[]{6, 7}, board[4][7], false, null);
                    m.setSpecialMove(SpecialMove.CASTLING);
                    castlingMoves.add(m);
                }

                if (board[3][7] == null &&
                        board[2][7] == null &&
                        board[1][7] == null &&
                        board[0][7] != null &&
                        board[0][7] instanceof Rook &&
                        board[0][7].getAllegiance() == Allegiance.BLACK &&
                        !hasPieceMoved(board[0][7]) &&
                        !isSpaceAttacked(3, 7, enemyMoves, allegiance) &&
                        !isSpaceAttacked(2, 7, enemyMoves, allegiance)) {

                    Move m = new Move(new int[]{4, 7}, new int[]{2, 7}, board[4][7], false, null);
                    m.setSpecialMove(SpecialMove.CASTLING);
                    castlingMoves.add(m);
                }
            }
        }

        moves.addAll(castlingMoves);
    }

    public boolean hasPieceMoved(Piece p) {
        boolean hasMoved = false;
        for (Move m : moveHistory) {
            if ((m.getCurrentLocation()[0] == p.x() &&
                    m.getCurrentLocation()[1] == p.y()) ||
                    (m.getDestinationLocation()[0] == p.x() &&
                            m.getDestinationLocation()[1] == p.y())
            ) {
                hasMoved = true;
                break;
            }
        }
        return hasMoved;
    }

    public boolean isSpaceAttacked(int x, int y, List<Move> enemyMoves, Allegiance allegiance) {
        if(enemyMoves == null){
            enemyMoves = new ArrayList<>();
            if(allegiance == Allegiance.WHITE){
                enemyMoves.addAll(getAllMovesSansCastling(Allegiance.BLACK));
            } else {
                enemyMoves.addAll(getAllMovesSansCastling(Allegiance.WHITE));
            }

        }
        boolean isAttacked = false;
        for (Move m : enemyMoves) {
            if(getPiece(m.currentLocation).getAllegiance() != allegiance) {
                if (m.getDestinationLocation()[0] == x && m.getDestinationLocation()[1] == y) {
                    isAttacked = true;
                    break;
                }
            }
        }
        return isAttacked;
    }

    public Piece getPiece(int[] location){
        return board[location[0]][location[1]];
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public int getMaxPieceCount(Allegiance allegiance){
        if(allegiance == Allegiance.WHITE){
            return maxWhitePieces;
        } else {
            return maxBlackPieces;
        }
    }
}
