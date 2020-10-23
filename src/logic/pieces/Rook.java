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
            Move m = testSquareLegality(board, i, y);
            if(m != null){
                legalMoves.add(m);
            } else {
                break;
            }
        }

        // Test left
        for(int i = x-1 ; i >= 0 ; i--) {
            Move m = testSquareLegality(board, i, y);
            if(m != null){
                legalMoves.add(m);
            } else {
                break;
            }
        }

        // Test up
        for(int i = y+1 ; i <= 7 ; i++) {
            Move m = testSquareLegality(board, x, i);
            if(m != null){
                legalMoves.add(m);
            } else {
                break;
            }
        }

        // Test down
        for(int i = y-1 ; i >= 0 ; i--) {
            Move m = testSquareLegality(board, x, i);
            if(m != null){
                legalMoves.add(m);
            } else {
                break;
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return "R";
    }
}
