import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameWinScreen implements ActionListener
{
    //need the levelnumber for going back to the menu
    private int lastLevelNumber;

    private JFrame gameWinFrame = new JFrame();
    private JPanel gameWinPanel = new JPanel();
    private BorderLayout gameWinLayout = new BorderLayout();

    private JButton mainMenuButton = new JButton("Return to menu");

    private JLabel gameWinLabel = new JLabel("You win!");

    public GameWinScreen(int level)
    {
        this.lastLevelNumber = level;

        gameWinPanel.setLayout(gameWinLayout);
        gameWinPanel.add("North", gameWinLabel);
        gameWinPanel.add("Center", mainMenuButton);
        mainMenuButton.addActionListener(this);
       
        //smaller frame for win screen
        gameWinFrame.setContentPane(gameWinPanel);
        gameWinFrame.setTitle("Game Over!");
        gameWinFrame.setSize(300, 300);
        gameWinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWinFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        //go back to the main menu on the last level that was played
        if (e.getSource() == mainMenuButton)
        {
            new Menu(this.lastLevelNumber);
            gameWinFrame.dispose();
        }

            
    }

}