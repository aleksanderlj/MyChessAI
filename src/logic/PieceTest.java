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
                new Queen(3, 3, false),
                new Pawn(3, 6, false),
                new Pawn(5, 1, true)
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
                new Bishop(3, 3, false),
                new Pawn(5, 5, false),
                new Pawn(1, 1, true)
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
                new Knight(3, 3, false),
                new Pawn(1, 2, false),
                new Pawn(1, 4, true)
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
                new King(3, 1, false),
                new King(0, 0, false),
                new Pawn(3, 6, true),
                new Pawn(2, 2, true),
                new Pawn(2, 0, false),
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
                new Pawn(3, 1, false),
                new Pawn(7, 7, false),
                new Pawn(3, 6, true),
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
                new Pawn(3, 1, false),
                new Pawn(6, 1, false),
                new Pawn(3, 6, true),
                new Pawn(3, 2, false),
                new Pawn(3, 5, false),
                new Pawn(4, 2, true),
                new Pawn(2, 2, true),
                new Pawn(4, 5, false),
                new Pawn(2, 5, false),
                new Pawn(5, 2, false),
                new Pawn(6, 2, true),
                new Pawn(7, 2, false),
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
                new Rook(3, 4, false),
                new Rook(3, 1, false),
                new Rook(1, 4, true),
                new Rook(6, 4, false),
                new Rook(3, 6, false),
                new Rook(7, 6, false),
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