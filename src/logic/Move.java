package logic;

import logic.pieces.Piece;

public class Move {
    int[] currentLocation;
    int[] destinationLocation;
    Piece piece;

    public Move(int[] currentLocation, int[] destinationLocation, Piece piece){
        this.currentLocation = currentLocation;
        this.destinationLocation = destinationLocation;
        this.piece = piece;
    }

}
