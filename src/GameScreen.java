import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * GameScreen class for 3D-2048 game
 * Graphics and drawing for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */

public class GameScreen extends JPanel
{
    private Grid myGrid;

    public GameScreen(Grid grid)
    {
        myGrid = grid;
        this.addKeyListener(new GameKeyHandler(myGrid, this));
        this.setFocusable(true);
        this.requestFocusInWindow();

        FieldUpdater up = new FieldUpdater();
        up.start();
    }

    public void paintComponent(Graphics g)
    {
//        System.out.println("GameScreen paintComponent called");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        myGrid.drawEmpty(g2);
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
}