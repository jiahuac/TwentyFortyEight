import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * Instructions Screen class for 3D-2048 game
 * Instructions Screen for 2048 game
 * @author Jiahua Chen
 * @version alph-0.5 04.18.2019 Milestone 2
 */
public class InstructionScreen extends JPanel
{
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    private GameApp myApp;

    private Rectangle2D.Double myGameButton;

    public InstructionScreen(GameApp app)
    {
        myApp = app;
        addMouseListener(new MyButtonListener());
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawString("Instructions will be implemented soon! Basically use W, A, S, D, Q, and E to shift. ", 50, 50);

        setBackground(Color.WHITE);

        int buttonX = (GameApp.WIDTH / 2) - (BUTTON_WIDTH / 2);
        int buttonY = (GameApp.HEIGHT / 2) - (BUTTON_HEIGHT / 2) + 100;

        myGameButton = new Rectangle2D.Double(buttonX, buttonY,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        g2.draw(myGameButton);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Play!", buttonX + 78, buttonY + 30);
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
