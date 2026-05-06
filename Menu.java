import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener
{
    private int pageNumber;

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
        {//level 2
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "head_yellow", "hole"},
            {"hole", "tree", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole","hole"},
        },
        {//level 3
            {"tree", "hole", "hole", "snowball_large", "hole"},
            {"hole", "hole", "hole", "snowball_small", "hole"},
            {"hole", "hole", "hole", "hole","head_red"},
            {"hole", "tree", "hole", "hole","hole"},
        },
        {//level 4
            {"tree", "hole", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"head_yellow", "hole", "hole", "hole","snowball_large"},
        },
        {//level 5
            {"tree", "hole", "tree", "hole", "tree"},
            {"hole", "hole", "head_blue", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "hole", "hole","snowball_large"},
        },
        {//level 6
            {"hole", "hole", "tree", "hole", "hole"},
            {"head_blue", "snowball_small", "hole", "snowball_large", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "tree", "hole","hole"},
        },
        {//level 7
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "tree", "tree", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "head_red"},
            {"snowball_small", "hole", "snowball_large", "hole","hole"},
        },
        {//level 8
            {"hole", "tree", "hole", "snowball_small", "snowball_large"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "tree"},
            {"hole", "hole", "head_blue", "hole","hole"},
        },
        {//level 9
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "head_yellow", "hole"},
            {"hole", "tree", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole","hole"},
        },
        {//level 10
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "head_yellow", "hole"},
            {"hole", "tree", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole","hole"},
        },
    };

    public Menu(int pageNum)
    {
        this.pageNumber = pageNum;

        previewGameBoard = new GameBoard(true, this.gameLevels[pageNum - 1], pageNum);

        selectButton.setText("Play Level " + pageNum);
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
        menuPanel.remove(previewGameBoard.getBoardPanel());
        previewGameBoard = new GameBoard(true, this.gameLevels[pageNumber - 1], pageNumber);
        menuPanel.add("Center", previewGameBoard.getBoardPanel());
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == rightArrowButton)
        {
            pageNumber++;
        }
        else if (e.getSource() == leftArrowButton)
        {
            pageNumber--;
        }
        else if (e.getSource() == selectButton)
        {
            menuFrame.dispose();
            new GameBoard(false, gameLevels[pageNumber - 1], pageNumber);
        }

        if (pageNumber > gameLevels.length)
        {
            pageNumber = 1;
        }
        else if (pageNumber < 1)
        {
            pageNumber = gameLevels.length;
        }
        updateMenu();
    }
}