package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        List<int[]> squares = new ArrayList<>();

        int x = this.position[0];
        int y = this.position[1];

        for (int[] a : squares) {
            System.out.println("" + a[0] + a[1]);
        }

        for (int n = x - 1; n < x + 2; n++) {
            for (int i = y - 1; i < y + 2; i++) {
                if (!(n == x && i == y)) {
                    squares.add(new int[]{n, i});
                }
            }
        }

        for (int n = 0; n < squares.size(); n++) {
            Move m = testSquareLegality(board, squares.get(n)[0], squares.get(n)[1]);
            if (m != null) {
                legalMoves.add(m);
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return "K";
    }
}
