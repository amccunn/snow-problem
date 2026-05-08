import java.awt.*;
import javax.swing.*;

public class GameSquare extends JButton
{
    private Boolean readyToStack;
    private Boolean selectedBoolean;

    //define the type of square with a name 
    //and define the location with respect to the grid 
    private String squareName;
    private int xCord;
    private int yCord;

    public GameSquare(String name, int x, int y)
    {
        //creates the square with the image of its name
        ImageIcon imageName = new ImageIcon(name + ".png");
        super(imageName);

        //sets attributes to base values
        this.squareName = name;
        this.xCord = x;
        this.yCord = y;
        this.selectedBoolean = false;
        this.readyToStack = false;
    }

    public Boolean isStackable()
    {
        return this.readyToStack;
    }

    public void setStackable(Boolean stack)
    {
        this.readyToStack = stack;
    }

    //checks whether the square can be selected only snowballs and arrows can be selected
    //and in some cases the heads can be selected
    public Boolean canBeSelected(GameBoard theGameBoard)
    {
        //snowball and arrows prompt action when selected but a stack doesnt can only be selected when a head was selected before it
        if (squareName.startsWith("snowball_") || squareName.endsWith("_arrow") || squareName.equals("snowman_stack"))
        {
            return true;
        }
        //heads can be selected but only if they have a snowman stack next to them
        else if (squareName.startsWith("head_"))
        {
            GameSquare[] squares = theGameBoard.checkAdjacentSquares(this);
    
            //check adjacent squares for a stack
            for (int i = 0; i < squares.length; i++)
            {
                if (squares[i] != null)
                {
                    if (squares[i].getName().equals("snowman_stack"))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        else
        {
            return false;
        }
    }

    //lets you see what square you have selected with a border
    public void selected()
    {
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        this.selectedBoolean = true;
    }

    public void deSelect()
    {
        this.setBorder(null);
        this.selectedBoolean = false;
    }

    public Boolean getSelectedBoolean()
    {
        return this.selectedBoolean;
    }
    
    public String getName()
    {
        return squareName;
    }

    public void setName(String name)
    {
        this.squareName = name;
    }

    public int[] getCords()
    {
        int[] cords = {xCord, yCord};
        return cords;
    }

    public void setCords(int x, int y)
    {
        this.xCord = x;
        this.yCord = y;
    }

    //swaps the current square and target square
    public void swap(GameSquare target, GameBoard board)
    {
        //change the icons 
        ImageIcon newIconThis = new ImageIcon(this.squareName + ".png");
        ImageIcon newIconTarget = new ImageIcon(target.getName() + ".png");

        this.setIcon(newIconTarget);
        target.setIcon(newIconThis);

        //change the names of the squares
        String tempName = this.squareName;
        this.squareName = target.getName();
        target.setName(tempName);

    }

    //this object is the bottom of the stack the topStack is the top
    public void stack(GameSquare topStack)
    {
        if (this.getName().startsWith("snowball_"))
        {
            ImageIcon stackIcon = new ImageIcon("snowman_stack.png");
            this.setIcon(stackIcon);
            this.setName("snowman_stack");
        }
        else
        {
            //in the else means its a head so it colour determines the snowman colour
            String colour = topStack.getName().split("_")[1];

            ImageIcon stackIcon = new ImageIcon("snowman_" + colour + ".png");
            this.setIcon(stackIcon);
            this.setName("snowman_" + colour);
        }

        ImageIcon holeIcon = new ImageIcon("hole.png");
        topStack.setIcon(holeIcon);
        topStack.setName("hole");
        
    }

    //moves in the given direction
    public void gameMove(String direction, GameBoard board)
    {
        int[] cords = new int[2];
        if (direction.equals("up"))
        {
            //goes through all squares up can looks for objects
            for (int i = this.yCord - 1; i >= 0; i--)
            {
                //holes and arrows cant be collided with
                if (!board.getSquaresArray()[i][this.xCord].getName().equals("hole") && !board.getSquaresArray()[i][this.xCord].getName().endsWith("_arrow"))
                {
                    cords[0] = this.xCord;
                    cords[1] =  i + 1;
                    break;
                }
                //if it doesnt collide with anything fall off and die
                else if (i == 0)
                {
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
            }
        }
        else if (direction.equals("down"))
        {
            //goes through all squares down can looks for objects
            for (int i = this.yCord + 1; i <= 3; i++)
            {
                //holes and arrows cant be collided with
                if (!board.getSquaresArray()[i][this.xCord].getName().equals("hole") && !board.getSquaresArray()[i][this.xCord].getName().endsWith("_arrow"))
                {
                    cords[0] = this.xCord;
                    cords[1] =  i - 1;
                    break;
                }
                //if it doesnt collide with anything fall off and die
                else if (i == 3)
                {
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
            }
        }
        else if (direction.equals("right"))
        {
            //goes through all squares right can looks for objects
            for (int i = this.xCord + 1; i <= 4; i++)
            {
                //holes and arrows cant be collided with
                if (!board.getSquaresArray()[this.yCord][i].getName().equals("hole") && !board.getSquaresArray()[this.yCord][i].getName().endsWith("_arrow"))
                {
                    cords[0] = i - 1;
                    cords[1] =  this.yCord;
                    break;
                }
                //if it doesnt collide with anything fall off and die
                else if (i == 4)
                {
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
                System.out.println(i);
            }
        }
        else if (direction.equals("left"))
        {
            //go through all squares left and looks for objects 
            for (int i = this.xCord - 1; i >= 0; i--)
            {
                //holes and arrows cant be collided with
                if (!board.getSquaresArray()[this.yCord][i].getName().equals("hole") && !board.getSquaresArray()[this.yCord][i].getName().endsWith("_arrow"))
                {
                    cords[0] = i + 1;
                    cords[1] =  this.yCord;
                    break;
                }
                //if it doesnt collide with anything fall off and die
                else if (i == 0)
                {
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
            }
        }
        //after locations have been got swap them
        this.swap(board.getSquaresArray()[cords[1]][cords[0]], board);
    }
}