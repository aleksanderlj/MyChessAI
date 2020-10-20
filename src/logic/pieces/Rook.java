package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        int x = this.position[0];
        int y = this.position[1];

        // Test right
        for(int i = x+1 ; i <= 7 ; i++) {
            if (board.getSquare(i, y) == null) {
                legalMoves.add(new Move(this.position, new int[]{i, y}, this));
            } else if (board.getSquare(i, y).getColor() != this.color) {
                legalMoves.add(new Move(this.position, new int[]{i, y}, this));
                break;
            } else if (board.getSquare(i, y).getColor() == this.color) {
                break;
            }
        }

        // Test left
        for(int i = x-1 ; i >= 0 ; i--) {
            if (board.getSquare(i, y) == null) {
                legalMoves.add(new Move(this.position, new int[]{i, y}, this));
            } else if (board.getSquare(i, y).getColor() != this.color) {
                legalMoves.add(new Move(this.position, new int[]{i, y}, this));
                break;
            } else if (board.getSquare(i, y).getColor() == this.color) {
                break;
            }
        }

        // Test up
        for(int i = y+1 ; i <= 7 ; i++) {
            if (board.getSquare(x, i) == null) {
                legalMoves.add(new Move(this.position, new int[]{x, i}, this));
            } else if (board.getSquare(x, i).getColor() != this.color) {
                legalMoves.add(new Move(this.position, new int[]{x, i}, this));
                break;
            } else if (board.getSquare(x, i).getColor() == this.color) {
                break;
            }
        }

        // Test down
        for(int i = y-1 ; i >= 0 ; i++) {
            if (board.getSquare(x, i) == null) {
                legalMoves.add(new Move(this.position, new int[]{x, i}, this));
            } else if (board.getSquare(x, i).getColor() != this.color) {
                legalMoves.add(new Move(this.position, new int[]{x, i}, this));
                break;
            } else if (board.getSquare(x, i).getColor() == this.color) {
                break;
            }
        }


        return legalMoves;
    }
}
