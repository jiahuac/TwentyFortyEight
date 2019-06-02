import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;

/**
 * Tile class for 3D-2048 game
 * Individual Tile objects for the game grid
 *
 * @author Jiahua Chen
 * @version Final-1.0 06.02.2019 2:00pm
 */

public class Tile
{
	/**
	 * power of the individual tiles, -1 if empty
	 */
	private int power;
	
	/**
	 * location of the individual tile, using a Loc object
	 */
	private Loc loc;
	private Loc newLoc;
	
	private int drawStep;
	
	/**
	 * power of empty tile
	 */
	private static final int EMPTY_POWER = 0;
	
	/**
	 * move state of a tile
	 */
	private boolean moved;
	
	public static BufferedImage[] myTilesImage;
	
	private static boolean imagesLoaded = false;
	
	/**
	 * default constructor
	 */
	public Tile()
	{
		this(new Loc(0, 0, 0));
	}
	
	/**
	 * constructs an empty tile at location
	 * @param loc location tile is being constructed at
	 */
	public Tile(Loc loc)
	{
		this(EMPTY_POWER, loc);
	}
	
	/**
	 * constructs a tile with power pow at location
	 * @param pow power of tile
	 * @param loc location being constructed at
	 */
	public Tile(int pow, Loc loc)
	{
		this.power = pow;
		this.setLoc(loc);
		this.moved = false;
		if (!imagesLoaded)
		{
			myTilesImage = new BufferedImage[14];
			for (int i = 0; i < myTilesImage.length; i++)
			{
				try
				{
					InputStream is = getClass().getResourceAsStream("/tileimages/" + i + ".jpg");
					myTilesImage[i] = makeRoundedCorner(ImageIO.read(is), 16);
				}
				catch (IOException ioe)
				{
					System.out.println("InputStream ERROR");
				}
			}
			imagesLoaded = true;
		}
	}
	
	/**
	 * attempts to set the power to pow
	 *
	 * @param pow power to set to
	 * @return true if successful; else false (or if pow are equal)
	 */
	public boolean setPower(int pow)
	{
		if (this.power == pow)
		{
			return false;
		}
		else
		{
			this.power = pow;
			return true;
		}
	}
	
	/**
	 * increments the power of the tile
	 *
	 * @return new power of the tile
	 */
	public int incrementPower()
	{
		this.moved();
		return ++this.power;
	}
	
	/**
	 * attempts to get the power of the tile
	 *
	 * @return power of the tile
	 */
	public int getPower()
	{
		return this.power;
	}
	
	/**
	 * Sets both locs of tile to newLoc
	 *
	 * @param newLoc new Location of tile, after animation is over
	 */
	public void setLoc(Loc newLoc)
	{
		this.loc = newLoc;
		this.newLoc = newLoc;
	}
	
	/**
	 * Sets new Loc of tile to newLoc, allows for animation, tile remembers
	 * old loc and animates from old loc to new loc
	 *
	 * @param newLoc new Location of tile
	 */
	public void setNewLoc(Loc newLoc)
	{
		this.newLoc = newLoc;
	}
	
	/**
	 * attempts to get the location of the tile
	 *
	 * @return location of the tile
	 */
	public Loc getLoc()
	{
		return this.loc;
	}
	
	/**
	 * toString method of the Tile
	 *
	 * @return integer value of tile (2^power), or " " if empty
	 */
	public String toString()
	{
		if (this.isEmpty())
		{
			return "-";
		}
		else
		{
			return String.valueOf((int) Math.pow(2, this.power));
		}
	}
	
	/**
	 * checks if tile is empty
	 *
	 * @return true if tile is empty, else false
	 */
	public boolean isEmpty()
	{
		return this.power == EMPTY_POWER;
	}
	
	/**
	 * tries to initiate a new tile at an empty tile
	 *
	 * @return power of new tile, 0 if empty
	 */
	public int initiate()
	{
		if (this.isEmpty())
		{
			this.setPower((int) (1 + Math.random() * 2));
			/** To test the win screen: */
//            this.setPower(10);
			return this.getPower();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * Checks if tile can be moved (if it's already been moved then cannot)
	 *
	 * @return NOT moved, so if the tile has been moved it returns false
	 */
	public boolean canMove()
	{
		return !this.moved;
	}
	
	/**
	 * Tells tile that the tile has been moved
	 */
	public void moved()
	{
		this.moved = true;
	}
	
	/**
	 * Resets the moved state of tile
	 */
	public void resetMoved()
	{
		this.moved = false;
	}
	
	/**
	 * Draws the tile object on the board
	 *
	 * @param g graphics obj passed down
	 */
	public void drawMe(Graphics2D g)
	{
		int xVal, yVal;
		if (isEmpty())
		{
			setLoc(newLoc);
			xVal = getXCoord(loc);
			yVal = getYCoord(loc);
		}
		else
		{
			xVal = getXCoord(loc) + (drawStep * (getXCoord(newLoc) - getXCoord(loc))) / 60;
			yVal = getYCoord(loc) + (drawStep * (getYCoord(newLoc) - getYCoord(loc))) / 60;
			if (!loc.equals(newLoc))
			{
				drawStep++;
				int adjStep = drawStep % 60;
				if (drawStep != adjStep)
				{
					drawStep = adjStep;
					setLoc(newLoc);
				}
			}
		}
		drawTile(g, xVal, yVal);
	}
	
	/**
	 * Gets the screen Y coord from board relative position
	 *
	 * @param l Loc of tile
	 * @return Y position on screen/graphics of tile
	 */
	public static int getYCoord(Loc l)
	{
		return 100 + l.row * 60;
	}
	
	/**
	 * Gets the screen X coord from board relative position
	 *
	 * @param l Loc of tile
	 * @return X position on screen/graphics of tile
	 */
	public static int getXCoord(Loc l)
	{
		return 100 + l.stack * 200 + l.col * 60;
	}
	
	/**
	 * Draws tile at specific location on screen
	 *
	 * @param g graphics obj passed down
	 * @param x X coord on screen to draw
	 * @param y Y coord on screen to draw
	 */
	public void drawTile(Graphics2D g, int x, int y)
	{
		if (!isEmpty())
		{
			if (getPower() < 14)
			{
				g.drawImage(myTilesImage[this.getPower()], x, y, 50, 50, null);
			}
			else
			{
				g.setFont(new Font("PT Sans Caption", Font.BOLD, 11));
				g.drawString(Integer.toString((int) Math.pow(2,
						this.getPower())), x + 7,
						y + 30);
			}
		}
	}
	
	/*
		START of copied code to make BufferedImage with rounded corners
		
		source:
		https://stackoverflow.com/questions/7603400/how-to-make-a-rounded-corner-image-in-java
	 */
	public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = output.createGraphics();
		
		// This is what we want, but it only does hard-clipping, i.e. aliasing
		// g2.setClip(new RoundRectangle2D ...)
		
		// so instead fake soft-clipping by first drawing the desired clip shape
		// in fully opaque white with antialiasing enabled...
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
		
		// ... then compositing the image on top,
		// using the white shape from above as alpha source
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(image, 0, 0, null);
		
		g2.dispose();
		
		return output;
	}
    /*
        END of copied code
    */
}