import javax.swing.*;
import java.awt.event.*;

/**
 * KeyHandler class for 3D-2048 game
 * KeyHandler for the GameScreen and InstructionScreen (it's used twice so it's a separate class)
 *
 * @author Jiahua Chen
 * @version Final-1.0 06.02.2019 2:00pm
 */
public class GameKeyHandler implements KeyListener
{
	/* Game Grid */
	private Grid myGrid;
	
	/**
	 * GameKeyHandler Constructor
	 *
	 * @param grid   grid obj of the game
	 * @param screen screen obj of the game
	 */
	public GameKeyHandler(Grid grid, JPanel screen)
	{
		this.myGrid = grid;
	}
	
	/**
	 * Actions on keypress
	 *
	 * @param e KeyEvent
	 */
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
		else
		{
		}
		this.myGrid.resetLoc();
		System.out.println(myGrid);
	}
	
	/**
	 * actions on key release
	 * does nothing
	 *
	 * @param e KeyEvent
	 */
	public void keyReleased(KeyEvent e)
	{
	
	}
	
	/**
	 * actions on key typed
	 * does nothing
	 *
	 * @param e KeyEvent
	 */
	public void keyTyped(KeyEvent e)
	{
	
	}
}