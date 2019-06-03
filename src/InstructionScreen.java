import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 Instructions Screen class for 3D-2048 game
 Instructions Screen for 2048 game
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 12:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */
public class InstructionScreen extends JPanel
{
	/** Background of InstructionScreen */
	private static BufferedImage myBackground;
	
	/** GridLocked prompt */
	private static BufferedImage gridLocked;
	
	/** reset button */
	private ImageButton resetButton;
	
	/** mouse hovering over reset button */
	private boolean resetHover;
	
	/** play button */
	private ImageButton playButton;
	
	/** mouse hovering over play button */
	private boolean playHover;
	
	/** menu button */
	private ImageButton menuButton;
	
	/** mouse hovering over menu button */
	private boolean menuHover;
	
	/** App of the game */
	private GameApp myApp;
	
	/** smaller grid for Instruction screen */
	private Grid myGrid;
	
	/**
	 Constructs an InstructionScreen
	 @param app myApp passed down from app class
	 */
	public InstructionScreen(GameApp app)
	{
		myApp = app;
		myGrid = new Grid(2);
		this.addMouseListener(new MyButtonListener());
		this.addMouseMotionListener(new MyHoverListener());
		FieldUpdater up = new FieldUpdater();
		this.addKeyListener(new GameKeyHandler(myGrid));
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
	 Painting screen
	 @param g graphics obj passed down
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
	
	/**
	 Listens for mouse hovering over buttons.
	 */
	private class MyHoverListener implements MouseMotionListener
	{
		/**
		 Does nothing when mouse is dragged
		 @param e MouseEvent
		 */
		public void mouseDragged(MouseEvent e)
		{
		}
		
		/**
		 Actions when mouse if moved, checks if mouse is in the buttons
		 @param e MouseEvent
		 */
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
	 Resets the hovering status of button
	 */
	public void resetHover()
	{
		resetHover = false;
		playHover = false;
		menuHover = false;
	}
	
	/**
	 Listens for mouse clicking on buttons
	 */
	private class MyButtonListener implements MouseListener
	{
		/**
		 Checks for when mouse is pressed, changes screen when it is.
		 @param e MouseEvent
		 */
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
		
		/**
		 Does nothing when mouse is released
		 @param e MouseEvent
		 */
		public void mouseReleased(MouseEvent e)
		{
		
		}
		
		/**
		 Does nothing when mouse is clicked (not pressed)
		 @param e MouseEvent
		 */
		public void mouseClicked(MouseEvent e)
		{
		
		}
		
		/**
		 Does nothing when mouse enters screen
		 @param e MouseEvent
		 */
		public void mouseEntered(MouseEvent e)
		{
		
		}
		
		/**
		 Does nothing when mouse exits screen
		 @param e MouseEvent
		 */
		public void mouseExited(MouseEvent e)
		{
		
		}
	}
	
	/**
	 FieldUpdater updating the screen every frame.
	 */
	private class FieldUpdater extends Thread
	{
		/**
		 Runs the FieldUpdater Thread
		 */
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
					System.out.println("FieldUpdater InterruptedException");
				}
			}
		}
	}
}
