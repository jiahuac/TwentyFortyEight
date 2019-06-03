import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 Image Button class for 3D-2048 game
 Used for image buttons for 2048 game
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 12:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */
public class ImageButton
{
	
	/** x coord of button */
	private int x;
	
	/** y coord of button */
	private int y;
	
	/** width of button */
	private int w;
	
	/** height of button */
	private int h;
	
	/** image of button, not hovering */
	private BufferedImage buttonImage;
	
	/** image of button, hovering */
	private BufferedImage buttonImageHover;
	
	/**
	 constructor for ImageButton
	 @param x x pos of button
	 @param y y pos of button
	 @param w width of button
	 @param h height of button
	 @param image not hovering image of button
	 @param hover hovering image of button
	 */
	public ImageButton(int x, int y, int w, int h, String image, String hover)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		try
		{
			InputStream is = getClass().getResourceAsStream(image);
			buttonImage = ImageIO.read(is);
			is = getClass().getResourceAsStream(hover);
			buttonImageHover = ImageIO.read(is);
		}
		catch (IOException ioe)
		{
			System.out.println("InputStream ERROR");
		}
	}
	
	/**
	 Draws a button at the button location
	 @param g graphics object passed down from Screen
	 */
	public void drawButton(Graphics2D g)
	{
		g.drawImage(buttonImage, x, y, w, h, null);
	}
	
	/**
	 Draws the button being hovered at button location
	 @param g graphics object passed down from Screen
	 */
	public void drawHover(Graphics2D g)
	{
		g.drawImage(buttonImageHover, x, y, w, h, null);
	}
	
	/**
	 Draws the button, and automatically adjusts based on hovering or not
	 @param g graphics object passed down from Screen
	 @param hover is the mouse hovering over the button or not
	 */
	public void draw(Graphics2D g, boolean hover)
	{
		if (hover)
		{
			drawHover(g);
		}
		else
		{
			drawButton(g);
		}
	}
	
	/**
	 Checks if a MouseEvent is contained within the button
	 @param e MouseEvent passed down from MouseListener
	 @return true if mouse is in button, else false
	 */
	public boolean doesContain(MouseEvent e)
	{
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		return (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h);
	}
}
