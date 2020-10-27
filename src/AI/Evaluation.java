package AI;

import logic.Board;
import logic.Move;
import logic.pieces.*;

public class Evaluation {
    public static final int START_DEPTH = 7;
    public static Move bestMove;
    public static boolean gameOver;

    //https://www.youtube.com/watch?v=l-hh51ncgDI
    public static int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, Allegiance allegiance){
        if(depth == 0 || gameOver) {
            return scoreEvaluation(board, allegiance);
        }

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;

            for (Move m : board.getAllMoves(allegiance)) {
                board.executeMove(m);

                int eval = minimax(board, depth-1, alpha, beta, false, allegiance);

                if(depth == START_DEPTH){
                    if(eval > maxEval){
                        bestMove = m;
                    }
                }

                maxEval = Math.max(maxEval, eval);

                board.reverseMove();

                alpha = Math.max(alpha, eval);
                if(beta <= alpha){
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Move m : board.getAllMoves(allegiance)) {
                board.executeMove(m);

                int eval = minimax(board, depth-1, alpha, beta, true, allegiance);
                minEval = Math.min(minEval, eval);

                board.reverseMove();

                beta = Math.min(beta, eval);
                if(beta <= alpha){
                    break;
                }
            }
            return minEval;
        }

    }

    public static int scoreEvaluation(Board board, Allegiance allegiance){
        gameOver = false;
        int kingcounter = 0;
        int score = 0;

        for (Piece p : board.getAllPieces()) {
            if(p.getAllegiance() == allegiance){
                score += getPieceScore(p);
            } else {
                score -= getPieceScore(p);
            }

            if(p instanceof King){
                kingcounter++;
            }
        }

        if(kingcounter != 2){
            gameOver = true;
        }

        return score;
    }

    public static int getPieceScore(Piece p){
        int score;

        score = p.getBaseValue();

        return score;
    }

    public Move getBestMove() {
        return bestMove;
    }
}


//TODO:
// -Base value
// Base value modifier
// Preferred coordinates
// isMate
// Castling
// Double pawn
// Pieces threatened by minor piece
// Endgame
