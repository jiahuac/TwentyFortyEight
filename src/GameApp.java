import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * GameApp class for 3D-2048 game
 * Runs the app for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */
public class GameApp {

    /** width of the screen */
    public static final int WIDTH = 770;

    /** height of the screen */
    public static final int HEIGHT = 370;

    /** the window of the app */
    private JFrame myWindow;

    private JPanel myPanel;

    private static final String TITLE = "Title";
    private static final String GAME = "Game";
    private static final String INSTRUCTION = "Instruction";


    /** the screen of the app */
    private GameScreen myGameScreen;
    private TitleScreen myTitleScreen;
    private InstructionScreen myInstructionScreen;

    private Grid gameGrid;

    public GameApp()
    {
        myTitleScreen = new TitleScreen(this);
        gameGrid = new Grid();
        myGameScreen = new GameScreen(gameGrid);
        myInstructionScreen = new InstructionScreen(this);
    }

    public void run()
    {
        myWindow = new JFrame();

        myWindow.setSize(WIDTH, HEIGHT);
        myWindow.setTitle("2048^3");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myPanel = new JPanel(new CardLayout());

        myPanel.add(myGameScreen, GAME);
        myPanel.add(myTitleScreen, TITLE);
        myPanel.add(myInstructionScreen, INSTRUCTION);
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, TITLE);

        myWindow.add(myPanel);
        myWindow.setVisible(true);
    }

    public void loadGameScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, GAME);
        myGameScreen.requestFocusInWindow();
    }

    public void loadInstructionScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, INSTRUCTION);
        myInstructionScreen.requestFocusInWindow();
    }

    public static void main(String[] args)
    {
        GameApp main = new GameApp();
        main.run();
    }
}
