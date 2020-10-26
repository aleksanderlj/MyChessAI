package AI;

import logic.Board;
import logic.Move;
import logic.pieces.*;

public class Evaluation {

    public int minimax(Board board, int depth, boolean maximizingPlayer, Allegiance allegiance){
        if(depth == 0 ) { // || TODO Game is over
            return scoreEvaluation(board, allegiance);
        }

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;

            for (Move m : board.getAllWhiteMoves()) {
                // Create copy of board and do move

                int eval = minimax(board, depth-1, false, allegiance);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Move m : board.getAllWhiteMoves()) {
                int eval = minimax(board, depth-1, false, allegiance);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }

    }

    public int scoreEvaluation(Board board, Allegiance allegiance){
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
}
