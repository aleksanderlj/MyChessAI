package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        int x = this.position[0];
        int y = this.position[1];

        if(!color) { // white
            Move m = testSquareLegality(board, x, y+1);
            if(m != null){
                legalMoves.add(m);
                // Hvis det er første træk
                if(y == 1){
                    m = testSquareLegality(board, x, y+2);
                    if(m != null){
                        legalMoves.add(m);
                    }
                }
            }

            // Hvis der kan tages en brik
            if(board.getSquare(x+1, y+1) != null && board.getSquare(x+1, y+1).color){
                legalMoves.add(new Move(this.position, new int[]{x+1, y+1}, this));
            }

            if(board.getSquare(x-1, y+1) != null && board.getSquare(x-1, y+1).color){
                legalMoves.add(new Move(this.position, new int[]{x-1, y+1}, this));
            }

        } else { // Black
            Move m = testSquareLegality(board, x, y-1);
            if(m != null){
                legalMoves.add(m);
                // Hvis det er første træk
                if(y == 6){
                    m = testSquareLegality(board, x, y-2);
                    if(m != null){
                        legalMoves.add(m);
                    }
                }
            }

            // Hvis der kan tages en brik
            if(board.getSquare(x+1, y-1) != null && !board.getSquare(x+1, y-1).color){
                legalMoves.add(new Move(this.position, new int[]{x+1, y-1}, this));
            }

            if(board.getSquare(x-1, y-1) != null && !board.getSquare(x-1, y-1).color){
                legalMoves.add(new Move(this.position, new int[]{x-1, y-1}, this));
            }
        }

        return legalMoves;
    }

    @Override
    public Move testSquareLegality(Board board, int x, int y) {
        if (board.getSquare(x, y) == null) {
            return new Move(this.position, new int[]{x, y}, this);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "P";
    }
}
