import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Title Screen class for 3D-2048 game
 * Introductory Title Screen for 2048 game
 *
 * @author Jiahua Chen
 * @version Final-1.1 06.02.2019 8:00pm
 *
 * COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved.
 */
public class TitleScreen extends JPanel
{
	/**
	 * GameApp obj
	 */
	private GameApp myApp;
	
	/**
	 * Button to start game
	 */
	private ImageButton playButton;
	private boolean playButtonHover;
	
	/**
	 * Button to go to instructions
	 */
	private ImageButton instructionsButton;
	private boolean instructionsButtonHover;
	
	/**
	 * Background image (tiles)
	 */
	private static BufferedImage myBackground;
	
	/**
	 * constructs a new TitleScreen
	 *
	 * @param app GameApp object
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
	 * Paints the title screen
	 *
	 * @param g graphics obj
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
				null);
		
		playButton.draw(g2, playButtonHover);
		
		instructionsButton.draw(g2, instructionsButtonHover);
	}
	
	
	private class MyHoverListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
		}
		
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
	 * resets the hovering state (for when mouse is outside buttons)
	 */
	private void resetHover()
	{
		playButtonHover = false;
		instructionsButtonHover = false;
	}
	
	private class MyButtonListener implements MouseListener
	{
		public void mousePressed(MouseEvent e)
		{
			if (playButton.doesContain(e))
			{
				myApp.loadGameScreen();
			}
			else if (instructionsButton.doesContain(e))
			{
				myApp.loadInstructionScreen();
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
