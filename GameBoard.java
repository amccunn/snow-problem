import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameBoard implements ActionListener
{
    private JFrame boardFrame = new JFrame();
    private JPanel boardPanel = new JPanel();
    private int gameBoardWidth = 5;
    private int gameBoardHeight = 4;
    private GridLayout boardLayout = new GridLayout(gameBoardHeight, gameBoardWidth);

    private GameSquare[][] squaresArray = new GameSquare[gameBoardHeight][gameBoardWidth];

    public GameBoard(Boolean previewBool, String[][] squareNames)
    {
        boardPanel.setLayout(boardLayout);

        for (int i = 0; i < gameBoardHeight; i++)
        {
            for (int j = 0; j < gameBoardWidth; j++)
            {
                squaresArray[i][j] = new GameSquare(squareNames[i][j], j, i);
                boardPanel.add(squaresArray[i][j]);
                if (!previewBool)
                {
                    squaresArray[i][j].addActionListener(this);
                }
            }
        }

        if (!previewBool)
        {
            boardFrame.setContentPane(boardPanel);
            boardFrame.setTitle("Snow Game");
            boardFrame.setSize(800, 640);
            boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            boardFrame.setVisible(true);
        }
    }

    public GameSquare[][] getSquaresArray()
    {
        return squaresArray;
    }
    
    public JPanel getBoardPanel()
    {
        return this.boardPanel;
    }

    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < gameBoardHeight; i++)
        {
            for (int j = 0; j < gameBoardWidth; j++)
            {
                if (e.getSource() == squaresArray[i][j])
                {
                    
                }
            }
        }
    }

}