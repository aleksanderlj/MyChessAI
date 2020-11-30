package GUI2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Field extends JButton implements ActionListener {
    int id;
    static int clicks = 0;
    static int[] coords = new int[4];
    GUI.PlayerMoveListener pml;
    public Field(int id, GUI.PlayerMoveListener pml){
        this.id = id;
        this.addActionListener(this);
        this.pml = pml;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clicks++;
        switch (clicks){
            case 1:
                coords[0] = getChessX();
                coords[1] = getChessY();
                break;

            case 2:
                coords[2] = getChessX();
                coords[3] = getChessY();
                pml.playerMoves(coords);
                clicks = 0;
                break;
        }
    }


    public int getId() {
        return id;
    }


    public int getChessX(){
        if(id == 0){
            return 0;
        }

        return id%8;
    }


    public int getChessY(){
        if(id == 0){
            return 7;
        }
        return 7-id/8;
    }
}
