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
    public static final int HEIGHT = 390;

    /** the window of the app */
    private JFrame myWindow;
    
    /** the window of the app */
    private JPanel myPanel;

    /** Names of the screens */
    private static final String TITLE = "Title";
    private static final String GAME = "Game";
    private static final String INSTRUCTION = "Instruction";
    private static final String PAUSE = "Pause";


    /** the screen of the app */
    private GameScreen myGameScreen;
    private TitleScreen myTitleScreen;
    private InstructionScreen myInstructionScreen;
    private PauseScreen myPauseScreen;

    /** Grid object */
    private Grid gameGrid;

    /** Default constructor, creates a new GameApp */
    public GameApp()
    {
        myTitleScreen = new TitleScreen(this);
        gameGrid = new Grid();
        myGameScreen = new GameScreen(gameGrid, this);
        myInstructionScreen = new InstructionScreen(this);
        myPauseScreen = new PauseScreen(this);
    }

    /** Runs the GameApp, this is the main code that runs the game */
    public void run()
    {
        myWindow = new JFrame();
        myWindow.setResizable(false);

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

    /** Changes the screen to the GameScreen */
    public void loadGameScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, GAME);
        myGameScreen.requestFocusInWindow();
    }

    /** Changes the screen to the InstructionScreen */
    public void loadInstructionScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, INSTRUCTION);
        myInstructionScreen.requestFocusInWindow();
    }

    /** Changes the screen to the TitleScreen */
    public void loadTitleScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, TITLE);
        myTitleScreen.requestFocusInWindow();
        reset();
    }

    /** Resets the game and clears the board */
    public void reset()
    {
        myGameScreen = new GameScreen(new Grid(), this);
        myWindow.dispose();
        run();
    }

    /** Loads PauseScreen */
    public void loadPauseScreen()
    {
        CardLayout layout = (CardLayout) myPanel.getLayout();
        layout.show(myPanel, PAUSE);
        myPauseScreen.requestFocusInWindow();
    }

    /** Main Code */
    public static void main(String[] args)
    {
        GameApp main = new GameApp();
        main.run();
    }
}
