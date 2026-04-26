import java.awt.*;
import javax.swing.*;

public class GameBoard
{
    private JFrame boardFrame = new JFrame();
    private JPanel boardPanel = new JPanel();
    private GridLayout boardLayout = new GridLayout(4, 5);

    private GameSquare square00 = new GameSquare("hole");
    private GameSquare square01 = new GameSquare("hole");
    private GameSquare square02 = new GameSquare("hole");
    private GameSquare square03 = new GameSquare("hole");
    private GameSquare square04 = new GameSquare("hole");
    private GameSquare square10 = new GameSquare("hole");
    private GameSquare square11 = new GameSquare("hole");
    private GameSquare square12 = new GameSquare("hole");
    private GameSquare square13 = new GameSquare("hole");
    private GameSquare square14 = new GameSquare("hole");
    private GameSquare square20 = new GameSquare("hole");
    private GameSquare square21 = new GameSquare("hole");
    private GameSquare square22 = new GameSquare("hole");
    private GameSquare square23 = new GameSquare("hole");
    private GameSquare square24 = new GameSquare("hole");
    private GameSquare square30 = new GameSquare("hole");
    private GameSquare square31 = new GameSquare("hole");
    private GameSquare square32 = new GameSquare("hole");
    private GameSquare square33 = new GameSquare("hole");
    private GameSquare square34 = new GameSquare("hole");




    public GameBoard()
    {
        boardPanel.setLayout(boardLayout);

        boardPanel.add(square00);
        boardPanel.add(square01);
        boardPanel.add(square02);
        boardPanel.add(square03);
        boardPanel.add(square04);
        boardPanel.add(square10);
        boardPanel.add(square11);
        boardPanel.add(square12);
        boardPanel.add(square13);
        boardPanel.add(square14);
        boardPanel.add(square20);
        boardPanel.add(square21);
        boardPanel.add(square22);
        boardPanel.add(square23);
        boardPanel.add(square24);
        boardPanel.add(square30);
        boardPanel.add(square31);
        boardPanel.add(square32);
        boardPanel.add(square33);
        boardPanel.add(square34);

        boardFrame.setContentPane(boardPanel);
        boardFrame.setTitle("Snow Game");
        boardFrame.setSize(800, 640);
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setVisible(true);
    }

}