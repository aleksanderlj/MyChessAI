package logic;

import logic.pieces.Piece;

import java.util.Arrays;

public class Move {
    int[] currentLocation;
    int[] destinationLocation;
    Piece piece;
    boolean empty;

    public Move(int[] currentLocation, int[] destinationLocation, Piece piece, boolean empty){
        this.currentLocation = currentLocation;
        this.destinationLocation = destinationLocation;
        this.piece = piece;
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
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
