package GUI;

import logic.Board;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Table {
    
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    Color lightSquareColor = new Color(193, 138, 84);
    Color darkSquareColor = new Color(71, 30, 7);
    Color borderColor = new Color(54, 52, 52);


    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1000,1000);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(600,500);
    private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(20,20);

    public Table() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenubar = new JMenuBar();
        fillMenuBar(tableMenubar);
        this.gameFrame.setJMenuBar(tableMenubar);

        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);


        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);


        this.gameFrame.setVisible(true);

    }

    private void fillMenuBar(JMenuBar tableMenubar) {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenubar.add(createMenuBarItems());

    }

    private JMenu createMenuBarItems() {
       final JMenu gameMenu = new JMenu("Menu");

       //history item
       final JMenuItem openHistory = new JMenuItem("Load history");
       openHistory.addActionListener(e -> System.out.println("Game history will be here"));
       gameMenu.add(openHistory);

       // exitGame item
       final JMenuItem exitMenuIteam = new JMenuItem("Exit");
       exitMenuIteam.addActionListener(actionEvent -> System.exit(0));
       gameMenu.add(exitMenuIteam);

        return gameMenu;
    }

    private class BoardPanel extends JPanel{

        final List<SquarePanel> boardSquares;
        final int NUM_SQUARES = 64;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardSquares = new ArrayList<>();
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setBackground(borderColor);
            for (int i = 0; i < NUM_SQUARES; i++){
                final SquarePanel squarePanel = new SquarePanel(this, i);
                this.boardSquares.add(squarePanel);
                add(squarePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class SquarePanel extends JPanel{
        private final int squareId;

        SquarePanel(final BoardPanel boardPanel,
                    final int squareId)
        {
            super(new GridBagLayout());
            this.squareId = squareId;
            setPreferredSize(SQUARE_PANEL_DIMENSION);
            assignSquareColor();
            validate();
        }
        private void assignSquareColor() {
            if(squareId%16 >= 0 && squareId%16 < 8) {
                if (squareId % 2 == 0) {
                    this.setBackground(lightSquareColor);
                } else {
                    setBackground(darkSquareColor);
                }
            } else {
                if (squareId % 2 != 0) {
                    this.setBackground(lightSquareColor);
                } else {
                    setBackground(darkSquareColor);
                }
            }


        }
    }

}
