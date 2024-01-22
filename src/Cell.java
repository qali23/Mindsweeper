import java.awt.Frame;
import java.awt.*;
abstract public class Cell {
    public int row;
    public int column;
    public char c;
    public boolean state_hidden = true;

    public Cell(int x , int y){
        this.row = x;
        this.column = y;
    }

    //public Button button;

//    public Cell(){
//        button = new Button("X");
//    }
//
//    public void changeLabelButton(){
//        if (!this.state_hidden){
//            this.button.setLabel(""+this.c);
//        }
//    }





}
