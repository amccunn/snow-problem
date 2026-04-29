import java.awt.*;
import javax.swing.*;

public class GameSquare extends JButton
{
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
    }

    public Boolean canBeSelected()
    {
        if (squareName.startsWith("snowball_"))
        {
            return true;
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

    public void gameMove(String direction, GameBoard board)
    {
        int[] cords = new int[2];
        if (direction == "up")
        {
            for (int i = this.yCord - 1; i > -1; i--)
            {
                if (board.getSquaresArray()[i][this.xCord].getName() != "hole")
                {
                    cords[0] = this.xCord;
                    cords[1] =  i - 1;
                    break;
                }
                else if (i == 0)
                {
                    new GameOverScreen(board.getLevelNumber());
                }
            }
        }
    }
}