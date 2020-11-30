package GUI2;

import AI.Evaluation;
import TextGUI.MoveBuilder;
import logic.Board;
import logic.Move;
import logic.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    Color dark = new Color(71, 116, 54);
    Color light = new Color(198, 231, 185);
    Color blue = new Color(93, 161, 231);
    JPanel panel = new JPanel();
    Field[] fields = new Field[8 * 8];

    public GUI(PlayerMoveListener pml) {
        super("Chess");
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(8, 8));

        for (int n = 0; n < 8 * 8; n++) {
            Field f = new Field(n, pml);
            fields[n] = f;
            setFieldColor(f, n);
            f.setBorderPainted(false);
            panel.add(f);
        }
        add(panel);
        setVisible(true);
    }

    private void resetFieldColors() {
        for (int n = 0; n < 8 * 8; n++) {
            setFieldColor(fields[n], n);
        }
    }

    private void setFieldColor(Field f, int n) {
        if (n % 16 >= 0 && n % 16 < 8) {
            if (n % 2 == 0) {
                f.setBackground(light);
            } else {
                f.setBackground(dark);
            }
        } else {
            if (n % 2 != 0) {
                f.setBackground(light);
            } else {
                f.setBackground(dark);
            }
        }
    }

    public void updateGUI(Board board) {
        Piece[][] pieces = board.getBoard();
        List<Move> moveHistory = board.getMoveHistory();
        resetFieldColors();
        for (int n = 0; n < fields.length; n++) {
            setPiece(fields[n], pieces[fields[n].getChessX()][fields[n].getChessY()]);
        }

        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.get(moveHistory.size() - 1);
            fields[coord2Id(lastMove.getCurrentLocation())].setBackground(blue);
            fields[coord2Id(lastMove.getDestinationLocation())].setBackground(blue);
        }

    }

    private int coord2Id(int[] coords) {
        return ((7 - coords[1]) * 8) + coords[0];
    }

    public void disableFields() {
        for (Field f : fields) {
            f.setEnabled(false);
        }
    }

    public void enableFields() {
        for (Field f : fields) {
            f.setEnabled(true);
        }
    }

    public void setPiece(Field f, Piece p) {
        if (p == null) {
            f.setIcon(null);
            return;
        }

        String s = "";

        if (p instanceof Pawn) {
            s += "P";
        } else if (p instanceof Knight) {
            s += "H";
        } else if (p instanceof Bishop) {
            s += "B";
        } else if (p instanceof Rook) {
            s += "R";
        } else if (p instanceof Queen) {
            s += "Q";
        } else if (p instanceof King) {
            s += "K";
        }

        if (p.getAllegiance() == Allegiance.WHITE) {
            s += "W";
        } else {
            s += "B";
        }

        f.setIcon(new ImageIcon(getClass().getResource("res/" + s + ".png")));
        f.setDisabledIcon(new ImageIcon(getClass().getResource("res/" + s + ".png")));
    }

    private static boolean playerDone = false;
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        PMLImpl pml = new PMLImpl(board);
        GUI gui = new GUI(pml);
        gui.updateGUI(board);


        while (true) {
            aiMoves(gui, board, Allegiance.WHITE);
            waitForPlayer(gui, pml, board, Allegiance.BLACK);
        }

        /*
        while (true) {
            gui.disableFields();
            aiMoves(board, Allegiance.WHITE);
            gui.updateGUI(board);

            gui.disableFields();
            aiMoves(board, Allegiance.BLACK);
            gui.updateGUI(board);
        }
         */
    }

    public static void waitForPlayer(GUI gui, PMLImpl pml, Board board, Allegiance allegiance){
        playerDone = false;
        pml.setAllegiance(allegiance);
        gui.enableFields();
        while (!playerDone) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gui.updateGUI(board);
    }

    public static void aiMoves(GUI gui, Board board, Allegiance allegiance) {
        gui.disableFields();
        try {
            final long startTime = System.currentTimeMillis();
            Evaluation.minimax(board, Evaluation.START_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, allegiance);
            board.executeMove(Evaluation.bestMove);
            final long endTime = System.currentTimeMillis();
            System.out.println(Evaluation.bestMove);
            board.visualizeState();
            System.out.println(MoveBuilder.unParse(Evaluation.bestMove));
            System.out.printf("Time taken: %.4fs\n", (endTime - startTime) / 1000.0);
        } catch (Exception e) {
            System.out.println(movesToText(board.getMoveHistory()));
            e.printStackTrace();
        }
        gui.updateGUI(board);
    }

    public static String movesToText(List<Move> moveList) {
        StringBuilder s = new StringBuilder();
        for (Move m : moveList) {
            s.append(MoveBuilder.unParse(m));
            s.append("\n");
        }
        return s.toString();
    }

    interface PlayerMoveListener {
        public void playerMoves(int[] coords);
    }

    static class PMLImpl implements PlayerMoveListener {
        Board board;
        Allegiance allegiance;

        public PMLImpl(Board board) {
            this.board = board;
        }

        public void setAllegiance(Allegiance allegiance) {
            this.allegiance = allegiance;
        }

        @Override
        public void playerMoves(int[] coords) {
            Move myMove;
            myMove = MoveBuilder.parseGUI(board, coords, allegiance);

            if (myMove == null) {
                System.out.println("Not a valid move");
            } else {
                System.out.println(myMove);
                board.executeMove(myMove);
                board.visualizeState();
                playerDone = true;
            }
        }
    }
}
