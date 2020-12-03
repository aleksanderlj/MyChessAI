package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(int x, int y, Allegiance allegiance) {
        super(x, y, allegiance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        List<int[]> squares = new ArrayList<>();

        //int x = this.position[0];
        //int y = this.position[1];

        for (int n = x - 1; n < x + 2; n++) {
            for (int i = y - 1; i < y + 2; i++) {
                if (!(n == x && i == y)) {
                    Move m = testSquareLegality(board, n, i);
                    if (m != null) {
                        legalMoves.add(m);
                    }
                }
            }
        }

        return legalMoves;
    }
}
