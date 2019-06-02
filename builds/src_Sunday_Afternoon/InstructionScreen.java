import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Instructions Screen class for 3D-2048 game
 * Instructions Screen for 2048 game
 *
 * @author Jiahua Chen
 * @version Final-1.0 06.02.2019 2:00pm
 */
public class InstructionScreen extends JPanel
{
	/**
	 * Background of InstructionScreen
	 */
	private static BufferedImage myBackground;
	/**
	 * GridLocked prompt
	 */
	private static BufferedImage gridLocked;
	
	/**
	 * Buttons in Instructions Screen
	 */
	private ImageButton resetButton;
	private boolean resetHover;
	private ImageButton playButton;
	private boolean playHover;
	private ImageButton menuButton;
	private boolean menuHover;
	
	/**
	 * App of the game
	 */
	private GameApp myApp;
	
	/**
	 * smaller grid for Instruction screen
	 */
	private Grid myGrid;
	
	/**
	 * Constructs an InstructionScreen
	 *
	 * @param app myApp passed down from app class
	 */
	public InstructionScreen(GameApp app)
	{
		myApp = app;
		myGrid = new Grid(2);
		this.addMouseListener(new MyButtonListener());
		this.addMouseMotionListener(new MyHoverListener());
		FieldUpdater up = new FieldUpdater();
		this.addKeyListener(new GameKeyHandler(myGrid, this));
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		resetButton = new ImageButton(35, 317, 155, 15, "/screens" +
				"/insResetButton.png", "/screens/insResetHover.png");
		
		playButton = new ImageButton(246, 317, 136, 19, "/screens" +
				"/insPlayButton.png", "/screens/insPlayHover.png");
		
		menuButton = new ImageButton(440, 317, 127, 15, "/screens" +
				"/insMenuButton.png", "/screens/insMenuHover.png");
		
		try
		{
			InputStream is = getClass().getResourceAsStream("/screens" +
					"/insScreen.png");
			myBackground = ImageIO.read(is);
			is = getClass().getResourceAsStream("/screens" +
					"/InstructionsGridLock.png");
			gridLocked = ImageIO.read(is);
		}
		catch (IOException ioe)
		{
			System.out.println("InputStream ERROR");
		}
		
		up.start();
	}
	
	/**
	 * Painting screen
	 * @param g graphics obj passed down
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
				null);
		
		resetButton.draw(g2, resetHover);
		menuButton.draw(g2, menuHover);
		playButton.draw(g2, playHover);
		
		myGrid.drawBoard(g2);
		for (Tile t : myGrid.getTiles())
		{
			t.drawMe(g2);
		}
		
		if (myGrid.gridLocked())
		{
			g2.drawImage(gridLocked, 36, 241, 401,
					51,
					null);
		}
	}
	
	private class MyHoverListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
		}
		
		public void mouseMoved(MouseEvent e)
		{
			
			if (resetButton.doesContain(e))
			{
				resetHover = true;
				playHover = false;
				menuHover = false;
			}
			else if (playButton.doesContain(e))
			{
				resetHover = false;
				playHover = true;
				menuHover = false;
			}
			else if (menuButton.doesContain(e))
			{
				resetHover = false;
				playHover = false;
				menuHover = true;
			}
			else
			{
				resetHover();
			}
		}
	}
	
	/**
	 * Resets the hovering status of button
	 */
	public void resetHover()
	{
		resetHover = false;
		playHover = false;
		menuHover = false;
	}
	
	private class MyButtonListener implements MouseListener
	{
		public void mousePressed(MouseEvent e)
		{
			if (resetButton.doesContain(e))
			{
				myGrid.rebuildGrid();
			}
			else if (playButton.doesContain(e))
			{
				myApp.loadGameScreen();
				resetHover();
			}
			else if (menuButton.doesContain(e))
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
	
	private class FieldUpdater extends Thread
	{
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
