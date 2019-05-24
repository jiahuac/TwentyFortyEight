import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * KeyHandler class for 3D-2048 game
 * KeyHandler for the GameScreen and InstructionScreen (it's used twice so it's a separate class)
 * @author Jiahua Chen
 * @version alph-1.0 05.05.2019
 */
public class GameKeyHandler implements KeyListener
{
    private Grid myGrid;
    private JPanel myScreen;

    public GameKeyHandler(Grid grid, JPanel screen)
    {
        this.myGrid = grid;
        this.myScreen = screen;
    }

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
        ((GameScreen) myScreen).paintSequence();
        System.out.println(myGrid);
    }

    public void keyReleased(KeyEvent e)
    {

    }

    public void keyTyped(KeyEvent e)
    {

    }
}