import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * GameApp class for 3D-2048 game
 * Runs the app for 2048 game
 * @author Jiahua Chen
 * @version 0.02 04.18.2019 Milestone 2
 */
public class GameApp {

    /** width of the screen */
    public static final int WIDTH = 770;

    /** height of the screen */
    public static final int HEIGHT = 370;

    /** the window of the app */
    private JFrame myWindow;

    /** the screen of the app */
    private GameScreen myScreen;

    public GameApp()
    {
        Grid gameGrid = new Grid();
        myScreen = new GameScreen(gameGrid);

        myWindow = new JFrame();
        myWindow.setSize(WIDTH, HEIGHT);
        myWindow.setTitle("2048^3");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myWindow.add(myScreen);

        myWindow.setVisible(true);
    }

    public static void main(String[] args)
    {
        GameApp main = new GameApp();
    }
}
