package AI;

import logic.Board;
import logic.Move;
import logic.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Evaluation {
    public static final int START_DEPTH = 6;
    public static Move bestMove;
    public static boolean gameOver;
    public static int gameNotOverStates = 0;

    //https://www.youtube.com/watch?v=l-hh51ncgDI
    public static int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, Allegiance allegiance) {
        if(depth == 0 || gameOver) {
            int scoreEval = scoreEvaluation(board, allegiance);
            gameOver = false;
            return scoreEval;
        }

        List<Move> possibleMoves;
        if(maximizingPlayer){
            possibleMoves = board.getAllMoves(allegiance);
        } else {
            possibleMoves = board.getAllMoves(getOppositeAllegiance(allegiance));
        }


        // Move ordering
        List<MoveBoardPair> moveBoardPairs = new ArrayList<>(); // Semi-caching
        List<MoveBoardPair> illegalMoves = new ArrayList<>();
        for(Move m : possibleMoves){
            Board tempBoard = new Board(board);
            boolean go = tempBoard.executeMove(m);
            m.setHeuristicValue(scoreEvaluation(tempBoard, allegiance));
            MoveBoardPair mbp = new MoveBoardPair(m, tempBoard, go);
            moveBoardPairs.add(mbp);

            // Check for checkmate
            if(depth == START_DEPTH-1 && !gameOver){
                gameNotOverStates++;
            }

            // Shallow way of removing moves that end in check (will only stop ai form executing those moves, not factoring them in calculation at further depth)
            // Checking at every depth is slower, but provides better results. I chose speed, since the extra depth matters more to me.
            if(depth == START_DEPTH){
                boolean illegalMove = false;
                for (Move enemyMove : tempBoard.getAllMoves(getOppositeAllegiance(allegiance))){
                    if(enemyMove.isAttack() && enemyMove.getSpecialMove() == null){
                        Piece target = tempBoard.getPiece(enemyMove.getDestinationLocation());
                        if (target.getAllegiance() == allegiance && target instanceof King){
                            illegalMove = true;
                        }
                    }
                }
                if(illegalMove){
                    illegalMoves.add(mbp);
                }
            }
        }
        moveBoardPairs.removeAll(illegalMoves);

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;

            moveBoardPairs.sort(Collections.reverseOrder()); // MAX

            for (MoveBoardPair mbp : moveBoardPairs) {
                Move m = mbp.m;
                Board tempBoard = mbp.b;
                gameOver = mbp.go;
                int eval = minimax(tempBoard, depth-1, alpha, beta, false, allegiance);

                if(depth == START_DEPTH){
                    if(eval > maxEval){
                        bestMove = m;
                    }
                }

                maxEval = Math.max(maxEval, eval);

                alpha = Math.max(alpha, eval);
                if(beta <= alpha){
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            Collections.sort(moveBoardPairs);

            for (MoveBoardPair mbp : moveBoardPairs) {
                Move m = mbp.m;
                Board tempBoard = mbp.b;
                gameOver = mbp.go;
                int eval = minimax(tempBoard, depth-1, alpha, beta, true, allegiance);

                minEval = Math.min(minEval, eval);

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

    public static Allegiance getOppositeAllegiance(Allegiance allegiance){
        if(allegiance == Allegiance.WHITE){
            return Allegiance.BLACK;
        } else {
            return Allegiance.WHITE;
        }
    }

    // Simple caching class
    static class MoveBoardPair implements Comparable<MoveBoardPair>{
        Move m;
        Board b;
        boolean go;

        public MoveBoardPair(Move m, Board b, boolean go){
            this.m = m;
            this.b = b;
            this.go = go;
        }

        @Override
        public int compareTo(MoveBoardPair o) {
            return this.m.getHeuristicValue() - o.m.getHeuristicValue();
        }
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
