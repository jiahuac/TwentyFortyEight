import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

/**
 * GameScreen class for 3D-2048 game
 * Graphics and drawing for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */

public class GameScreen extends JPanel
{

    /** GameApp and Grid objects */
    private GameApp myApp;
    private Grid myGrid;

    /** Pause Button */
    private Rectangle2D.Double myPauseButton;

    /** GameScreen Constructor
     * @param grid Grid of the game
     * @param app GameApp of the Game (for changing screens)
     */
    public GameScreen(Grid grid, GameApp app)
    {
        myGrid = grid;
        myApp = app;
        this.addKeyListener(new GameKeyHandler(myGrid, this));
        this.setFocusable(true);
        this.requestFocusInWindow();
        addMouseListener(new MyButtonListener());

        FieldUpdater up = new FieldUpdater();
        up.start();
    }

    /**
     * Paints the screen
     * @param g Graphics object
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int buttonX = 600;
        int buttonY = 30;

        myPauseButton = new Rectangle2D.Double(buttonX, buttonY,
                70, 30);
        g2.draw(myPauseButton);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.drawString("Pause", buttonX + 10, buttonY + 23);

        myGrid.drawBoard(g2);
        for (Tile t : myGrid.getTiles())
        {
            t.drawMe(g2);
        }
        g2.drawString("Score: " + Integer.toString(myGrid.getScore()), 50, 50);
    }

    private class FieldUpdater extends Thread {
        public void run()
        {
            while (true)
            {
                repaint();
                try
                {
                    sleep(1);
                }
                catch (InterruptedException ie)
                {

                }
            }
        }
    }

    private class MyButtonListener implements MouseListener
    {
        public void mousePressed(MouseEvent e)
        {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (myPauseButton.contains(mouseX, mouseY))
            {
                myApp.loadPauseScreen();
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