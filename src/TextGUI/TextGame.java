package TextGUI;

import AI.Evaluation;
import logic.Board;
import logic.Move;
import logic.pieces.Allegiance;

public class TextGame {
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        int round = 1;

        while (true){
            playerMoves(board, Allegiance.WHITE);
            aiMoves(board, Allegiance.BLACK);
        }

        /*
        while (true){
            aiMoves(board, Allegiance.WHITE);
            playerMoves(board, Allegiance.BLACK);
        }
         */

        /*

        board.executeMove(board.getAllMoves(Allegiance.WHITE).get(1));
        board.visualizeState();

        board.reverseMove();
        board.visualizeState();

         */

    }

    public static void playerMoves(Board board, Allegiance allegiance){
        Move myMove;
        do {
            myMove = MoveBuilder.getMove(board, allegiance);
            if(myMove == null){
                System.out.println("Not a valid move");
            }
        } while (myMove == null);
        System.out.println(myMove);
        board.executeMove(myMove);
        board.visualizeState();
    }

    public static void aiMoves(Board board, Allegiance allegiance){
        System.nanoTime();
        Evaluation.minimax(board, Evaluation.START_DEPTH, true, allegiance);
        board.executeMove(Evaluation.bestMove);
        System.out.println(Evaluation.bestMove);
        board.visualizeState();
    }
}
