import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameOverScreen implements ActionListener
{
    private int lastLevelNumber;

    private JFrame gameOverFrame = new JFrame();
    private JPanel gameOverPanel = new JPanel();
    private BorderLayout gameOverLayout = new BorderLayout();

    private JButton mainMenuButton = new JButton("Return to menu");

    private JLabel gameOverLabel = new JLabel("Game Over!");

    public GameOverScreen(int level)
    {
        this.lastLevelNumber = level;

        gameOverPanel.setLayout(gameOverLayout);
        gameOverPanel.add("North", gameOverLabel);
        gameOverPanel.add("North", mainMenuButton);
       

        gameOverFrame.setContentPane(gameOverPanel);
        gameOverFrame.setTitle("Game Over!");
        gameOverFrame.setSize(300, 300);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == mainMenuButton)
        {
            new Menu(this.lastLevelNumber);
        }

            
    }

}