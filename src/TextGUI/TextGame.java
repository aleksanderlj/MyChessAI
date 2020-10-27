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
            Move myMove;
            do {
                myMove = MoveBuilder.getMove(board);
            } while (myMove == null);
            System.out.println(myMove);
            board.executeMove(myMove);
            board.visualizeState();

            Evaluation.minimax(board, Evaluation.START_DEPTH, true, Allegiance.BLACK);
            board.executeMove(Evaluation.bestMove);
            System.out.println(Evaluation.bestMove);
            board.visualizeState();
        }





        /*

        board.executeMove(board.getAllMoves(Allegiance.WHITE).get(1));
        board.visualizeState();

        board.reverseMove();
        board.visualizeState();

         */

    }
}
