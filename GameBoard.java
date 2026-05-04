import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameBoard implements ActionListener
{
    private int levelNumber;
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
    public GameBoard(Boolean previewBool, String[][] squareNames, int level)
    {
        this.levelNumber = level;

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

    public int getLevelNumber()
    {
        return this.levelNumber;
    }

    public GameSquare[][] getSquaresArray()
    {
        return this.squaresArray;
    }
    
    public JPanel getBoardPanel()
    {
        return this.boardPanel;
    }

    public JFrame getFrame()
    {
        return this.boardFrame;
    }

    public void setSquaresArray(int x, int y, GameSquare square)
    {
        this.squaresArray[y][x] = (GameSquare) square;
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

    //take the current square and put arrows on available squares next to it
    public void promptAction(GameSquare currentSquare)
    {
        GameSquare[] adjacentSquares = checkAdjacentSquares(currentSquare);
        String[] directions = {"up", "right", "down", "left"};

        for (int i = 0; i < 4; i++)
        {
            //make sure its a GameSquare not null before calling GameSquare methods
            if (adjacentSquares[i] != null)
            {
                if (adjacentSquares[i].getName().equals("hole") && !currentSquare.getName().startsWith("head_"))
                {
                    ImageIcon arrowIcon = new ImageIcon(directions[i] + "_arrow.png");
                    adjacentSquares[i].setIcon(arrowIcon);
                    adjacentSquares[i].setName(directions[i] + "_arrow");
                }
                else if (adjacentSquares[i].getName().equals("snowball_large") && currentSquare.getName().equals("snowball_small"))
                {
                    adjacentSquares[i].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    adjacentSquares[i].setStackable(true);
                    currentSquare.setStackable(true);
                }
                else if (adjacentSquares[i].getName().equals("snowman_stack") && currentSquare.getName().startsWith("head_"))
                {
                    adjacentSquares[i].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    adjacentSquares[i].setStackable(true);
                    currentSquare.setStackable(true);
                }

            }
            
        }
    }

    //listeners for the squares on the screen
    public void actionPerformed(ActionEvent e)
    {
        //the only things you can press in gameboard are the gamesquares so shouldnt ever break
        GameSquare clickedSquare = (GameSquare) e.getSource();

        System.out.println(clickedSquare.getCords()[0] + " " + clickedSquare.getCords()[1]);
        System.out.println(clickedSquare.getName());

        if (clickedSquare.canBeSelected(this))
        {
            Boolean arrowClicked = false;
            //need to check if arrow has been clicked
            if (clickedSquare.getName().endsWith("_arrow"))
            {
                arrowClicked = true;
            }
            
            System.out.println(clickedSquare.getName());

            //move
            if (arrowClicked)
            {
                System.out.println("square moving " + clickedSquare.getName());
                //what direction to move
                if (clickedSquare.getName().startsWith("up"))
                {
                    //go below the up arrow to get the square to  be moved
                    squaresArray[clickedSquare.getCords()[1] + 1][clickedSquare.getCords()[0]].gameMove("up", this);
                }
                else if (clickedSquare.getName().startsWith("down"))
                {
                    //go above the down arrow to get the square to  be moved
                    squaresArray[clickedSquare.getCords()[1] - 1][clickedSquare.getCords()[0]].gameMove("down", this);
                }
                else if (clickedSquare.getName().startsWith("left"))
                {
                    //go to the right the left arrow to get the square to  be moved
                    squaresArray[clickedSquare.getCords()[1]][clickedSquare.getCords()[0] + 1].gameMove("left", this);
                }
                else if (clickedSquare.getName().startsWith("right"))
                {
                    //go to the left the right arrow to get the square to  be moved
                    squaresArray[clickedSquare.getCords()[1]][clickedSquare.getCords()[0] - 1].gameMove("right", this);
                }
            }

            if (clickedSquare.getName().startsWith("head_"))
            {
                clickedSquare.setStackable(true);
            }

            System.out.println(clickedSquare.isStackable());
            //stack the snowballs if stackable
            if (clickedSquare.isStackable())
            {
                GameSquare[] adjacentSquares = checkAdjacentSquares(clickedSquare);
                System.out.println("Stackable");

                //find the square next to the current one that is getting stacked
                for (int i = 0; i < adjacentSquares.length; i++)
                {
                    if (adjacentSquares[i] != null)
                    {
                        if (adjacentSquares[i].isStackable() && (!adjacentSquares[i].getName().startsWith("head_") || clickedSquare.getName().equals("snowman_stack")))
                        {
                            clickedSquare.stack(adjacentSquares[i]);
                            clickedSquare.setStackable(false);
                            adjacentSquares[i].setStackable(false);
                            clickedSquare.deSelect();
                        }
                    }
                    
                }
            }
            
            //deSelects everything selected on board before selecting new item
            //also now going to make it get rid of existing arrows
            for (int i = 0; i < gameBoardHeight; i++)
            {
                for (int j = 0; j < gameBoardWidth; j++)
                {
                    if (squaresArray[i][j].getSelectedBoolean())
                    {
                        squaresArray[i][j].deSelect();
                    }

                    if (squaresArray[i][j].getName().endsWith("_arrow"))
                    {
                        ImageIcon holeIcon = new ImageIcon("hole.png");
                        squaresArray[i][j].setIcon(holeIcon);
                        squaresArray[i][j].setName("hole");
                    }
                }
            }

            //only snowballs can prompt an action
            if (clickedSquare.getName().startsWith("snowball_") || clickedSquare.getName().startsWith("head_"))
            {
                clickedSquare.selected();
                promptAction(clickedSquare);
            }
        }
        


    }

}