import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 Paused Screen class for 3D-2048 game
 Paused screen for 2048 game
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 12:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */
public class PauseScreen extends JPanel
{
	/** Background image of PauseScreen */
	private static BufferedImage myBackground;
	
	/** GameApp of the game */
	private GameApp myApp;
	
	/** resume button */
	private ImageButton resumeButton;
	
	/** mouse hovering over resume button */
	private boolean resumeHover;
	
	/** reset button */
	private ImageButton resetButton;
	
	/** mouse hovering over reset button */
	private boolean resetHover;
	
	/**
	 Constructor for PauseScreen
	 @param app GameApp passed down from App class
	 */
	public PauseScreen(GameApp app)
	{
		myApp = app;
		this.addMouseListener(new MyButtonListener());
		this.addMouseMotionListener(new MyHoverListener());
		
		resetButton = new ImageButton(302, 233, 166, 45, "/screens" +
				"/PauseResetButton.png", "/screens/PauseResetHover.png");
		
		resumeButton = new ImageButton(274, 182, 218, 24, "/screens" +
				"/PauseResumeButton.png", "/screens/PauseResumeHover.png");
		
		try
		{
			InputStream is = getClass().getResourceAsStream("/screens" +
					"/PauseScreen.png");
			myBackground = ImageIO.read(is);
		}
		catch (IOException ioe)
		{
			System.out.println("InputStream ERROR");
		}
	}
	
	/**
	 Paints the PauseScreen
	 @param g graphics obj
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
				null);
		
		resetButton.draw(g2, resetHover);
		resumeButton.draw(g2, resumeHover);
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
				resumeHover = false;
				repaint();
			}
			else if (resumeButton.doesContain(e))
			{
				resetHover = false;
				resumeHover = true;
				repaint();
			}
			else
			{
				resetHover();
				repaint();
			}
		}
	}
	
	/**
	 resets the hovering status of both buttons
	 */
	private void resetHover()
	{
		resetHover = false;
		resumeHover = false;
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
			if (resumeButton.doesContain(e))
			{
				myApp.loadGameScreen();
				resetHover();
			}
			else if (resetButton.doesContain(e))
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
}
