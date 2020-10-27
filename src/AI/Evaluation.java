package AI;

import logic.Board;
import logic.Move;
import logic.pieces.*;

public class Evaluation {
    public static final int START_DEPTH = 5;
    public static Move bestMove;

    public static int minimax(Board board, int depth, boolean maximizingPlayer, Allegiance allegiance){
        if(depth == 0) { // || TODO Game is over
            return scoreEvaluation(board, allegiance);
        }

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;

            for (Move m : board.getAllMoves(allegiance)) {
                board.executeMove(m);

                int eval = minimax(board, depth-1, false, allegiance);

                if(depth == START_DEPTH){
                    if(eval > maxEval){
                        bestMove = m;
                    }
                }

                maxEval = Math.max(maxEval, eval);

                board.reverseMove();
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Move m : board.getAllMoves(allegiance)) {
                board.executeMove(m);

                int eval = minimax(board, depth-1, true, allegiance);
                minEval = Math.min(minEval, eval);

                board.reverseMove();
            }
            return minEval;
        }

    }

    public static int scoreEvaluation(Board board, Allegiance allegiance){
        int score = 0;

        for (Piece p : board.getAllPieces()) {
            if(p.getAllegiance() == allegiance){
                score += p.getBaseValue();
            } else {
                score -= p.getBaseValue();
            }
        }

        return score;
    }

    public Move getBestMove() {
        return bestMove;
    }
}
