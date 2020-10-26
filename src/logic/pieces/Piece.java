package logic.pieces;

import logic.Board;
import logic.Move;

import java.util.Arrays;
import java.util.List;

public abstract class Piece {
    int[] position;
    Allegiance allegiance; // false = white, true = black
    int baseValue;

    public Piece(int x, int y, Allegiance allegiance){
        this.position = new int[]{x, y};
        this.allegiance = allegiance;
        if(this instanceof Pawn){
            baseValue = 100;
        } else if(this instanceof Knight){
            baseValue = 300;
        } else if(this instanceof Bishop){
            baseValue = 300;
        } else if(this instanceof Rook){
            baseValue = 500;
        } else if(this instanceof Queen){
            baseValue = 900;
        } else if(this instanceof King){
            baseValue = 10000;
        }
    }

    public abstract List<Move> calculateLegalMoves(Board board);

    public Move testSquareLegality(Board board, int x, int y){
        if(!(x > -1 && x < 8 && y > -1 && y < 8)){
            return null;
        } else if (board.getSquare(x, y) == null) {
            return new Move(this.position, new int[]{x, y}, this, true);
        } else if (board.getSquare(x, y).getAllegiance() != this.allegiance) {
            return new Move(this.position, new int[]{x, y}, this, false);
        } else if (board.getSquare(x, y).getAllegiance() == this.allegiance) {
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

    public Allegiance getAllegiance(){
        return this.allegiance;
    }

    public int x(){
        return position[0];
    }

    public int y(){
        return position[1];
    }

    public boolean isWhite(){
        return allegiance == Allegiance.WHITE;
    }

    public boolean isBlack(){
        return allegiance == Allegiance.BLACK;
    }

    @Override
    public String toString() {
        String s;

        if(isWhite()){
            s = ANSI.GREEN;
        } else {
            s = ANSI.RED;
        }

        if(this instanceof Pawn){
            s += "P";
        } else if(this instanceof Knight){
            s += "H";
        } else if(this instanceof Bishop){
            s += "B";
        } else if(this instanceof Rook){
            s += "R";
        } else if(this instanceof Queen){
            s += "Q";
        } else if(this instanceof King){
            s += "K";
        }
        s += ANSI.RESET;

        return s;
    }
}
