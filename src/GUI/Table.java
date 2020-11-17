package GUI;



import logic.Board;
import logic.Move;
import logic.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessboard;

    private Piece sourceSquare;
    private Piece destinationSquare;
    private Piece humanMovedPiece;

    Color lightSquareColor = new Color(193, 138, 84);
    Color darkSquareColor = new Color(109, 48, 13);
    Color borderColor = new Color(54, 52, 52);



    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1200,1200);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(600,600);
    private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(20,20);

    private static String pieceImagePath = "pieceArt/pieces" +
            "";

    public Table() throws IOException {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenubar = new JMenuBar();
        fillMenuBar(tableMenubar);
        this.gameFrame.setJMenuBar(tableMenubar);

        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessboard = new Board();
        chessboard.initialize();


        this.boardPanel =  new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);

        assignStartBoard(chessboard);

    }

    private int getCoord(int[] coords){
        return (coords[1]*8)+coords[0];
    }


    private int[] getCoords(int n){
        if(n == 0){
            return new int[]{0,0};
        }

        return new int[]{n%8, n/8};
    }

    private void assignStartBoard(final Board board){
        //this.boardPanel.removeAll();

        try{
            final File filepathWR = new File("pieceArt/pieces/WR.gif");
            final BufferedImage imageWR = ImageIO.read(filepathWR);
            List<Piece> list = board.getAllPieces();



           for(Piece p : list){
                SquarePanel sp = boardPanel.boardSquares.get(getCoord(p.getPosition()));

                sp.add(new JLabel(new ImageIcon(imageWR)));

            }

        }catch (Exception e){}

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
            assignPieceIcon(chessboard);


            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent mouseEvent) {
                    if (isRightMouseButton(mouseEvent)){
                        // cancel the move
                        sourceSquare = null;
                        destinationSquare = null;
                        humanMovedPiece = null;

                    }else if (isLeftMouseButton(mouseEvent)){
                        if (sourceSquare == null){
                            // need to fix the square id it should give x and y cord
                           // sourceSquare = chessboard.getSquare(squareId);
                            // need a method for getPiece
                           // humanMovedPiece = sourceSquare.getPiece();
                            if (humanMovedPiece == null){
                                sourceSquare = null;
                            }
                        }else {
                           // destinationSquare = chessboard.getSquare(squareId);
                            // logic from Move class, to actually move the piece
                            final Move move = null;
                        }

                        // TODO Here we wanna move the piece, have to get the squares x and y first tho!


                    }
                }

                @Override
                public void mousePressed(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(final MouseEvent mouseEvent) {

                }
            });

            validate();
        }

        private void assignPieceIcon(final Board board){
            this.removeAll();
            int[] coords = getCoords(this.squareId);
            //if (board.getSquare(coords[0], coords[1]))
            // TODO : need to get the x and y postion of the square from logic.Board


            for(Piece p : board.getAllPieces()){
                getCoord(p.getPosition());
            }
        }


        private void assignSquareColor() {
            if(squareId % 16 >= 0 && squareId % 16 < 8) {
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
