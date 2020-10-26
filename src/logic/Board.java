package logic;

import logic.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

    Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    public void initialize() {
        List<Piece> arr = new ArrayList<>();

        for(int n=0 ; n < 8 ; n++){
            arr.add(new Pawn(n, 1, Allegiance.WHITE));
            arr.add(new Pawn(n, 6, Allegiance.BLACK));
        }

        // White
        arr.add(new Rook(0,0, Allegiance.WHITE));
        arr.add(new Rook(7,0, Allegiance.WHITE));
        arr.add(new Knight(1,0, Allegiance.WHITE));
        arr.add(new Knight(6,0, Allegiance.WHITE));
        arr.add(new Bishop(2,0, Allegiance.WHITE));
        arr.add(new Bishop(5,0, Allegiance.WHITE));
        arr.add(new Queen(3,0, Allegiance.WHITE));
        arr.add(new King(4,0, Allegiance.WHITE));

        // Black
        arr.add(new Rook(0,7, Allegiance.BLACK));
        arr.add(new Rook(7,7, Allegiance.BLACK));
        arr.add(new Knight(1,7, Allegiance.BLACK));
        arr.add(new Knight(6,7, Allegiance.BLACK));
        arr.add(new Bishop(2,7, Allegiance.BLACK));
        arr.add(new Bishop(5,7, Allegiance.BLACK));
        arr.add(new Queen(3,7, Allegiance.BLACK));
        arr.add(new King(4,7, Allegiance.BLACK));

        for (Piece p : arr) {
            placePiece(p);
        }

        System.out.println("--------------");
        visualizeState();
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
            s.append(y + " " + (y+1) + " ");
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
                "    0  1  2  3  4  5  6  7\n");
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

    public Piece[][] getBoard() {
        return board;
    }

    // TODO this will slow down eveything if called and then iterated through. You should just iterate through board[][] only optimally
    public List<Piece> getAllPieces(){
        List<Piece> pieces = new ArrayList<>();

        for (int n = 0; n<board.length ; n++){
            for (int i = 0; n<board[0].length ; i++){
                if(board[n][i] != null){
                    pieces.add(board[n][i]);
                }
            }
        }

        return pieces;
    }

    public List<Move> getAllWhiteMoves(){
        List<Move> moves = new ArrayList<>();

        for (int n = 0; n<board.length ; n++){
            for (int i = 0; n<board[0].length ; i++){
                if(board[n][i] != null && board[n][i].getAllegiance() == Allegiance.WHITE){
                    moves.addAll(board[n][i].calculateLegalMoves(this));
                }
            }
        }

        return moves;
    }

    public List<Move> getAllBlackMoves(){
        List<Move> moves = new ArrayList<>();

        for (int n = 0; n<board.length ; n++){
            for (int i = 0; n<board[0].length ; i++){
                if(board[n][i] != null && board[n][i].getAllegiance() == Allegiance.WHITE){
                    moves.addAll(board[n][i].calculateLegalMoves(this));
                }
            }
        }

        return moves;
    }
}
