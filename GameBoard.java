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

    /**
     * @param previewBool if true then create only a preview and dont add listeners
     * @param squareNames a 2d array which represents what should be in each square
     * if its just a preview then I can just use the boardPanel
     */
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

        //creates the all details regarding the frame only if its not a preview
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
        return this.squaresArray;
    }
    
    public JPanel getBoardPanel()
    {
        return this.boardPanel;
    }

    //returns an array of what the squares around the currentSquare are {up, right, down, left}
    public GameSquare[] checkAdjacentSquares(GameSquare currentSquare)
    {
        GameSquare[] adjacentSquares = new GameSquare[4];

        //check up
        if (currentSquare.getCords()[1] > 0)
        {
            adjacentSquares[0] = squaresArray[currentSquare.getCords()[1] - 1][currentSquare.getCords()[0]];
        }

        //check down
        if (currentSquare.getCords()[1] < 3)
        {
            adjacentSquares[2] = squaresArray[currentSquare.getCords()[1] + 1][currentSquare.getCords()[0]];
        }

        //check right
        if (currentSquare.getCords()[0] < 4)
        {
            adjacentSquares[1] = squaresArray[currentSquare.getCords()[1]][currentSquare.getCords()[0] + 1];
        }

        //check left
        if (currentSquare.getCords()[0] > 0)
        {
            adjacentSquares[3] = squaresArray[currentSquare.getCords()[1]][currentSquare.getCords()[0] - 1];
        }

        return adjacentSquares;
    }

    public void promptAction(GameSquare currentSquare)
    {
        GameBoard[] adjacentSquares = checkAdjacentSquares(currentSquare);
        String[] directions = {"up", "right", "down", "left"};

        for (int i = 0; i < 4; i++)
        {
            if (adjacentSquares[i].getName() == "hole")
            {
                adjacentSquares[i].setIcon
            }
        }
    }

    //listeners for the squares on the screen
    public void actionPerformed(ActionEvent e)
    {
        //the only things you can press in gameboard are the gamesquares so shouldnt ever break
        GameSquare clickedSquare = (GameSquare) e.getSource();

        if (clickedSquare.canBeSelected())
        {
            //deSelects everything selected on board before selecting new item
            for (int i = 0; i < gameBoardHeight; i++)
            {
                for (int j = 0; j < gameBoardWidth; j++)
                {
                    if (squaresArray[i][j].getSelectedBoolean())
                    {
                        squaresArray[i][j].deSelect();
                    }
                }
            }
            clickedSquare.selected();

            
        }

    }

}