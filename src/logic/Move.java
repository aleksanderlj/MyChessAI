package logic;

import logic.pieces.Piece;

import java.util.Arrays;

public class Move {
    int[] currentLocation;
    int[] destinationLocation;
    Piece piece;
    boolean attack;

    public Move(int[] currentLocation, int[] destinationLocation, Piece piece, boolean attack){
        this.currentLocation = currentLocation;
        this.destinationLocation = destinationLocation;
        this.piece = piece;
        this.attack = attack;
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
                '}';
    }
}
