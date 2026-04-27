import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener
{
    private int pageNumber = 1;

    private BorderLayout menuLayout = new BorderLayout();

    private JLabel selectLabel = new JLabel("Select Level");

    private ImageIcon rightArrowIcon = new ImageIcon("right_arrow.png");
    private JButton rightArrowButton = new JButton(rightArrowIcon);
        
    private ImageIcon leftArrowIcon = new ImageIcon("left_arrow.png");
    private JButton leftArrowButton = new JButton(leftArrowIcon);

    private JFrame menuFrame = new JFrame();
    private JPanel menuPanel = new JPanel(); 

    private JButton selectButton = new JButton();

    private GameBoard previewGameBoard;

    private String[][][] gameLevels = {
        {//level 1
            {"hole", "snowball_small", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"head_blue", "hole", "hole", "hole", "snowball_large"},
        },
        {
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "head_yellow", "hole"},
            {"hole", "tree", "hole", "hole", "snowball_small"},
            {"head_blue", "hole", "hole", "hole","hole"},
        },
    };

    public Menu()
    {
        previewGameBoard = new GameBoard(true, this.gameLevels[0]);

        selectButton.setText("Play Level 1");
        menuPanel.setLayout(menuLayout);
        menuPanel.add("North", selectLabel);

        menuPanel.add("East", rightArrowButton);
        rightArrowButton.addActionListener(this);

        menuPanel.add("West", leftArrowButton);
        leftArrowButton.addActionListener(this);

        menuPanel.add("Center", previewGameBoard.getBoardPanel());

        menuPanel.add("South", selectButton);
        selectButton.addActionListener(this);

        menuFrame.setContentPane(menuPanel);
        menuFrame.setTitle("Select Menu");
        menuFrame.setSize(1000, 800);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
    }

    public void updateMenu()
    {
        selectButton.setText("Play Level " + pageNumber);
        previewGameBoard = new GameBoard(true, this.gameLevels[pageNumber - 1]);
        menuPanel.add("Center", previewGameBoard.getBoardPanel());
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == leftArrowButton)
        {
            pageNumber++;
        }
        else if (e.getSource() == rightArrowButton)
        {
            pageNumber--;
        }
        if (pageNumber == 0 || pageNumber == 61)
        {
            pageNumber = 1;
        }
        System.out.println(pageNumber);
        updateMenu();
    }

}