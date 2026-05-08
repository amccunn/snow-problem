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

    //frame for the whole system
    private JFrame boardFrame = new JFrame();

    //panels for the game and the top toolbar then the combination
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
     * @param level takes what level we are on so can match everything up
     * if its just a preview then I can just use the boardPanel
     */
    public GameBoard(Boolean previewBool, String[][] squareNames, int level)
    {
        this.levelNumber = level;

        //read the file for highscores need catch for file errors
        try
        {
            BufferedReader highscoreContents = new BufferedReader(new FileReader("highscores.txt"));
            int count = 0;
            String hc;

            //everytime .readLine() is called it goes to the next line so only read it once and store as hc
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

        //this adds the gameSquares to the array based of off a 2D array of strings
        for (int i = 0; i < gameBoardHeight; i++)
        {
            for (int j = 0; j < gameBoardWidth; j++)
            {
                squaresArray[i][j] = new GameSquare(squareNames[i][j], j, i);
                boardPanel.add(squaresArray[i][j]);

                //only add listeners if its not a preview
                if (!previewBool)
                {
                    squaresArray[i][j].addActionListener(this);
                }
            }
        }

        //store the original array so the game can be reset
        originalArray = squareNames;

        //display highscore
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

            //main frame for the whole game
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
    
    //this is for the previews in the menu
    public JPanel getBoardPanel()
    {
        return this.boardPanel;
    }

    //this is so i can delete it is other objects
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
                //spawn arrow in adjacent square if there is nothing there 
                //if the selected square is a head it should only prompt snowman stacks
                //if its a small snowball it should be able to go on top of a large snowball 
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

    //checks for any heads to determine a win
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

        //reset button isnt a gamesquare so needs to be check before i cast on clickedSquare
        if (e.getSource() == resetButton)
        {
            new GameBoard(false, originalArray, levelNumber);
            boardFrame.dispose();
        }
        else
        {
        
            //the only things you can press in gameboard are the gamesquares so shouldnt ever break
            GameSquare clickedSquare = (GameSquare) e.getSource();
        
            //deselect button if its selected
            if (clickedSquare.getSelectedBoolean())
            {
                clickedSquare.setSelected(false);
            }

            //some button can only be selected in certain senarios and some cant be selected at all so i dont let them do anything
            if (clickedSquare.canBeSelected(this))
            {
                Boolean arrowClicked = false;

                //need to check if arrow has been clicked to determine next action
                if (clickedSquare.getName().endsWith("_arrow"))
                {
                    arrowClicked = true;
                }
                
                //move
                if (arrowClicked)
                {
                    //i know that i moved this turn so i can stop some prompts down the line
                    movedThisTurn = true;

                    //wipe the slate clean so nothing is accidentally triggered
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

                    //add and update current score
                    currentScore++;
                    currentScoreLabel.setText("Current score is " + currentScore);
                }

                //head has special outputs when clicked
                //already verified if it can be clicked in the canBeSelected method 
                if (clickedSquare.getName().startsWith("head_"))
                {
                    GameSquare[] adjacentSquaresHead = checkAdjacentSquares(clickedSquare);
                    for (int i = 0; i < 4; i++)
                    {
                        if (adjacentSquaresHead[i] != null)
                        {
                            //can only be stacked on a snowball_stack
                            if (adjacentSquaresHead[i].getName().equals("snowball_stack"))
                            {
                                clickedSquare.setStackable(true);
                            }
                        }
                    }
                }

                //stack the snowballs if stackable
                if (clickedSquare.isStackable())
                {
                    if (lastSquare != null)
                    {
                        //different ways something can be stacked
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
                //a number that should never be reached in game because i need it to be bigger than current score
                int numHighscore = 45091232;

                //if no highscore is set anything will be the next highscore
                if (highscores[levelNumber - 1].equals("N/A"))
                {
                    highscores[levelNumber - 1] = String.valueOf(currentScore);
                } 
                else
                {
                    //turn the string into an int to compare
                    numHighscore = Integer.parseInt(highscores[levelNumber - 1]);
                }

                //lower scores are better so if current is less then replace highscore
                if (numHighscore != 45091232 && numHighscore > currentScore)
                {
                    highscores[levelNumber - 1] = String.valueOf(currentScore);
                }

                //need a catch for the file errors
                try
                {
                    FileWriter highscoreWriter = new FileWriter("highscores.txt");
                    for (String score : highscores)
                    {
                        System.out.println(score);
                        //lineSeparator adds a new line in the file
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

            //new last square
            lastSquare = clickedSquare;
        }

    }

}