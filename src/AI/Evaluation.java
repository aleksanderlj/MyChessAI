package AI;

import logic.Board;
import logic.Move;
import logic.SpecialMove;
import logic.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Evaluation {
    public static final int START_DEPTH = 6;
    public static Move bestMove;
    public static boolean gameOver;
    public static int gameNotOverStates = 0;
    //public static int nodes = 0;

    //https://www.youtube.com/watch?v=l-hh51ncgDI
    public static int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, Allegiance allegiance, int cachedScoreEval) {
        if(depth == 0 || gameOver) {
            int scoreEval;
            if(cachedScoreEval != -1) { // this is done twice otherwise because of move ordering, im pretty sure
                scoreEval = cachedScoreEval;
            } else {
                scoreEval = scoreEvaluation(board, allegiance, depth);
            }

            gameOver = false;
            //nodes++;
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
            m.setHeuristicValue(scoreEvaluation(tempBoard, allegiance, depth));
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
                    if(enemyMove.isAttack() && enemyMove.getSpecialMove() == SpecialMove.KING_CAPTURE){
                        illegalMove = true;
                        break;
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
                int eval = minimax(tempBoard, depth-1, alpha, beta, false, allegiance, m.getHeuristicValue());

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
                int eval = minimax(tempBoard, depth-1, alpha, beta, true, allegiance, m.getHeuristicValue());

                minEval = Math.min(minEval, eval);

                beta = Math.min(beta, eval);
                if(beta <= alpha){
                    break;
                }
            }
            return minEval;
        }

    }

    public static int scoreEvaluation(Board board, Allegiance allegiance, int depth){
        gameOver = false;
        int kingcounter = 0;
        int score = 0;
        List<Piece> pieces = board.getAllPieces();
        int[] allyPawnPos = new int[8];
        int[] enemyPawnPos = new int[8];
        Piece survivingKing = null;

        for (Piece p : pieces) {
            if(p.getAllegiance() == allegiance){
                score += getPieceScore(p);
            } else {
                score -= getPieceScore(p);
            }

            if(p instanceof Pawn) {
                if (p.getAllegiance() == allegiance) {
                    allyPawnPos[p.x()]++;
                } else {
                    enemyPawnPos[p.x()]++;
                }
            }

            if(p instanceof King){
                kingcounter++;
                survivingKing = p;
            }
        }

        score += checkCastlingScore(board, allegiance);
        score += checkDoublePawn(allyPawnPos, enemyPawnPos, allegiance);

        if(kingcounter != 2){
            gameOver = true;
            if(survivingKing != null) { // In a normal game this will never be null, but it might in tests
                if (survivingKing.getAllegiance() == allegiance) {
                    score += 100 * (START_DEPTH-depth+1);
                } else {
                    score -= 100 * (START_DEPTH-depth+1);
                }
            }
        }

        return score;
    }

    public static int getPieceScore(Piece p){
        int score;

        score = p.getBaseValue();
        score += parsePositionScore(p);

        if(p instanceof Knight){
            score += 3*(4- checkKnightDistanceFromCenter(p));
        }

        return score;
    }

    // TODO java math is slow
    public static int checkKnightDistanceFromCenter(Piece p){
        double d = Math.sqrt(Math.pow((3.5-p.x()), 2) + Math.pow((3.5-p.y()), 2));
        return (int) d;

        // Calculating it correctly makes it worse. Possibly only until other pieces get bonuses as well.
        /*
        List<Integer> arr = new ArrayList<>();

        arr.add(KnightDistance.knightDistance(p, 3, 3));
        arr.add(KnightDistance.knightDistance(p, 3, 4));
        arr.add(KnightDistance.knightDistance(p, 4, 3));
        arr.add(KnightDistance.knightDistance(p, 4, 4));

        return Collections.min(arr);

         */
    }

    public static int checkCastlingScore(Board board, Allegiance allegiance){
        int score = 0;
        Move lastMove = board.getMoveHistory().get(board.getMoveHistory().size()-1);
        if (lastMove.getSpecialMove() == SpecialMove.CASTLING) {
            if(board.getPiece(lastMove.getDestinationLocation()).getAllegiance() == allegiance){
                score += 16;
            } else {
                score -= 16;
            }
        }
        return score;
    }

    public static int checkDoublePawn(int[] allyPawnPos, int[] enemyPawnPos, Allegiance allegiance){
        int score = 0;

        for (int n=0 ; n < allyPawnPos.length ; n++){
            if (allyPawnPos[n] > 0){
                score -= 8;
            }

            if (enemyPawnPos[n] > 0){
                score += 8;
            }
        }

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
// -Castling
// -Double pawn
// Pieces threatened by minor piece
// Endgame
