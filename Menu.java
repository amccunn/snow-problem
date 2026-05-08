import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener
{
    private int pageNumber;

    private BorderLayout menuLayout = new BorderLayout();

    private JLabel selectLabel = new JLabel("Select Level");

    //arrows to navigate levels
    private ImageIcon rightArrowIcon = new ImageIcon("right_arrow.png");
    private JButton rightArrowButton = new JButton(rightArrowIcon);
        
    private ImageIcon leftArrowIcon = new ImageIcon("left_arrow.png");
    private JButton leftArrowButton = new JButton(leftArrowIcon);

    private JFrame menuFrame = new JFrame();
    private JPanel menuPanel = new JPanel(); 

    private JButton selectButton = new JButton();

    private GameBoard previewGameBoard;

    //all the game levels in order
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
            {"tree", "hole", "snowball_small", "hole", "hole"},
            {"hole", "hole", "hole", "head_red", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "snowball_large", "hole", "hole","tree"},
        },
        {//level 10
            {"tree", "hole", "tree", "hole", "hole"},
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "head_red", "hole"},
            {"hole", "snowball_small", "hole", "hole","snowball_large"},
        },
        {//level 11
            {"tree", "tree", "hole", "head_yellow", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "hole", "hole", "tree"},
            {"snowball_large", "hole", "hole", "hole", "hole"},
        },
        {//level 12
            {"tree", "snowball_large", "hole", "hole", "tree"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "head_blue", "hole", "snowball_small", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
        },
        {//level 13
            {"tree", "hole", "snowball_small", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "head_red"},
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "snowball_large"},
        },
        {//level 14
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "tree", "hole"},
            {"snowball_small", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "head_red", "hole", "snowball_large"},
        },
        {//level 15
            {"tree", "tree", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "head_blue"},
            {"snowball_large", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "tree", "hole", "hole"},
        },
        {//level 16
            {"tree", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "head_blue"},
            {"snowball_small", "hole", "hole", "hole", "hole"},
            {"snowball_large", "tree", "hole", "hole", "hole"},
        },
        {//level 17
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "tree"},
            {"tree", "hole", "hole", "snowball_small", "hole"},
            {"hole", "hole", "hole", "head_red", "snowball_large"},
        },
        {//level 18
            {"hole", "hole", "tree", "snowball_small", "snowball_large"},
            {"tree", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "tree", "hole"},
            {"hole", "head_blue", "hole", "hole", "hole"},
        },
        {//level 19
            {"hole", "hole", "tree", "hole", "snowball_small"},
            {"tree", "hole", "hole", "hole", "tree"},
            {"hole", "head_yellow", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "snowball_large", "hole"},
        },
        {//level 20
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "head_yellow", "hole"},
            {"snowball_large", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "tree", "hole", "snowball_small"},
        },
        {//level 21
            {"hole", "hole", "hole", "head_blue", "hole"},
            {"tree", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "tree"},
            {"snowball_small", "hole", "snowball_large", "hole", "hole"},
        },
        {//level 22
            {"hole", "tree", "hole", "snowball_small", "hole"},
            {"tree", "hole", "hole", "hole", "hole"},
            {"head_blue", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "tree", "hole", "snowball_large"},
        },
        {//level 23
            {"hole", "hole", "tree", "hole", "hole"},
            {"tree", "hole", "hole", "hole", "head_blue"},
            {"hole", "tree", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "hole", "snowball_small"},
        },
        {//level 24
            {"hole", "hole", "head_blue", "hole", "hole"},
            {"tree", "hole", "hole", "hole", "tree"},
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "snowball_small", "hole", "hole", "snowball_large"},
        },
        {//level 25
            {"tree", "hole", "tree", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "head_blue", "hole"},
            {"snowball_large", "tree", "hole", "hole", "hole"},
        },
        {//level 26
            {"tree", "hole", "snowball_large", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "tree"},
            {"tree", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "hole", "head_red", "hole"},
        },
        {//level 27
            {"hole", "tree", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "tree"},
            {"head_red", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "tree", "snowball_large", "hole"},
        },
        {//level 28
            {"tree", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "tree"},
            {"snowball_small", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "head_blue", "hole"},
        },
        {//level 29
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "head_yellow", "hole", "head_blue", "hole"},
            {"snowball_large", "snowball_small", "hole", "snowball_small", "snowball_large"},
            {"hole", "hole", "hole", "hole", "hole"},
        },
        {//level 30
            {"head_red", "hole", "tree", "hole", "snowball_small"},
            {"snowball_large", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "snowball_small", "hole"},
            {"head_yellow", "snowball_large", "tree", "hole", "hole"},
        },
        {//level 31
            {"snowball_large", "hole", "head_blue", "hole", "snowball_large"},
            {"tree", "hole", "head_red", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "snowball_small"},
            {"hole", "hole", "snowball_small", "hole", "hole"},
        },
        {//level 32
            {"hole", "hole", "head_red", "hole", "snowball_large"},
            {"hole", "tree", "hole", "head_yellow", "hole"},
            {"hole", "hole", "tree", "hole", "hole"},
            {"snowball_small", "hole", "snowball_large", "hole", "snowball_small"},
        },
        {//level 33
            {"snowball_large", "hole", "hole", "hole", "snowball_small"},
            {"head_red", "hole", "head_yellow", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "snowball_small", "hole", "snowball_large"},
        },
        {//level 34
            {"snowball_large", "hole", "head_blue", "snowball_small", "snowball_large"},
            {"tree", "hole", "hole", "hole", "hole"},
            {"tree", "hole", "hole", "head_yellow", "hole"},
            {"hole", "hole", "hole", "snowball_small", "hole"},
        },
        {//level 35
            {"tree", "hole", "tree", "snowball_large", "head_yellow"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "snowball_small"},
            {"snowball_large", "snowball_small", "hole", "head_red", "hole"},
        },
        {//level 36
            {"snowball_large", "hole", "head_yellow", "hole", "snowball_small"},
            {"head_red", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "tree", "hole", "hole"},
            {"snowball_large", "hole", "hole", "hole", "snowball_small"},
        },
        {//level 37
            {"head_yellow", "hole", "snowball_large", "hole", "snowball_small"},
            {"hole", "tree", "hole", "head_blue", "hole"},
            {"hole", "hole", "hole", "tree", "hole"},
            {"snowball_large", "hole", "snowball_small", "hole", "hole"},
        },
        {//level 38
            {"snowball_large", "snowball_large", "hole", "snowball_small", "hole"},
            {"tree", "head_yellow", "hole", "head_blue", "tree"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "snowball_small", "hole", "hole"},
        },
        {//level 39
            {"head_blue", "hole", "hole", "snowball_large", "hole"},
            {"snowball_small", "hole", "tree", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "snowball_small"},
            {"snowball_large", "hole", "head_red", "hole", "hole"},
        },
        {//level 40
            {"tree", "head_blue", "hole", "snowball_large", "snowball_small"},
            {"head_red", "hole", "snowball_small", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "tree", "hole"},
        },
        {//level 41
            {"snowball_large", "hole", "hole", "hole", "snowball_small"},
            {"hole", "hole", "head_blue", "hole", "hole"},
            {"snowball_small", "hole", "head_yellow", "hole", "hole"},
            {"snowball_large", "hole", "hole", "hole", "hole"},
        },
        {//level 42
            {"snowball_large", "hole", "hole", "hole", "snowball_small"},
            {"hole", "head_red", "hole", "hole", "hole"},
            {"snowball_large", "hole", "head_yellow", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
        },
        {//level 43
            {"snowball_small", "head_blue", "tree", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "head_red", "hole", "snowball_large"},
        },
        {//level 44
            {"hole", "hole", "hole", "hole", "snowball_large"},
            {"hole", "tree", "head_red", "tree", "hole"},
            {"hole", "hole", "head_yellow", "hole", "hole"},
            {"snowball_small", "hole", "hole", "snowball_large", "snowball_small"},
        },
        {//level 45
            {"hole", "tree", "hole", "hole", "hole"},
            {"snowball_small", "hole", "snowball_small", "hole", "head_yellow"},
            {"hole", "head_blue", "hole", "tree", "hole"},
            {"snowball_large", "hole", "hole", "hole", "snowball_large"},
        },
        {//level 46
            {"snowball_large", "snowball_small", "tree", "snowball_large", "head_blue"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"tree", "head_yellow", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
        },
        {//level 47
            {"snowball_large", "hole", "head_red", "hole", "snowball_small"},
            {"hole", "tree", "hole", "tree", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"head_blue", "hole", "hole", "hole", "snowball_large"},
        },
        {//level 48
            {"hole", "tree", "hole", "hole", "snowball_large"},
            {"hole", "snowball_small", "hole", "hole", "tree"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "head_yellow", "snowball_small", "head_red", "snowball_large"},
        },
        {//level 49
            {"snowball_small", "snowball_small", "snowball_small", "snowball_large", "hole"},
            {"tree", "hole", "head_yellow", "tree", "hole"},
            {"hole", "snowball_large", "head_red", "hole", "hole"},
            {"hole", "hole", "head_blue", "hole", "snowball_large"},
        },
        {//level 50
            {"head_yellow", "snowball_large", "snowball_small", "hole", "snowball_small"},
            {"hole", "hole", "tree", "hole", "head_blue"},
            {"snowball_large", "hole", "tree", "hole", "hole"},
            {"hole", "head_red", "snowball_small", "hole", "snowball_large"},
        },
        {//level 51
            {"snowball_large", "snowball_large", "hole", "head_red", "hole"},
            {"tree", "hole", "hole", "hole", "head_blue"},
            {"snowball_large", "hole", "tree", "hole", "hole"},
            {"snowball_small", "snowball_small", "snowball_small", "head_yellow", "hole"},
        },
        {//level 52
            {"tree", "snowball_large", "hole", "hole", "snowball_large"},
            {"hole", "hole", "hole", "snowball_small", "head_blue"},
            {"snowball_small", "hole", "hole", "head_red", "snowball_large"},
            {"hole", "snowball_small", "head_yellow", "hole", "hole"},
        },
        {//level 53
            {"snowball_large", "hole", "tree", "hole", "hole"},
            {"hole", "head_yellow", "hole", "head_blue", "hole"},
            {"tree", "hole", "hole", "hole", "hole"},
            {"snowball_small", "snowball_small", "hole", "hole", "snowball_large"},
        },
        {//level 54
            {"hole", "hole", "snowball_large", "hole", "hole"},
            {"hole", "tree", "hole", "hole", "head_yellow"},
            {"hole", "tree", "hole", "hole", "head_blue"},
            {"snowball_small", "snowball_small", "hole", "hole", "snowball_large"},
        },
        {//level 55
            {"snowball_large", "tree", "head_red", "head_blue", "snowball_large"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "hole", "tree", "snowball_small"},
        },
        {//level 56
            {"hole", "tree", "hole", "tree", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "head_yellow", "hole", "snowball_large"},
            {"hole", "head_red", "hole", "snowball_large", "hole"},
        },
        {//level 57
            {"hole", "hole", "tree", "hole", "hole"},
            {"tree", "hole", "snowball_small", "head_red", "hole"},
            {"hole", "snowball_small", "hole", "hole", "head_yellow"},
            {"snowball_large", "hole", "hole", "hole", "snowball_large"},
        },
        {//level 58
            {"tree", "snowball_small", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "head_yellow"},
            {"tree", "hole", "snowball_large", "hole", "hole"},
            {"snowball_large", "hole", "hole", "head_red", "snowball_small"},
        },
        {//level 59
            {"snowball_large", "hole", "hole", "snowball_large", "hole"},
            {"hole", "tree", "hole", "hole", "head_blue"},
            {"head_red", "hole", "hole", "tree", "hole"},
            {"hole", "snowball_small", "hole", "hole", "snowball_small"},
        },
        {//level 60
            {"snowball_large", "snowball_small", "hole", "hole", "head_blue"},
            {"hole", "tree", "tree", "hole", "hole"},
            {"head_red", "hole", "hole", "hole", "hole"},
            {"hole", "snowball_small", "hole", "hole", "snowball_large"},
        },
        {//level 61
            {"head_yellow", "hole", "head_red", "hole", "snowball_small"},
            {"hole", "head_blue", "hole", "snowball_large", "tree"},
            {"snowball_large", "snowball_small", "snowball_small", "hole", "hole"},
            {"hole", "hole", "hole", "snowball_large", "hole"},
        },
        {//level 62
            {"snowball_small", "snowball_small", "snowball_small", "head_yellow", "hole"},
            {"hole", "tree", "snowball_large", "snowball_large", "head_blue"},
            {"hole", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "head_red", "snowball_large"},
        },
        {//level 63
            {"hole", "hole", "snowball_large", "hole", "snowball_small"},
            {"hole", "hole", "tree", "hole", "snowball_small"},
            {"head_red", "hole", "head_yellow", "hole", "head_blue"},
            {"snowball_small", "hole", "snowball_large", "hole", "snowball_large"},
        },
        {//level 64
            {"snowball_small", "hole", "head_blue", "hole", "hole"},
            {"tree", "head_red", "hole", "head_yellow", "snowball_large"},
            {"hole", "hole", "hole", "snowball_small", "tree"},
            {"snowball_large", "snowball_large", "hole", "hole", "snowball_small"},
        },
        {//level 65
            {"hole", "head_yellow", "hole", "head_blue", "hole"},
            {"tree", "snowball_small", "snowball_large", "hole", "hole"},
            {"hole", "hole", "snowball_small", "hole", "tree"},
            {"hole", "hole", "snowball_large", "hole", "hole"},
        },
        {//level 66
            {"hole", "head_yellow", "hole", "hole", "snowball_large"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "head_red", "snowball_small", "snowball_large"},
        },
        {//level 67
            {"hole", "snowball_large", "hole", "hole", "snowball_large"},
            {"tree", "hole", "hole", "hole", "snowball_small"},
            {"head_red", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "head_blue", "snowball_small", "hole"},
        },
        {//level 68
            {"tree", "hole", "head_yellow", "snowball_small", "hole"},
            {"snowball_small", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "snowball_large"},
            {"snowball_large", "hole", "head_red", "hole", "hole"},
        },
        {//level 69
            {"snowball_large", "hole", "head_red", "head_blue", "snowball_large"},
            {"snowball_small", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "tree", "head_yellow"},
            {"snowball_large", "hole", "hole", "snowball_small", "snowball_small"},
        },
        {//level 70
            {"hole", "tree", "snowball_small", "snowball_small", "hole"},
            {"hole", "tree", "hole", "snowball_small", "snowball_large"},
            {"head_red", "snowball_large", "hole", "hole", "hole"},
            {"head_yellow", "hole", "snowball_large", "head_blue", "hole"},
        },
        {//level 71
            {"snowball_large", "hole", "tree", "hole", "snowball_small"},
            {"head_yellow", "hole", "tree", "hole", "snowball_small"},
            {"head_blue", "snowball_large", "snowball_small", "hole", "hole"},
            {"snowball_large", "hole", "hole", "hole", "head_red"},
        },
        {//level 72
            {"snowball_large", "snowball_large", "hole", "snowball_large", "snowball_small"},
            {"tree", "head_red", "hole", "hole", "hole"},
            {"head_blue", "head_yellow", "hole", "hole", "hole"},
            {"hole", "hole", "snowball_small", "hole", "snowball_small"},
        },
        {//level 73
            {"hole", "head_yellow", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_large", "hole", "hole", "hole", "snowball_small"},
            {"hole", "hole", "hole", "head_blue", "snowball_large"},
        },
        {//level 74
            {"tree", "snowball_large", "snowball_large", "hole", "hole"},
            {"hole", "snowball_small", "snowball_small", "hole", "head_red"},
            {"head_blue", "hole", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "tree", "hole"},
        },
        {//level 75
            {"snowball_small", "tree", "hole", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "head_red"},
            {"snowball_small", "hole", "hole", "hole", "head_blue"},
            {"snowball_large", "tree", "hole", "hole", "snowball_large"},
        },
        {//level 76
            {"snowball_large", "hole", "hole", "head_blue", "snowball_small"},
            {"tree", "hole", "snowball_large", "hole", "hole"},
            {"hole", "hole", "hole", "hole", "head_red"},
            {"hole", "snowball_small", "hole", "hole", "hole"},
        },
        {//level 77
            {"tree", "snowball_small", "hole", "snowball_large", "hole"},
            {"head_red", "hole", "hole", "hole", "snowball_small"},
            {"hole", "hole", "tree", "snowball_small", "snowball_large"},
            {"hole", "head_blue", "hole", "head_yellow", "snowball_large"},
        },
        {//level 78
            {"hole", "head_blue", "head_yellow", "head_red", "snowball_large"},
            {"hole", "hole", "hole", "hole", "hole"},
            {"snowball_small", "hole", "hole", "hole", "snowball_large"},
            {"snowball_small", "snowball_small", "snowball_large", "hole", "hole"},
        },
        {//level 79
            {"tree", "hole", "snowball_large", "hole", "tree"},
            {"head_red", "head_blue", "snowball_small", "hole", "hole"},
            {"head_yellow", "snowball_large", "snowball_small", "hole", "hole"},
            {"hole", "hole", "snowball_small", "hole", "snowball_large"},
        },
        {//level 80
            {"snowball_small", "tree", "hole", "head_blue", "hole"},
            {"hole", "snowball_large", "snowball_large", "snowball_small", "snowball_large"},
            {"tree", "hole", "hole", "snowball_small", "hole"},
            {"hole", "hole", "hole", "head_yellow", "head_red"},
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
        //easy way to navigate menu with arrows
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

        //if it goes past all the levels go back to the start
        if (pageNumber > gameLevels.length)
        {
            pageNumber = 1;
        }
        else if (pageNumber < 1)
        {
            pageNumber = gameLevels.length;
        }
        //refreshes the menu after each button click
        updateMenu();
    }
}