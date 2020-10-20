package logic.pieces;

import logic.Move;

public abstract class Piece {
    int[] position;
    boolean color; // false = white, true = black

    public Piece(int x, int y, boolean color){
        this.position = new int[]{x, y};
    }

    public abstract void move();

    public abstract Move[] calculateLegalMoves();

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}
