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
    private static final String PAUSE = "Pause";


    /** the screen of the app */
    private GameScreen myGameScreen;
    private TitleScreen myTitleScreen;
    private InstructionScreen myInstructionScreen;
    private PauseScreen myPauseScreen;

    private Grid gameGrid;

    public GameApp()
    {
        myTitleScreen = new TitleScreen(this);
        gameGrid = new Grid();
        myGameScreen = new GameScreen(gameGrid, this);
        myInstructionScreen = new InstructionScreen(this);
        myPauseScreen = new PauseScreen(this);
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
        myPanel.add(myPauseScreen, PAUSE);
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

    public void loadTitleScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, TITLE);
        myTitleScreen.requestFocusInWindow();
        reset();
    }

    public void reset()
    {
        myGameScreen = new GameScreen(new Grid(), this);
        myWindow.dispose();
        run();
    }

    public void loadPauseScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, PAUSE);
        myPauseScreen.requestFocusInWindow();
    }

    public static void main(String[] args)
    {
        GameApp main = new GameApp();
        main.run();
    }
}
