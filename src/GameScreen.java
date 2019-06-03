import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 GameScreen class for 3D-2048 game
 Graphics and drawing for 2048 game
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 8:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */

public class GameScreen extends JPanel
{
	
	/** GameApp objects */
	private GameApp myApp;
	
	/** game grid object */
	private Grid myGrid;
	
	/** Reminder Button for tooltip */
	private ImageButton remindButton;
	
	/** mouse hovering over reminder button */
	private boolean remindHover;
	
	/** Pause Button */
	private ImageButton pauseButton;
	
	/** mouse hovering over pause button */
	private boolean pauseHover;
	
	/** Score Button/Hover tooltip */
	private ImageButton scoreButton;
	
	/** mouse hovering over score button */
	private boolean scoreHover;
	
	/** show text to prompt user to hover (first time true) */
	private boolean hoverTooltip = true;
	
	/** Background for WIN screen */
	private BufferedImage winBackground;
	
	/** display win screen */
	private boolean winScreen = true;
	
	/** Button to continue playing after win */
	private ImageButton winContinueButton;
	
	/** mouse hovering over continue after winning button */
	private boolean winContinueHover;
	
	/** Button for menu after win */
	private ImageButton winMenuButton;
	
	/** mouse hovering over menu after winning button */
	private boolean winMenuHover;
	
	/** Background for LOSE screen */
	private BufferedImage loseBackground;
	
	/** Button for menu after lose */
	private ImageButton loseMenuButton;
	
	/** mouse hovering over lose menu button */
	private boolean loseMenuHover;
	
	/** Game background, and tooltip views */
	private static BufferedImage myBackground;
	
	/** control tooltip image */
	private static BufferedImage myTooltip;
	
	/** score tooltip image */
	private static BufferedImage myScoreTooltip;
	
	/** tile color */
	private static final Color TILE_GREY = new Color(119, 110, 101);
	
	/**
	 GameScreen Constructor
	 @param grid Grid of the game
	 @param app GameApp of the Game (for changing screens)
	 */
	public GameScreen(Grid grid, GameApp app)
	{
		myGrid = grid;
		myApp = app;
		this.addKeyListener(new GameKeyHandler(myGrid));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addMouseListener(new MyButtonListener());
		this.addMouseMotionListener(new MyHoverListener());
		
		remindButton = new ImageButton(535, 316, 201, 12,
				"/screens/RemindButton.png", "/screens/RemindHover.png");
		pauseButton = new ImageButton(662, 24, 74, 38, "/screens/GamePauseButton" +
				".png", "/screens/GamePauseHover.png");
		scoreButton = new ImageButton(455, 24, 200, 38, "/screens/ScoreButton" +
				".png", "/screens/ScoreHover.png");
		
		winContinueButton = new ImageButton(252, 193, 138, 38, "/screens" +
				"/WinContinueButton.png", "/screens/WinContinueHover.png");
		winMenuButton = new ImageButton(413, 193, 104, 38, "/screens" +
				"/MenuButton.png", "/screens/MenuHover.png");
		
		loseMenuButton = new ImageButton(335, 193, 104, 38, "/screens" +
				"/MenuButton.png", "/screens/MenuHover.png");
		
		try
		{
			InputStream is = getClass().getResourceAsStream("/screens" +
					"/GameScreen.png");
			myBackground = ImageIO.read(is);
			is = getClass().getResourceAsStream("/screens" +
					"/ControlTooltip.png");
			myTooltip = ImageIO.read(is);
			is = getClass().getResourceAsStream("/screens" +
					"/ScoreTooltip.png");
			myScoreTooltip = ImageIO.read(is);
			is = getClass().getResourceAsStream("/screens" +
					"/WinPrompt.png");
			winBackground = ImageIO.read(is);
			is = getClass().getResourceAsStream("/screens" +
					"/LosePrompt.png");
			loseBackground = ImageIO.read(is);
		}
		catch (IOException ioe)
		{
			System.out.println("InputStream ERROR");
		}
		
		FieldUpdater up = new FieldUpdater();
		up.start();
	}
	
	/**
	 Paints the screen
	 @param g Graphics object
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
				null);
		
		remindButton.draw(g2, remindHover);
		pauseButton.draw(g2, pauseHover);
		
		scoreButton.draw(g2, scoreHover);
		
		myGrid.drawBoard(g2);
		for (Tile t : myGrid.getTiles())
		{
			t.drawMe(g2);
		}
		
		if (remindHover)
		{
			g2.drawImage(myTooltip, 202, 69, 364,
					225,
					null);
		}
		
		g2.setFont(new Font("PT Sans Caption", Font.BOLD, 17));
		g2.setColor(TILE_GREY);
		if (scoreHover)
		{
			g2.setColor(Color.BLACK);
			g2.drawImage(myScoreTooltip, 227, 12, 222,
					67,
					null);
		}
		g2.drawString(Integer.toString(myGrid.getScore()), 534, 49);
		
		if (hoverTooltip)
		{
			g2.setFont(new Font("PT Sans Caption", Font.PLAIN, 8));
			g2.drawString("Hover over me to see an explanation of scores", 463,
					74);
		}
		
		if (winScreen && myGrid.hasWon())
		{
			g2.drawImage(winBackground, -1, 0, GameApp.WIDTH,
					GameApp.HEIGHT - 20,
					null);
			winContinueButton.draw(g2, winContinueHover);
			winMenuButton.draw(g2, winMenuHover);
		}
		
		if (myGrid.gridLocked())
		{
			g2.drawImage(loseBackground, -1, 0, GameApp.WIDTH,
					GameApp.HEIGHT - 20,
					null);
			loseMenuButton.draw(g2, loseMenuHover);
		}
	}
	
	/**
	 Resets the win status, for starting new game after win.
	 */
	public void resetWin()
	{
		winScreen = true;
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
			if (winScreen && myGrid.hasWon())
			{
				resetHover();
				if (winContinueButton.doesContain(e))
				{
					winContinueHover = true;
					winMenuHover = false;
				}
				else if (winMenuButton.doesContain(e))
				{
					winContinueHover = false;
					winMenuHover = true;
				}
				else
				{
					winContinueHover = false;
					winMenuHover = false;
				}
			}
			else if (myGrid.gridLocked())
			{
				if (loseMenuButton.doesContain(e))
				{
					loseMenuHover = true;
				}
				else
				{
					loseMenuHover = false;
				}
			}
			else
			{
				if (remindButton.doesContain(e))
				{
					remindHover = true;
					pauseHover = false;
					scoreHover = false;
				}
				else if (pauseButton.doesContain(e))
				{
					remindHover = false;
					pauseHover = true;
					scoreHover = false;
				}
				else if (scoreButton.doesContain(e))
				{
					hoverTooltip = false;
					remindHover = false;
					pauseHover = false;
					scoreHover = true;
				}
				else
				{
					resetHover();
				}
			}
		}
	}
	
	/**
	 Resets the Hovering status, so no buttons are highlighted
	 */
	public void resetHover()
	{
		remindHover = false;
		pauseHover = false;
		scoreHover = false;
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
			if (winScreen && myGrid.hasWon())
			{
				if (winContinueButton.doesContain(e))
				{
					winScreen = false;
				}
				else if (winMenuButton.doesContain(e))
				{
					winContinueHover = false;
					winMenuHover = false;
					myApp.loadTitleScreen();
				}
			}
			else if (myGrid.gridLocked())
			{
				if (loseMenuButton.doesContain(e))
				{
					loseMenuHover = false;
					myApp.loadTitleScreen();
				}
			}
			else
			{
				if (pauseButton.doesContain(e))
				{
					resetHover();
					myApp.loadPauseScreen();
				}
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
}