package logic.pieces;

import logic.Board;
import logic.Move;
import logic.SpecialMove;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    //int[] position;
    int x, y;
    Allegiance allegiance; // false = white, true = black
    int baseValue;

    public Piece(int x, int y, Allegiance allegiance){
        //this.position = new int[]{x, y};
        this.x = x;
        this.y = y;
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

    public Piece(Piece p){
        //this.position = new int[]{p.x(), p.y()};
        this.x = p.x();
        this.y = p.y();
        if(p.allegiance == Allegiance.WHITE){
            this.allegiance = Allegiance.WHITE;
        } else {
            this.allegiance = Allegiance.BLACK;
        }
        this.baseValue = p.baseValue;
    }

    public static Piece clone(Piece p){
        Piece newPiece = null;
        if(p == null){
            return null;
        }
        int[] pos = new int[]{p.x(), p.y()};
        Allegiance alli;
        int baseVal = p.baseValue;

        if(p.allegiance == Allegiance.WHITE){
            alli = Allegiance.WHITE;
        } else {
            alli = Allegiance.BLACK;
        }

        if(p instanceof Pawn) {
            newPiece = new Pawn(p.x(), p.y(), alli);
        } else if(p instanceof Bishop) {
            newPiece = new Bishop(p.x(), p.y(), alli);
        } else if(p instanceof Knight) {
            newPiece = new Knight(p.x(), p.y(), alli);
        } else if(p instanceof Rook) {
            newPiece = new Rook(p.x(), p.y(), alli);
        } else if(p instanceof Queen) {
            newPiece = new Queen(p.x(), p.y(), alli);
        } else if (p instanceof King) {
            newPiece = new King(p.x(), p.y(), alli);
        }

        return newPiece;
    }

    public abstract List<Move> calculateLegalMoves(Board board);

    public Move testSquareLegality(Board board, int x, int y){
        if(!(x > -1 && x < 8 && y > -1 && y < 8)){
            return null;
        } else if (board.getSquare(x, y) == null) {
            //return new Move(this.position, new int[]{x, y}, this, false, null);
            return new Move(getPosition(), new int[]{x, y}, this, false, null);
        } else if (board.getSquare(x, y).getAllegiance() != this.allegiance) {
            //return new Move(this.position, new int[]{x, y}, this, true, board.getSquare(x, y));
            Move m = new Move(getPosition(), new int[]{x, y}, this, true, board.getSquare(x, y));
            if(m.isAttack() && board.getPiece(m.getDestinationLocation()) instanceof King){
                m.setSpecialMove(SpecialMove.KING_CAPTURE);
            }
            return m;
        } else if (board.getSquare(x, y).getAllegiance() == this.allegiance) {
            return null;
        } else {
            return null;
        }
    }

    public int[] getPosition() {
        //return position;
        return new int[]{this.x, this.y};
    }

    public void setPosition(int[] position) {
        //this.position = position;
        this.x = position[0];
        this.y = position[1];
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Allegiance getAllegiance(){
        return this.allegiance;
    }

    public int x(){
        //return position[0];
        return this.x;
    }

    public int y(){
        //return position[1];
        return this.y;
    }

    public boolean isWhite(){
        return allegiance == Allegiance.WHITE;
    }

    public boolean isBlack(){
        return allegiance == Allegiance.BLACK;
    }

    public int getBaseValue() {
        return baseValue;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return x == piece.x &&
                y == piece.y &&
                baseValue == piece.baseValue &&
                allegiance == piece.allegiance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, allegiance, baseValue);
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return baseValue == piece.baseValue &&
                Arrays.equals(position, piece.position) &&
                allegiance == piece.allegiance;
    }



    @Override
    public int hashCode() {
        int result = Objects.hash(allegiance, baseValue);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }

     */
}
