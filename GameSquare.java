import java.awt.*;
import javax.swing.*;

public class GameSquare extends JButton
{
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
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
    }
    
    public String getName()
    {
        return squareName;
    }

    public int[] getCords()
    {
        int[] cords = {xCord, yCord};
        return cords;
    }
}