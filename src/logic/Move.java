package logic;

import com.sun.xml.internal.ws.api.Component;
import logic.pieces.Piece;

import java.util.Arrays;
import java.util.Objects;

public class Move implements Comparable<Move> {
    int[] currentLocation;
    int[] destinationLocation;
    Piece piece;
    boolean attack;
    int heuristicValue;
    Piece attackedPiece;

    public Move(int[] currentLocation, int[] destinationLocation, Piece piece, boolean attack, Piece attackedPiece){
        this.currentLocation = currentLocation;
        this.destinationLocation = destinationLocation;
        this.piece = piece;
        this.attack = attack;
        this.attackedPiece = attackedPiece;
    }

    public boolean isAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return "Move{" +
                "currentLocation=" + Arrays.toString(currentLocation) +
                ", destinationLocation=" + Arrays.toString(destinationLocation) +
                ", piece=" + piece +
                ", heuristicValue=" + heuristicValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return attack == move.attack &&
                Arrays.equals(currentLocation, move.currentLocation) &&
                Arrays.equals(destinationLocation, move.destinationLocation) &&
                Objects.equals(piece, move.piece);
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    @Override
    public int compareTo(Move o) {
        return this.heuristicValue - o.getHeuristicValue();
    }
}
