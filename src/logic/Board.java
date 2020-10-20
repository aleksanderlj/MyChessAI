package logic;

import logic.pieces.Piece;

public class Board {

    Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    public void initialize(){

    }

    public Piece getSquare(int x, int y){
        return board[x][y];
    }

}
