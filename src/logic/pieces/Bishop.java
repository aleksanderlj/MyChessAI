package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        int x = this.position[0];
        int y = this.position[1];
        Move m;
        int n, i;

        // Test up-right
        n = x+1;
        i = y+1;
        while((m = testSquareLegality(board, n, i)) != null){
            legalMoves.add(m);
            n++;
            i++;
            if (!m.isEmpty()){
                break;
            }
        }

        // Test up-left
        n = x-1;
        i = y+1;
        while((m = testSquareLegality(board, n, i)) != null){
            legalMoves.add(m);
            n--;
            i++;
            if (!m.isEmpty()){
                break;
            }
        }

        // Test down-right
        n = x+1;
        i = y-1;
        while((m = testSquareLegality(board, n, i)) != null){
            legalMoves.add(m);
            n++;
            i--;
            if (!m.isEmpty()){
                break;
            }
        }

        // Test down-left
        n = x-1;
        i = y-1;
        while((m = testSquareLegality(board, n, i)) != null){
            legalMoves.add(m);
            n--;
            i--;
            if (!m.isEmpty()){
                break;
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        String s;

        if(!color){
            s = ANSI.GREEN;
        } else {
            s = ANSI.RED;
        }
        s += "B";
        s += ANSI.RESET;

        return s;
    }
}