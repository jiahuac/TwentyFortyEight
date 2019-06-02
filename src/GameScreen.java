import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;

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

    private static BufferedImage myBackground;
    private static BufferedImage myTooltip;


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

        try
        {
            InputStream is = getClass().getResourceAsStream("/screens" +
                    "/GameScreen.png");
            myBackground = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/ControlTooltip.png");
            myTooltip = ImageIO.read(is);
        }
        catch(IOException ioe)
        {
            System.out.println("InputStream ERROR");
        }

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

        g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
                null);

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

        if (true)
        {
            g2.drawImage(myTooltip, 202, 69, 364,
                    225,
                    null);
        }
        g2.drawString("Score: " + Integer.toString(myGrid.getScore()), 150, 50);
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