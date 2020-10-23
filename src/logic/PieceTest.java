package logic;

import logic.pieces.*;

public class PieceTest {
    public static void main(String[] args) {
        testKing();
        testPawn1();
        testRook();
    }

    private static void testKing(){
        Board board = new Board();
        Piece king = new King(3, 1, false);
        Piece king2 = new King(0, 0, false);
        Piece pawn1 = new Pawn(3, 6, true);
        Piece pawn2 = new Pawn(2, 2, true);
        Piece pawn3 = new Pawn(2, 0, false);

        board.placePiece(king);
        board.placePiece(king2);
        board.placePiece(pawn1);
        board.placePiece(pawn2);
        board.placePiece(pawn3);

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(king);
        board.visualizeLegalMoves(king2);
    }

    private static void testPawn1(){
        Board board = new Board();
        Piece pawnWhite = new Pawn(3, 1, false);
        Piece pawnWhite2 = new Pawn(7, 7, false);
        Piece pawnBlack = new Pawn(3, 6, true);

        board.placePiece(pawnWhite);
        board.placePiece(pawnWhite2);
        board.placePiece(pawnBlack);

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(pawnWhite);
        board.visualizeLegalMoves(pawnWhite2);
        System.out.println("----BLACK-----");
        board.visualizeLegalMoves(pawnBlack);
    }

    private static void testPawn2(){
        Board board = new Board();
        Piece pawnWhite = new Pawn(3, 1, false);
        Piece pawnBlack = new Pawn(3, 6, true);
        Piece pawn3 = new Pawn(3, 2, false);
        Piece pawn4 = new Pawn(3, 5, false);
        Piece pawn5 = new Pawn(4, 2, true);
        Piece pawn6 = new Pawn(2, 2, true);
        Piece pawn7 = new Pawn(4, 5, false);
        Piece pawn8 = new Pawn(2, 5, false);

        board.placePiece(pawnWhite);
        board.placePiece(pawnBlack);
        board.placePiece(pawn3);
        board.placePiece(pawn4);
        board.placePiece(pawn5);
        board.placePiece(pawn6);
        board.placePiece(pawn7);
        board.placePiece(pawn8);

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("----WHITE-----");
        board.visualizeLegalMoves(pawnWhite);
        board.visualizeLegalMoves(pawn3);
        board.visualizeLegalMoves(pawn4);
        System.out.println("----BLACK-----");
        board.visualizeLegalMoves(pawnBlack);
    }

    private static void testRook(){
        Board board = new Board();
        Piece rook1 = new Rook(3, 4, false);
        Piece rook2 = new Rook(3, 1, false);
        Piece rook3 = new Rook(1, 4, false);
        Piece rook4 = new Rook(6, 4, false);
        Piece rook5 = new Rook(3, 6, false);
        Piece rook6 = new Rook(7, 6, false);

        board.placePiece(rook1);
        board.placePiece(rook2);
        board.placePiece(rook3);
        board.placePiece(rook4);
        board.placePiece(rook5);
        board.placePiece(rook6);

        System.out.println("--------------");
        board.visualizeState();
        System.out.println("--------------");
        board.visualizeLegalMoves(rook1);
        board.visualizeLegalMoves(rook2);
        board.visualizeLegalMoves(rook6);
    }
}


// Notes
// Tur kan ses på runde nummer. Lige tal er hvid, ulige er sort.