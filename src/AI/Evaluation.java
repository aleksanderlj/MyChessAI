package AI;

import logic.Board;
import logic.Move;
import logic.pieces.*;

import java.util.Collections;
import java.util.List;

public class Evaluation {
    public static final int START_DEPTH = 7;
    public static Move bestMove;
    public static boolean gameOver;

    //https://www.youtube.com/watch?v=l-hh51ncgDI
    public static int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, Allegiance allegiance) {
        if(depth == 0 || gameOver) {
            gameOver = false;
            return scoreEvaluation(board, allegiance);
        }


        List<Move> possibleMoves = board.getAllMoves(allegiance);

        // Move ordering
        for(Move m : possibleMoves){
            Board tempBoard = new Board(board);
            tempBoard.executeMove(m);
            m.setHeuristicValue(scoreEvaluation(tempBoard, allegiance));
            //board.reverseMove();
        }


        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;

            possibleMoves.sort(Collections.reverseOrder()); //MAX
            //Collections.sort(possibleMoves);

            for (Move m : possibleMoves) {
                Board tempBoard = new Board(board);
                gameOver = tempBoard.executeMove(m);
                int eval = minimax(tempBoard, depth-1, alpha, beta, false, allegiance);

                //board.executeMove(m);
                //int eval = minimax(board, depth-1, alpha, beta, false, allegiance);

                if(depth == START_DEPTH){
                    if(eval > maxEval){
                        bestMove = m;
                    }
                }

                maxEval = Math.max(maxEval, eval);

                //board.reverseMove();

                alpha = Math.max(alpha, eval);
                if(beta <= alpha){
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            //possibleMoves.sort(Collections.reverseOrder());
            Collections.sort(possibleMoves); // MIN

            for (Move m : possibleMoves) {
                Board tempBoard = new Board(board);
                gameOver = tempBoard.executeMove(m);
                int eval = minimax(tempBoard, depth-1, alpha, beta, true, allegiance);

                //board.executeMove(m);
                //int eval = minimax(board, depth-1, alpha, beta, true, allegiance);

                minEval = Math.min(minEval, eval);

                //board.reverseMove();

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
        score += parsePositionScore(p);

        return score;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public static int parsePositionScore(Piece p){
        int x = p.x();
        int y = p.y();
        int posScore = 0;

        if(p.getAllegiance() == Allegiance.WHITE) {
            if (p instanceof Bishop) {
                posScore += PreferredCoordinates.WHITE_BISHOP[x][y];
            } else if (p instanceof King) {
                posScore += PreferredCoordinates.WHITE_KING[x][y];
            } else if (p instanceof Knight) {
                posScore += PreferredCoordinates.WHITE_KNIGHT[x][y];
            } else if (p instanceof Pawn) {
                posScore += PreferredCoordinates.WHITE_PAWN[x][y];
            } else if (p instanceof Queen) {
                posScore += PreferredCoordinates.WHITE_QUEEN[x][y];
            } else if (p instanceof Rook) {
                posScore += PreferredCoordinates.WHITE_ROOK[x][y];
            }
        } else {
            if (p instanceof Bishop) {
                posScore += PreferredCoordinates.BLACK_BISHOP[x][y];
            } else if (p instanceof King) {
                posScore += PreferredCoordinates.BLACK_KING[x][y];
            } else if (p instanceof Knight) {
                posScore += PreferredCoordinates.BLACK_KNIGHT[x][y];
            } else if (p instanceof Pawn) {
                posScore += PreferredCoordinates.BLACK_PAWN[x][y];
            } else if (p instanceof Queen) {
                posScore += PreferredCoordinates.BLACK_QUEEN[x][y];
            } else if (p instanceof Rook) {
                posScore += PreferredCoordinates.BLACK_ROOK[x][y];
            }
        }

        return posScore;
    }
}


//TODO:
// -Base value
// Base value modifier
// -Preferred coordinates
// isMate
// Castling
// Double pawn
// Pieces threatened by minor piece
// Endgame
