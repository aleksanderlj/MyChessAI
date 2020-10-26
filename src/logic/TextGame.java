package logic;

public class TextGame {
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        int round = 1;

        board.executeMove(board.getAllWhiteMoves().get(1));
        board.visualizeState();

        board.reverseMove();
        board.visualizeState();

    }
}
