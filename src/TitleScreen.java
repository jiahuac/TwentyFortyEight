import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 Title Screen class for 3D-2048 game
 Introductory Title Screen for 2048 game
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 12:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */
public class TitleScreen extends JPanel
{
	/** GameApp obj */
	private GameApp myApp;
	
	/** Button to start game */
	private ImageButton playButton;
	
	/** is mouse hovering over play button */
	private boolean playButtonHover;
	
	/** Button to go to instructions */
	private ImageButton instructionsButton;
	
	/** is mouse hovering over instructions button */
	private boolean instructionsButtonHover;
	
	/** Background image (the tiles) */
	private static BufferedImage myBackground;
	
	/**
	 constructs a new TitleScreen
	 @param app GameApp object
	 */
	public TitleScreen(GameApp app)
	{
		myApp = app;
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		this.addMouseListener(new MyButtonListener());
		this.addMouseMotionListener(new MyHoverListener());
		
		playButton = new ImageButton(70, 178, 78, 38,
				"/screens/PlayButton.png", "/screens/PlayHover.png");
		
		instructionsButton = new ImageButton(70, 254, 223, 29,
				"/screens/InstructionsButton.png", "/screens" +
				"/InstructionsHover" +
				".png");
		
		try
		{
			InputStream is = getClass().getResourceAsStream("/screens" +
					"/TitleScreenBackground.png");
			myBackground = ImageIO.read(is);
		}
		catch (IOException ioe)
		{
			System.out.println("InputStream ERROR");
		}
	}
	
	/**
	 Paints the title screen
	 @param g graphics obj
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
				null);
		
		playButton.draw(g2, playButtonHover);
		
		instructionsButton.draw(g2, instructionsButtonHover);
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
			if (playButton.doesContain(e))
			{
				playButtonHover = true;
				instructionsButtonHover = false;
				repaint();
			}
			else if (instructionsButton.doesContain(e))
			{
				playButtonHover = false;
				instructionsButtonHover = true;
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
	 resets the hovering state (for when mouse is outside buttons)
	 */
	private void resetHover()
	{
		playButtonHover = false;
		instructionsButtonHover = false;
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
			if (playButton.doesContain(e))
			{
				System.out.println("Loading Game...");
				myApp.loadGameScreen();
			}
			else if (instructionsButton.doesContain(e))
			{
				myApp.loadInstructionScreen();
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
