package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(int x, int y, Allegiance allegiance) {
        super(x, y, allegiance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        List<int[]> squares = new ArrayList<>();

        int x = this.position[0];
        int y = this.position[1];

        squares.add(new int[]{x+2, y+1});
        squares.add(new int[]{x+2, y-1});
        squares.add(new int[]{x-2, y+1});
        squares.add(new int[]{x-2, y-1});
        squares.add(new int[]{x-1, y+2});
        squares.add(new int[]{x+1, y+2});
        squares.add(new int[]{x-1, y-2});
        squares.add(new int[]{x+1, y-2});

        for (int[] a : squares) {
            Move m = testSquareLegality(board, a[0], a[1]);
            if (m != null) {
                legalMoves.add(m);
            }
        }

        return legalMoves;
    }
}
