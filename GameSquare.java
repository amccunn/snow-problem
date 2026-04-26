import java.awt.*;
import javax.swing.*;

public class GameSquare extends JButton
{
    private String squareType;

    public GameSquare(String type)
    {
        super(type + ".png")
        squareType = type;
    }

    public void setSquareType(String newType)
    {
        squareType = newType;
    }

    public String getSquareType()
    {
        return squareType;
    }
    
}