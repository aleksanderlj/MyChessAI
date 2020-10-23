package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.List;

public abstract class Piece {
    int[] position;
    boolean color; // false = white, true = black

    public Piece(int x, int y, boolean color){
        this.position = new int[]{x, y};
        this.color = color;
    }

    //public abstract void move();

    public abstract List<Move> calculateLegalMoves(Board board);

    public Move testSquareLegality(Board board, int x, int y){
        if (board.getSquare(x, y) == null) {
            return new Move(this.position, new int[]{x, y}, this);
        } else if (board.getSquare(x, y).getColor() != this.color) {
            return new Move(this.position, new int[]{x, y}, this);
        } else if (board.getSquare(x, y).getColor() == this.color) {
            return null;
        } else {
            return null;
        }
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean getColor(){
        return this.color;
    }

    public int x(){
        return position[0];
    }

    public int y(){
        return position[1];
    }
}
