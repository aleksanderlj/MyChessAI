package TextGUI;

import logic.Board;
import logic.Move;
import logic.pieces.Allegiance;
import logic.pieces.Piece;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveBuilder {
    public static Move getMove(Board board, Allegiance allegiance){
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        Pattern pattern = Pattern.compile("[a-hA-h][1-8]-[a-hA-h][1-8]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        while(!matcher.find()){
            System.out.println("Must be eg. \"E2-E4\"");
            input = sc.next();
            matcher = pattern.matcher(input);
        }

        return parse(board, input, allegiance);
    }

    private static Move parse(Board board, String s, Allegiance allegiance){
        s = s.toLowerCase();
        int[] start = new int[2];
        int[] end = new int[2];

        start[0] = s.charAt(0)-97;
        start[1] = Character.getNumericValue(s.charAt(1))-1;
        end[0] = s.charAt(3)-97;
        end[1] = Character.getNumericValue(s.charAt(4))-1;

        Piece p = board.getSquare(start[0], start[1]);
        Piece destination = board.getSquare(end[0], end[1]);

        if(p == null || p.getAllegiance() != allegiance){
            return null;
        }

        boolean isAttack;

        if(destination == null){
            isAttack = false;
        } else if (destination.getAllegiance() == p.getAllegiance()){
            isAttack = false;
        } else {
            isAttack = true;
        }

        Move m = new Move(start, end, p, isAttack);

        boolean isLegal = false;
        List<Move> legalMoves = board.getAllMoves(allegiance);
        for (Move legalM : legalMoves) {
            if(m.equals(legalM)){
                isLegal = true;
                break;
            }
        }

        if(!isLegal){
            return null;
        }

        // TODO COMPARE MOVES TO ALL LEGAL MOVES TO SEE IF IT IS LEGAL
        // TODO Dont allow moving of enemy pieces
        // TODO TIME THE ALGORITHM

        return m;
    }
}
