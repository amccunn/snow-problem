import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class GameBoard implements ActionListener
{
    private int levelNumber;
    private String[] highscores = new String[80];
    private JFrame boardFrame = new JFrame();
    private JPanel boardPanel = new JPanel();
    private JPanel fullPanel = new JPanel();
    private JPanel toolBarPanel = new JPanel();
    private int gameBoardWidth = 5;
    private int gameBoardHeight = 4;
    private GridLayout boardLayout = new GridLayout(gameBoardHeight, gameBoardWidth);

    //this allows me to put a toolbar at the top and the game below it
    private BoxLayout fullLayout = new BoxLayout(fullPanel, BoxLayout.Y_AXIS);
    private BoxLayout toolBarLayout = new BoxLayout(toolBarPanel, BoxLayout.X_AXIS);

    private JButton resetButton = new JButton("Reset level");
    private JLabel highscoreLabel = new JLabel("Highscore is none");
    private JLabel currentScoreLabel = new JLabel("Current score is 0");
    private int currentScore = 0;
    private Boolean movedThisTurn = false;
    private GameSquare lastSquare = null;

    private GameSquare[][] squaresArray = new GameSquare[gameBoardHeight][gameBoardWidth];
    private String[][] originalArray;

    /**
     * @param previewBool if true then create only a preview and dont add listeners
     * @param squareNames a 2d array which represents what should be in each square
     * if its just a preview then I can just use the boardPanel
     */
    public GameBoard(Boolean previewBool, String[][] squareNames, int level)
    {
        this.levelNumber = level;

        try
        {
            BufferedReader highscoreContents = new BufferedReader(new FileReader("highscores.txt"));
            int count = 0;
            String hc;

            while ((hc = highscoreContents.readLine()) != null)
            {
                highscores[count] = hc;
                count++;
            }

            highscoreContents.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Cannot find file");
        }
        catch (IOException e)
        {
            System.out.println("Error reading file");
        }
        
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
        originalArray = squareNames;
        highscoreLabel.setText("Highscore is " + highscores[levelNumber - 1]);

        //creates the all details regarding the frame only if its not a preview
        if (!previewBool)
        {
            toolBarPanel.setLayout(toolBarLayout);
            toolBarPanel.add(resetButton);
            resetButton.addActionListener(this);
            toolBarPanel.add(highscoreLabel);
            toolBarPanel.add(currentScoreLabel);

            fullPanel.setLayout(fullLayout);
            fullPanel.add(toolBarPanel);
            fullPanel.add(boardPanel);

            boardFrame.setContentPane(fullPanel);
            boardFrame.setTitle("Snow Game");
            boardFrame.setSize(800, 800);
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

    public Boolean checkWin()
    {
        for (int i = 0; i < gameBoardHeight; i++)
        {
            for (int j = 0; j < gameBoardWidth; j++)
            {
                if (squaresArray[i][j].getName().startsWith("head_"))
                {
                    return false;
                }
            } 
        }
        return true;
    }

    //listeners for the squares on the screen
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == resetButton)
        {
            new GameBoard(false, originalArray, levelNumber);
            boardFrame.dispose();
        }
        else
        {
        
            //the only things you can press in gameboard are the gamesquares so shouldnt ever break
            GameSquare clickedSquare = (GameSquare) e.getSource();
        
            if (clickedSquare.getSelectedBoolean())
            {
                clickedSquare.setSelected(false);
            }

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
                    movedThisTurn = true;
                    System.out.println("square moving " + clickedSquare.getName());
                    for (int i = 0; i < gameBoardHeight; i++)
                    {
                        for (int j = 0; j < gameBoardWidth; j++)
                        {
                            squaresArray[i][j].setStackable(false);
                            squaresArray[i][j].deSelect();
                        }
                    }
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
                    currentScore++;
                    currentScoreLabel.setText("Current score is " + currentScore);
                }

                if (clickedSquare.getName().startsWith("head_"))
                {
                    GameSquare[] adjacentSquaresHead = checkAdjacentSquares(clickedSquare);
                    for (int i = 0; i < 4; i++)
                    {
                        if (adjacentSquaresHead[i] != null)
                        {
                            if (adjacentSquaresHead[i].getName().equals("snowball_stack"))
                            {
                                clickedSquare.setStackable(true);
                            }
                        }
                    }
                }

                System.out.println(clickedSquare.isStackable());
                //stack the snowballs if stackable
                if (clickedSquare.isStackable())
                {
                    if (lastSquare != null)
                    {
                        if (lastSquare.isStackable() && (!lastSquare.getName().startsWith("head_") || clickedSquare.getName().equals("snowman_stack")) && lastSquare != clickedSquare)
                        {
                            clickedSquare.stack(lastSquare);
                            currentScore++;
                            currentScoreLabel.setText("Current score is " + currentScore);
                            clickedSquare.setStackable(false);
                            lastSquare.setStackable(false);
                            clickedSquare.deSelect();
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

                //only snowballs can prompt an action and heads in some cases
                //dont prompt action is youve moved this turn as this part is reserved for moving
                if ((clickedSquare.getName().startsWith("snowball_") || clickedSquare.getName().startsWith("head_")) && !movedThisTurn)
                {
                    clickedSquare.selected();
                    promptAction(clickedSquare);
                }
                else if (movedThisTurn)
                {
                    //set movedthisturn back to false
                    movedThisTurn = false;
                }
            }
            
            if (checkWin())
            {
                int numHighscore = 45091232;
                if (highscores[levelNumber - 1].equals("N/A") || numHighscore > currentScore)
                {
                    highscores[levelNumber - 1] = String.valueOf(currentScore);
                } 
                else
                {
                    numHighscore = Integer.parseInt(highscores[levelNumber - 1]);
                }

                if (numHighscore != 45091232 && numHighscore > currentScore)
                {
                    highscores[levelNumber - 1] = String.valueOf(currentScore);
                }

                try
                {
                    FileWriter highscoreWriter = new FileWriter("highscores.txt");
                    for (String score : highscores)
                    {
                        System.out.println(score);
                        highscoreWriter.write(score + System.lineSeparator());
                    }
                    highscoreWriter.close();
                }
                catch (IOException exception)
                {
                    System.out.println("IOException");
                }
                new GameWinScreen(levelNumber);
                boardFrame.dispose();
            }
            lastSquare = clickedSquare;
        }

    }

}