package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.List;

public abstract class Piece {
    int[] position;
    boolean color; // false = white, true = black

    public Piece(int x, int y, boolean color){
        this.position = new int[]{x, y};
    }

    //public abstract void move();

    public abstract List<Move> calculateLegalMoves(Board board);

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean getColor(){
        return this.color;
    }
}
