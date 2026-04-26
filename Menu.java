import java.awt.*;
import javax.swing.*;

public class Menu
{
    private int pageNumber;

    private BorderLayout menuPanel = new BorderLayout();

    private JLabel selectLabel = new JLabel("Select Level");

    private ImageIcon rightArrowIcon = new ImageIcon("right_arrow.png");
    private JButton rightArrowButton = new JButton(rightArrowIcon);
        
    private ImageIcon leftArrowIcon = new ImageIcon("left_arrow.png");
    private JButton leftArrowButton = new JButton(leftArrowIcon); 

    

    public Menu(int lastPage)
    {
        menuPanel.add("North", selectLabel);
        menuPanel.add("East", rightArrowButton);
        menuPanel.add("West", leftArrowButton);
        menuPanel.add("Centre", previewGameBoard);

        menuFrame.setContentPane(menuPanel);
        menuFrame.setTitle("Select Menu");
        menuFrame.setSize(800, 640);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
    }
}