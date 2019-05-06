import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * Title Screen class for 3D-2048 game
 * Introductory Title Screen for 2048 game
 * @author Jiahua Chen
 * @version alph-1.0 05.05.2019
 */
public class TitleScreen extends JPanel
{
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    private GameApp myApp;
    
    private Rectangle2D.Double myGameButton;

    private Rectangle2D.Double myInstructionsButton;

    public TitleScreen(GameApp app)
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
        g2.drawString("Play!", buttonX + 78, buttonY + 30);

        myInstructionsButton = new Rectangle2D.Double(buttonX, buttonY + BUTTON_HEIGHT + 20,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        g2.draw(myInstructionsButton);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Instructions", buttonX + 50, buttonY + BUTTON_HEIGHT + 50);

        g2.setFont(new Font("Helvetica", Font.BOLD, 36));
        g2.drawString("2048", buttonX + 58, buttonY - 25);

        g2.setFont(new Font("Helvetica", Font.BOLD, 22));
        g2.drawString("3", buttonX + 140, buttonY - 40);
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
                myApp.loadInstructionScreen();
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
