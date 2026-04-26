import java.awt.*;
import javax.swing.*;

public class Driver
{
    public static void main(String[] args) 
    {
        ImageIcon blueHead = new ImageIcon("head_blue.png");
        GameBoard myBoard = new GameBoard();
        myBoard.getSquaresArray()[0][0].setIcon(blueHead);
    }
}