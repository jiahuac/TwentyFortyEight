import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * GameScreen class for 3D-2048 game
 * Graphics and drawing for 2048 game
 * @author Jiahua Chen
 * @version 0.02 04.18.2019 Milestone 2
 */

public class GameScreen extends JPanel
{
    private Grid myGrid;
    private JLabel text;

    public GameScreen(Grid grid)
    {
        myGrid = grid;
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Tile t : myGrid.getTiles())
        {
            t.drawMe(g2);
        }
        g2.drawString("Score: " + Integer.toString(myGrid.getScore()), 50, 50);
    }

    private class KeyHandler implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_W)
            {
                myGrid.doMove(Move.UP);
            }
            else if (code == KeyEvent.VK_A)
            {
                myGrid.doMove(Move.LEFT);
            }
            else if (code == KeyEvent.VK_S)
            {
                myGrid.doMove(Move.DOWN);
            }
            else if (code == KeyEvent.VK_D)
            {
                myGrid.doMove(Move.RIGHT);
            }
            else if (code == KeyEvent.VK_Q)
            {
                myGrid.doMove(Move.FORWARD);
            }
            else if (code == KeyEvent.VK_E)
            {
                myGrid.doMove(Move.BACKWARD);
            }
            else { }
            repaint();
            System.out.println(myGrid);
        }

        public void keyReleased(KeyEvent e)
        {

        }

        public void keyTyped(KeyEvent e)
        {

        }
    }
}