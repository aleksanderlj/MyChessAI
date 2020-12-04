package GUI2;

import AI.Evaluation;
import GUI2.res.MyColors;
import TextGUI.MoveBuilder;
import logic.Board;
import logic.Move;
import logic.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GUI extends JFrame {

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
            setFieldDefaultColor(f, n);
            f.setBorder(BorderFactory.createLineBorder(f.getBackground(), 8));
            f.setBorderPainted(false);
            panel.add(f);
        }
        add(panel);
        setVisible(true);
    }

    private void resetFieldColors() {
        for (int n = 0; n < 8 * 8; n++) {
            setFieldDefaultColor(fields[n], n);
        }
    }

    private void setFieldDefaultColor(Field f, int n) {
        if (n % 16 >= 0 && n % 16 < 8) {
            if (n % 2 == 0) {
                f.setBackground(MyColors.light);
            } else {
                f.setBackground(MyColors.dark);
            }
        } else {
            if (n % 2 != 0) {
                f.setBackground(MyColors.light);
            } else {
                f.setBackground(MyColors.dark);
            }
        }
    }

    public boolean isLight(int fieldId){
        if (fieldId % 16 >= 0 && fieldId % 16 < 8) {
            return fieldId % 2 == 0;
        } else {
            return fieldId % 2 != 0;
        }
    }

    private void setFieldDefaultColor(int[] coords){
        Field f = fields[coord2Id(coords)];
        setFieldDefaultColor(f, coord2Id(coords));
    }

    private void setFieldColor(int[] coords, Color c){
        Field f = fields[coord2Id(coords)];
        //f.setBorderPainted(true);
        f.setBackground(c);
    }

    private void setFieldSelected(int[] coords){
        int id = coord2Id(coords);
        if(isLight(id)){
            setFieldColor(coords, MyColors.orange);
        } else {
            setFieldColor(coords, MyColors.darkorange);
        }
    }

    private void setFieldPreviousMove(int[] coords){
        int id = coord2Id(coords);
        if(isLight(id)){
            setFieldColor(coords, MyColors.blue);
        } else {
            setFieldColor(coords, MyColors.darkblue);
        }
    }

    public void updateGUI(Board board) {
        Piece[][] pieces = board.getBoard();
        List<Move> moveHistory = board.getMoveHistory();
        resetFieldColors();
        for (int n = 0; n < fields.length; n++) {
            setPiece(fields[n], pieces[fields[n].getChessX()][fields[n].getChessY()]);
            fields[n].setBorderPainted(false);
        }

        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.get(moveHistory.size() - 1);
            setFieldPreviousMove(lastMove.getCurrentLocation());
            setFieldPreviousMove(lastMove.getDestinationLocation());
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

    public void announceEnd(Allegiance allegiance){
        JOptionPane.showMessageDialog(this, allegiance.name() + " wins!");
        disableFields();
        gameRunning = false;
    }

    private static boolean playerDone = false;
    private static boolean gameRunning;
    public static void main(String[] args) {
        gameRunning = true;
        Board board = new Board();
        board.initialize();
        //board = getTestBoard();
        PMLImpl pml = new PMLImpl(board);
        GUI gui = new GUI(pml);
        pml.setGui(gui);
        gui.updateGUI(board);

        String[] choices = {"AI vs Human", "Human vs AI", "Human vs Human", "AI vs AI"};
        String input = (String) JOptionPane.showInputDialog(gui, "Choose opponents (White vs Black)", "Start game", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if(input == null){
            // tests
            MoveScript ms = new MoveScript(board, Allegiance.BLACK);
            while(true){
                aiMoves(gui, board, Allegiance.WHITE);
                board.executeMove(ms.next());
                gui.updateGUI(board);
            }
        }

        switch (input){
            case "AI vs Human":
                while (gameRunning) {
                    aiMoves(gui, board, Allegiance.WHITE);
                    waitForPlayer(gui, pml, board, Allegiance.BLACK);
                }
                break;

            case "Human vs AI":
                while (gameRunning) {
                    waitForPlayer(gui, pml, board, Allegiance.WHITE);
                    aiMoves(gui, board, Allegiance.BLACK);
                }
                break;

            case "Human vs Human":
                while (gameRunning) {
                    waitForPlayer(gui, pml, board, Allegiance.WHITE);
                    waitForPlayer(gui, pml, board, Allegiance.BLACK);

                }
                break;

            case "AI vs AI":
                while (gameRunning) {
                    aiMoves(gui, board, Allegiance.WHITE);
                    aiMoves(gui, board, Allegiance.BLACK);
                }
                break;
        }
    }

    public static void waitForPlayer(GUI gui, PMLImpl pml, Board board, Allegiance allegiance){
        if(!gameRunning){
            return;
        }
        playerDone = false;
        pml.setAllegiance(allegiance);
        gui.enableFields();
        List<Move> moves = board.getAllMoves(allegiance);
        board.removeCheckMoves(moves, allegiance);
        if(moves.isEmpty()){
            gui.announceEnd(Evaluation.getOppositeAllegiance(allegiance));
        }
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
        if(!gameRunning){
            return;
        }
        gui.disableFields();
        try {
            final long startTime = System.currentTimeMillis();
            Evaluation.gameNotOverStates = 0;
            Evaluation.minimax(board, Evaluation.START_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, allegiance, -1);
            if(Evaluation.gameNotOverStates == 0) {
                gui.announceEnd(Evaluation.getOppositeAllegiance(allegiance));
            } else {
                board.executeMove(Evaluation.bestMove);
                final long endTime = System.currentTimeMillis();
                System.out.println(Evaluation.bestMove);
                board.visualizeState();
                System.out.println(MoveBuilder.unParse(Evaluation.bestMove));
                System.out.printf("Time taken: %.4fs\n", (endTime - startTime) / 1000.0);
                //System.out.println(Evaluation.nodes);
                //Evaluation.nodes = 0;
            }
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
        boolean choosePiece(int[] coords);
    }

    static class PMLImpl implements PlayerMoveListener {
        Board board;
        GUI gui;
        Allegiance allegiance;

        public PMLImpl(Board board) {
            this.board = board;
        }

        public void setAllegiance(Allegiance allegiance) {
            this.allegiance = allegiance;
        }

        public void setGui(GUI gui) {
            this.gui = gui;
        }

        @Override
        public void playerMoves(int[] coords) {
            Move myMove;
            myMove = MoveBuilder.parseGUI(board, coords, allegiance);

            if (myMove == null) {
                System.out.println("Not a valid move");
                gui.updateGUI(board);
            } else {
                System.out.println(myMove);
                board.executeMove(myMove);
                board.visualizeState();
                playerDone = true;
            }
        }

        @Override
        public boolean choosePiece(int[] coords) {
            boolean valid = false;
            List<Move> moves = board.getAllMoves(allegiance);
            board.removeCheckMoves(moves, allegiance);
            for(Move m : moves){
                if(Arrays.equals(coords, m.getCurrentLocation())){
                    gui.setFieldSelected(m.getCurrentLocation());
                    gui.setFieldSelected(m.getDestinationLocation());
                    valid = true;
                }
            }
            return valid;
        }
    }

    public static Board getTestBoard(){
        Board board = new Board();
        Piece[] arr = {
                new Rook(4, 0, Allegiance.WHITE),
                new Queen(4, 7, Allegiance.BLACK),
                new Bishop(5, 3, Allegiance.WHITE),
                new King(3, 3, Allegiance.BLACK),
                new King(6, 7, Allegiance.WHITE)
        };

        for (Piece p : arr) {
            board.placePiece(p);
        }
        return board;
    }
}
