package logic;

import logic.pieces.*;

import java.awt.*;

public class PieceTest {
    public static void main(String[] args) {
        testQueen();
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