import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;

/**
 * Paused Screen class for 3D-2048 game
 * Paused screen for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */
public class PauseScreen extends JPanel
{
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    private static BufferedImage myBackground;

    private GameApp myApp;

    private ImageButton resumeButton;
    boolean resumeHover;
    private ImageButton resetButton;
    boolean resetHover;


    public PauseScreen(GameApp app)
    {
        myApp = app;
        this.addMouseListener(new MyButtonListener());
        this.addMouseMotionListener(new MyHoverListener());

        resetButton = new ImageButton(302, 233, 166, 45, "/screens" +
                "/PauseResetButton.png", "/screens/PauseResetHover.png");

        resumeButton = new ImageButton(274, 182, 218, 24, "/screens" +
                "/PauseResumeButton.png", "/screens/PauseResumeHover.png");

        try
        {
            InputStream is = getClass().getResourceAsStream("/screens" +
                    "/PauseScreen.png");
            myBackground = ImageIO.read(is);
        }
        catch(IOException ioe)
        {
            System.out.println("InputStream ERROR");
        }
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
                null);

        resetButton.draw(g2, resetHover);
        resumeButton.draw(g2, resumeHover);
    }


    private class MyHoverListener implements MouseMotionListener
    {
        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {

            if (resetButton.doesContain(e))
            {
                resetHover = true;
                resumeHover = false;
                repaint();
            }
            else if (resumeButton.doesContain(e))
            {
                resetHover = false;
                resumeHover = true;
                repaint();
            }
            else
            {
                resetHover();
                repaint();
            }
        }
    }

    private void resetHover()
    {
        resetHover = false;
        resumeHover = false;
    }

    private class MyButtonListener implements MouseListener
    {
        public void mousePressed(MouseEvent e)
        {
            if (resumeButton.doesContain(e))
            {
                myApp.loadGameScreen();
                resetHover();
            }
            else if (resetButton.doesContain(e))
            {
                myApp.loadTitleScreen();
                resetHover();
            }
        }

        public void mouseReleased(MouseEvent e)
        {

        }

        public void mouseClicked(MouseEvent e)
        {

        }

        public void mouseEntered(MouseEvent e)
        {

        }

        public void mouseExited(MouseEvent e)
        {

        }
    }
}
