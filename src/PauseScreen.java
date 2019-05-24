import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

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

    private GameApp myApp;

    private Rectangle2D.Double myGameButton;

    private Rectangle2D.Double myInstructionsButton;

    public PauseScreen(GameApp app)
    {
        myApp = app;
        addMouseListener(new MyButtonListener());
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        setBackground(Color.WHITE);

        int buttonX = (GameApp.WIDTH / 2) - (BUTTON_WIDTH / 2);
        int buttonY = (GameApp.HEIGHT / 2) - (BUTTON_HEIGHT / 2);

        myGameButton = new Rectangle2D.Double(buttonX, buttonY,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        g2.draw(myGameButton);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Resume", buttonX + 75, buttonY + 30);

        myInstructionsButton = new Rectangle2D.Double(buttonX, buttonY + BUTTON_HEIGHT + 20,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        g2.draw(myInstructionsButton);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Title Screen & Restart", buttonX + 10, buttonY + BUTTON_HEIGHT + 50);

        g2.setFont(new Font("Helvetica", Font.BOLD, 36));
        g2.drawString("Paused! ", buttonX + 38, buttonY - 25);

        g2.setFont(new Font("Helvetica", Font.BOLD, 36));
        g2.drawString("2048", buttonX + 58, buttonY - 65);

        g2.setFont(new Font("Helvetica", Font.BOLD, 22));
        g2.drawString("3", buttonX + 140, buttonY - 80);
    }

    private class MyButtonListener implements MouseListener
    {
        public void mousePressed(MouseEvent e)
        {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (myGameButton.contains(mouseX, mouseY))
            {
                myApp.loadGameScreen();
            }
            else if (myInstructionsButton.contains(mouseX, mouseY))
            {
                myApp.loadTitleScreen();
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
