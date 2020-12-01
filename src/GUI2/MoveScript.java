package GUI2;

import TextGUI.MoveBuilder;
import logic.Board;
import logic.Move;
import logic.pieces.Allegiance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MoveScript {
    File f;
    Scanner sc;
    Board board;
    Allegiance allegiance;

    public MoveScript(Board board, Allegiance allegiance){
        this.board = board;
        this.allegiance = allegiance;
        f = new File(getClass().getResource("res/movescript").getPath());
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(allegiance == Allegiance.BLACK){
            sc.nextLine();
        }
    }

    public Move next(){
        Move m = null;
        if(sc.hasNextLine()){
            String sMove = sc.nextLine();
            m = MoveBuilder.parse(board, sMove, allegiance);
            if(sc.hasNextLine()) sc.nextLine();
        } else {
            sc.close();
        }
        return m;
    }
}
