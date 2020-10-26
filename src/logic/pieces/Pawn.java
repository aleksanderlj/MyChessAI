package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(int x, int y, Allegiance allegiance) {
        super(x, y, allegiance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        int x = this.position[0];
        int y = this.position[1];

        if (allegiance == Allegiance.WHITE) { // white
            Move m = testSquareLegality(board, x, y + 1);
            if (m != null) {
                legalMoves.add(m);
                // Hvis det er første træk
                if (y == 1) {
                    m = testSquareLegality(board, x, y + 2);
                    if (m != null) {
                        legalMoves.add(m);
                    }
                }
            }

            // Hvis der kan tages en brik
            if (x + 1 < 8 && y + 1 < 8) {
                if (board.getSquare(x + 1, y + 1) != null && board.getSquare(x + 1, y + 1).getAllegiance() != this.allegiance) {
                    legalMoves.add(new Move(this.position, new int[]{x + 1, y + 1}, this, false));
                }
            }

            if (x - 1 > -1 && y + 1 < 8) {
                if (board.getSquare(x - 1, y + 1) != null && board.getSquare(x - 1, y + 1).getAllegiance() != this.allegiance) {
                    legalMoves.add(new Move(this.position, new int[]{x - 1, y + 1}, this, false));
                }
            }

        } else { // Black
            Move m = testSquareLegality(board, x, y - 1);
            if (m != null) {
                legalMoves.add(m);
                // Hvis det er første træk
                if (y == 6) {
                    m = testSquareLegality(board, x, y - 2);
                    if (m != null) {
                        legalMoves.add(m);
                    }
                }
            }

            // Hvis der kan tages en brik
            if (x + 1 < 8 && y - 1 > -1) {
                if (board.getSquare(x + 1, y - 1) != null && board.getSquare(x + 1, y - 1).getAllegiance() != this.allegiance) {
                    legalMoves.add(new Move(this.position, new int[]{x + 1, y - 1}, this, false));
                }
            }

            if (x - 1 > -1 && y - 1 > -1) {
                if (board.getSquare(x - 1, y - 1) != null && board.getSquare(x - 1, y - 1).getAllegiance() != this.allegiance) {
                    legalMoves.add(new Move(this.position, new int[]{x - 1, y - 1}, this, false));
                }
            }
        }

        return legalMoves;
    }

    @Override
    public Move testSquareLegality(Board board, int x, int y) {
        if(!(x > -1 && x < 8 && y > -1 && y < 8)){
            return null;
        } else if (board.getSquare(x, y) == null) {
            return new Move(this.position, new int[]{x, y}, this, true);
        } else {
            return null;
        }
    }
}
