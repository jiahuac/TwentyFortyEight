import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Paused Screen class for 3D-2048 game
 * Paused screen for 2048 game
 *
 * @author Jiahua Chen
 * @version Final-1.0 06.02.2019 2:00pm
 *
 * COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved.
 */
public class PauseScreen extends JPanel
{
	/**
	 * Background image of PauseScreen
	 */
	private static BufferedImage myBackground;
	
	/**
	 * GameApp
	 */
	private GameApp myApp;
	
	/**
	 * Buttons for menu
	 */
	private ImageButton resumeButton;
	private boolean resumeHover;
	private ImageButton resetButton;
	private boolean resetHover;
	
	/**
	 * Constructor for PauseScreen
	 *
	 * @param app GameApp passed down from App class
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
	 * Paints the PauseScreen
	 *
	 * @param g graphics obj
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
				null);
		
		resetButton.draw(g2, resetHover);
		resumeButton.draw(g2, resumeHover);
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
	
	private void resetHover()
	{
		resetHover = false;
		resumeHover = false;
	}
	
	private class MyButtonListener implements MouseListener
	{
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
