package TextGUI;

import AI.Evaluation;
import logic.Board;
import logic.Move;
import logic.pieces.Allegiance;

import javax.swing.*;
import java.util.List;

public class TextGame {
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        int round = 1;


        /*
        while (true){
            playerMoves(board, Allegiance.WHITE);
            aiMoves(board, Allegiance.BLACK);
        }

         */


        while (true){
            aiMoves(board, Allegiance.WHITE);
            playerMoves(board, Allegiance.BLACK);
        }



        /*
        while(true){
            aiMoves(board, Allegiance.WHITE);
            Evaluation.scoreEvaluation(board, Allegiance.WHITE);
            aiMoves(board, Allegiance.BLACK);
            Evaluation.scoreEvaluation(board, Allegiance.BLACK);
        }

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
        try {
            final long startTime = System.currentTimeMillis();
            Evaluation.minimax(board, Evaluation.START_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, allegiance);
            board.executeMove(Evaluation.bestMove);
            final long endTime = System.currentTimeMillis();
            System.out.println(Evaluation.bestMove);
            board.visualizeState();
            System.out.println(MoveBuilder.unParse(Evaluation.bestMove));
            System.out.printf("Time taken: %.4fs\n", (endTime - startTime) / 1000.0);
        } catch (Exception e){
            System.out.println(movesToText(board.getMoveHistory()));
            e.printStackTrace();
        }
    }

    public static String movesToText(List<Move> moveList){
        StringBuilder s = new StringBuilder();
        for (Move m : moveList) {
            s.append(MoveBuilder.unParse(m));
            s.append("\n");
        }
        return s.toString();
    }
}
