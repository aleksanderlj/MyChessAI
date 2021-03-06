package logic;

import logic.pieces.*;

import java.awt.*;

public class PieceTest {
    public static void main(String[] args) {
        testEnPassantWhite();
    }

    private static void testEnPassantWhite(){
        Board board = new Board();

        Piece[] arr = {
                new Pawn(3, 6, Allegiance.BLACK),
                new Pawn(4, 4, Allegiance.WHITE),
                new Pawn(2, 4, Allegiance.WHITE)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        /*
        for(Move m : board.getAllMoves(Allegiance.BLACK)){
            if(m.destinationLocation[0] == 3 && m.destinationLocation[1] == 5){
                board.executeMove(m);
            }
        }

         */

        for(Move m : board.getAllMoves(Allegiance.BLACK)){
            if(m.destinationLocation[0] == 3 && m.destinationLocation[1] == 4){
                board.executeMove(m);
            }
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeSpecialMoves(Allegiance.WHITE, SpecialMove.EN_PASSANT);

        /*
        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 7 && m.destinationLocation[1] == 1){
                board.executeMove(m);
            }
        }

        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 7 && m.destinationLocation[1] == 0){
                board.executeMove(m);
            }
        }

         */

        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if (m.getSpecialMove() == SpecialMove.EN_PASSANT){
                board.executeMove(m);
                break;
            }
        }
        board.visualizeState();

    }

    private static void testEnPassantBlack(){
        Board board = new Board();

        Piece[] arr = {
                new Pawn(3, 1, Allegiance.WHITE),
                new Pawn(4, 3, Allegiance.BLACK),
                new Pawn(2, 3, Allegiance.BLACK)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 3 && m.destinationLocation[1] == 3){
                board.executeMove(m);
            }
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeSpecialMoves(Allegiance.BLACK, SpecialMove.EN_PASSANT);

        /*
        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 7 && m.destinationLocation[1] == 1){
                board.executeMove(m);
            }
        }

        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 7 && m.destinationLocation[1] == 0){
                board.executeMove(m);
            }
        }

         */

        for(Move m : board.getAllMoves(Allegiance.BLACK)){
            if (m.getSpecialMove() == SpecialMove.EN_PASSANT){
                board.executeMove(m);
                break;
            }
        }
        board.visualizeState();

    }

    private static void testCastling(){
        Board board = new Board();

        Piece[] arr = {
                new King(4, 0, Allegiance.WHITE),
                new Rook(7, 0, Allegiance.WHITE),
                new Rook(0, 0, Allegiance.WHITE),
                new Rook(3, 4, Allegiance.BLACK)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeSpecialMoves(Allegiance.WHITE, SpecialMove.CASTLING);

        /*
        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 7 && m.destinationLocation[1] == 1){
                board.executeMove(m);
            }
        }

        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if(m.destinationLocation[0] == 7 && m.destinationLocation[1] == 0){
                board.executeMove(m);
            }
        }

         */

        for(Move m : board.getAllMoves(Allegiance.WHITE)){
            if (m.getSpecialMove() == SpecialMove.CASTLING){
                board.executeMove(m);
            }
        }
        board.visualizeState();

    }

    private static void testPromotion(){
        Board board = new Board();

        Piece[] arr = {
                new Pawn(3, 6, Allegiance.WHITE),
                new Pawn(3, 1, Allegiance.BLACK)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.executeMove(arr[0].calculateLegalMoves(board).get(0));
        board.executeMove(arr[1].calculateLegalMoves(board).get(0));
        board.visualizeState();
        System.out.println(arr[0].y());
    }

    private static void testQueen() {
        Board board = new Board();

        Piece[] arr = {
                new Queen(3, 3, Allegiance.WHITE),
                new Pawn(3, 6, Allegiance.WHITE),
                new Pawn(5, 1, Allegiance.BLACK)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(arr[0]);
    }

    private static void testBishop() {
        Board board = new Board();

        Piece[] arr = {
                new Bishop(3, 3, Allegiance.WHITE),
                new Pawn(5, 5, Allegiance.WHITE),
                new Pawn(1, 1, Allegiance.BLACK)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(arr[0]);
    }

    private static void testKnight() {
        Board board = new Board();

        Piece[] arr = {
                new Knight(3, 3, Allegiance.WHITE),
                new Pawn(1, 2, Allegiance.WHITE),
                new Pawn(1, 4, Allegiance.BLACK)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(arr[0]);
    }

    private static void testKing() {
        Board board = new Board();

        Piece[] arr = {
                new King(3, 1, Allegiance.WHITE),
                new King(0, 0, Allegiance.WHITE),
                new Pawn(3, 6, Allegiance.BLACK),
                new Pawn(2, 2, Allegiance.BLACK),
                new Pawn(2, 0, Allegiance.WHITE),
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(arr[0]);
        board.visualizeLegalMoves(arr[1]);
    }

    private static void testPawn1() {
        Board board = new Board();

        Piece[] arr = {
                new Pawn(3, 1, Allegiance.WHITE),
                new Pawn(7, 7, Allegiance.WHITE),
                new Pawn(3, 6, Allegiance.BLACK),
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(arr[0]);
        board.visualizeLegalMoves(arr[1]);
        System.out.println("----BLACK-----");
        board.visualizeLegalMoves(arr[2]);
    }

    private static void testPawn2() {
        Board board = new Board();

        Piece[] arr = {
                new Pawn(3, 1, Allegiance.WHITE),
                new Pawn(6, 1, Allegiance.WHITE),
                new Pawn(3, 6, Allegiance.BLACK),
                new Pawn(3, 2, Allegiance.WHITE),
                new Pawn(3, 5, Allegiance.WHITE),
                new Pawn(4, 2, Allegiance.BLACK),
                new Pawn(2, 2, Allegiance.BLACK),
                new Pawn(4, 5, Allegiance.WHITE),
                new Pawn(2, 5, Allegiance.WHITE),
                new Pawn(5, 2, Allegiance.WHITE),
                new Pawn(6, 2, Allegiance.BLACK),
                new Pawn(7, 2, Allegiance.WHITE),
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(arr[0]);
        board.visualizeLegalMoves(arr[1]);
        board.visualizeLegalMoves(arr[3]);
        board.visualizeLegalMoves(arr[4]);
        System.out.println("----BLACK-----");
        board.visualizeLegalMoves(arr[2]);
    }

    private static void testRook() {
        Board board = new Board();

        Piece[] arr = {
                new Rook(3, 4, Allegiance.WHITE),
                new Rook(3, 1, Allegiance.WHITE),
                new Rook(1, 4, Allegiance.BLACK),
                new Rook(6, 4, Allegiance.WHITE),
                new Rook(3, 6, Allegiance.WHITE),
                new Rook(7, 6, Allegiance.WHITE),
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("--------------");
        board.visualizeLegalMoves(arr[0]);
        board.visualizeLegalMoves(arr[1]);
        board.visualizeLegalMoves(arr[5]);
    }
}


// Notes
// Tur kan ses på runde nummer. Lige tal er hvid, ulige er sort.