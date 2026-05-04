import java.awt.*;
import javax.swing.*;

public class GameSquare extends JButton
{
    private Boolean readyToStack;
    private Boolean selectedBoolean;
    private String squareName;
    private int xCord;
    private int yCord;

    public GameSquare(String name, int x, int y)
    {
        ImageIcon imageName = new ImageIcon(name + ".png");
        super(imageName);
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

    public Boolean canBeSelected(GameBoard theGameBoard)
    {
        if (squareName.startsWith("snowball_") || squareName.endsWith("_arrow") || squareName.equals("snowman_stack"))
        {
            return true;
        }
        else if (squareName.startsWith("head_"))
        {
            GameSquare[] squares = theGameBoard.checkAdjacentSquares(this);
    
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

    public void stack(GameSquare topStack)
    {
        System.out.println("Stacking...");
        if (this.getName().startsWith("snowball_"))
        {
            ImageIcon stackIcon = new ImageIcon("snowman_stack.png");
            this.setIcon(stackIcon);
            this.setName("snowman_stack");
        }
        else
        {
            System.out.println(topStack.getName());            
            String colour = topStack.getName().split("_")[1];
            System.out.println(colour);


            ImageIcon stackIcon = new ImageIcon("snowman_" + colour + ".png");
            this.setIcon(stackIcon);
            this.setName("snowman_" + colour);
        }

        ImageIcon holeIcon = new ImageIcon("hole.png");
        topStack.setIcon(holeIcon);
        topStack.setName("hole");
        
    }

    public void gameMove(String direction, GameBoard board)
    {
        System.out.println("Moving...");
        int[] cords = new int[2];
        if (direction.equals("up"))
        {
            System.out.println("Going up");

            for (int i = this.yCord - 1; i >= 0; i--)
            {
                if (!board.getSquaresArray()[i][this.xCord].getName().equals("hole") && !board.getSquaresArray()[i][this.xCord].getName().endsWith("_arrow"))
                {
                    System.out.println("No hole " + i);
                    cords[0] = this.xCord;
                    cords[1] =  i - 1;
                    break;
                }
                else if (i == 0)
                {
                    System.out.println("Fall off map");
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
                System.out.println(i);
            }
        }
        else if (direction.equals("down"))
        {
            System.out.println("Going down");

            for (int i = this.yCord + 1; i <= 3; i++)
            {
                if (!board.getSquaresArray()[i][this.xCord].getName().equals("hole") && !board.getSquaresArray()[i][this.xCord].getName().endsWith("_arrow"))
                {
                    System.out.println("No hole " + i);
                    cords[0] = this.xCord;
                    cords[1] =  i - 1;
                    break;
                }
                else if (i == 3)
                {
                    System.out.println("Fall off map");
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
                System.out.println(i + " " + board.getSquaresArray()[i][this.xCord].getName());
            }
        }
        else if (direction.equals("right"))
        {
            System.out.println("Going right");

            for (int i = this.xCord + 1; i <= 4; i++)
            {
                if (!board.getSquaresArray()[this.yCord][i].getName().equals("hole") && !board.getSquaresArray()[this.yCord][i].getName().endsWith("_arrow"))
                {
                    System.out.println("No hole " + i);
                    cords[0] = i - 1;
                    cords[1] =  this.yCord;
                    break;
                }
                else if (i == 4)
                {
                    System.out.println("Fall off map");
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
                System.out.println(i);
            }
        }
        else if (direction.equals("left"))
        {
            System.out.println("Going left");

            for (int i = this.xCord - 1; i >= 0; i--)
            {
                if (!board.getSquaresArray()[this.yCord][i].getName().equals("hole") && !board.getSquaresArray()[this.yCord][i].getName().endsWith("_arrow"))
                {
                    System.out.println("No hole " + i);
                    cords[0] = i + 1;
                    cords[1] =  this.yCord;
                    break;
                }
                else if (i == 0)
                {
                    System.out.println("Fall off map");
                    new GameOverScreen(board.getLevelNumber());
                    board.getFrame().dispose();
                }
                System.out.println(i);
            }
        }
        System.out.println(cords[0] + " " + cords[1]);
        this.swap(board.getSquaresArray()[cords[1]][cords[0]], board);
    }
}