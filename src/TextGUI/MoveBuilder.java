package TextGUI;

import logic.Board;
import logic.Move;
import logic.pieces.Piece;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveBuilder {
    public static Move getMove(Board board){
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        Pattern pattern = Pattern.compile("[a-hA-h][1-8]-[a-hA-h][1-8]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        while(!matcher.find()){
            System.out.println("Must be eg. \"E2-E4\"");
            input = sc.next();
            matcher = pattern.matcher(input);
        }

        return parse(board, input);
    }

    private static Move parse(Board board, String s){
        s = s.toLowerCase();
        int[] start = new int[2];
        int[] end = new int[2];

        start[0] = s.charAt(0)-97;
        start[1] = Character.getNumericValue(s.charAt(1))-1;
        end[0] = s.charAt(3)-97;
        end[1] = Character.getNumericValue(s.charAt(4))-1;

        Piece p = board.getSquare(start[0], start[1]);
        Piece destination = board.getSquare(end[0], end[1]);

        if(p == null){
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

        // TODO COMPARE MOVES TO ALL LEGAL MOVES TO SEE IF IT IS LEGAL
        // TODO TIME THE ALGORITHM

        return m;
    }
}
