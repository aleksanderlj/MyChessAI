package logic;

import logic.pieces.Piece;

import java.util.List;

public class Board {

    Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    public void initialize() {

    }

    public Piece getSquare(int x, int y) {
        return board[x][y];
    }

    public void placePiece(Piece piece) {
        board[piece.x()][piece.y()] = piece;
    }

    public void visualizeState() {
        StringBuilder s = new StringBuilder();
        for (int y = board.length-1; y >= 0; y--) {
            s.append(y + " ");
            for (int x = 0; x < board[0].length; x++) {
                if (board[x][y] != null) {
                    s.append(board[x][y] + "  ");
                } else {
                    s.append(".  ");
                }
            }
            s.append("\n");
        }
        s.append("  0  1  2  3  4  5  6  7\n");
        System.out.println(s);
    }

    public void visualizeLegalMoves(Piece piece) {
        List<Move> moves = piece.calculateLegalMoves(this);

        StringBuilder s = new StringBuilder();
        for (int y = board.length-1; y >= 0; y--) {
            s.append(y + " ");
            for (int x = 0; x < board[0].length; x++) {

                boolean b = false;
                for (Move m : moves) {
                    if(m.destinationLocation[0] == x && m.destinationLocation[1] == y){
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

}
