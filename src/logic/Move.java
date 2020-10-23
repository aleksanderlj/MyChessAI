package logic;

import logic.pieces.Piece;

import java.util.Arrays;

public class Move {
    int[] currentLocation;
    int[] destinationLocation;
    Piece piece;

    public Move(int[] currentLocation, int[] destinationLocation, Piece piece){
        this.currentLocation = currentLocation;
        this.destinationLocation = destinationLocation;
        this.piece = piece;
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
