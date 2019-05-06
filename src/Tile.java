import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;

/**
 * Tile class for 3D-2048 game
 * Individual Tile objects for the game grid
 * @author Jiahua Chen
 * @version 0.02 04.02.2019
 */

public class Tile
{
    /** power of the individual tiles, -1 if empty */
    private int power;

    /** location of the individual tile, using a Loc object*/
    private Loc loc;

    /** power of empty tile */
    private static final int EMPTY_POWER = 0;

    /** move state of a tile */
    private boolean moved;

    private static BufferedImage[] myTilesImage;

    private static boolean imagesLoaded = false;

    /** default constructor */
    public Tile()
    {
        this(new Loc(0,0,0));
    }

    /** constructs an empty tile at location */
    public Tile(Loc loc)
    {
        this(EMPTY_POWER, loc);
    }

    /** constructs a tile with power pow at location */
    public Tile(int pow, Loc loc)
    {
        this.power = pow;
        this.loc = loc;
        this.moved = false;
        if (!imagesLoaded)
        {
            myTilesImage = new BufferedImage[14];
            for (int i = 0; i < myTilesImage.length; i++)
            {
                try
                {
                    InputStream is = getClass().getResourceAsStream("/tileimages/" + i + ".jpg");
                    myTilesImage[i] = ImageIO.read(is);
                }
                catch(IOException ioe)
                {
                    System.out.println("InputStream ERROR");
                }
            }
            imagesLoaded = true;
        }
    }

    /**
     * attempts to set the power to pow
     * @return true if successful; false if not (or if power already equals power)
     */
    public boolean setPower(int pow)
    {
        if (this.power == pow) { return false; }
        else
        {
            this.power = pow;
            return true;
        }
    }

    /**
     * increments the power of the tile
     * @return new power of the tile
     */
    public int incrementPower()
    {
        this.moved();
        return ++this.power;
    }

    /**
     * attempts to get the power of the tile
     * @return power of the tile
     */
    public int getPower()
    {
        return this.power;
    }

    public void setLoc(Loc newLoc)
    {
        this.loc = newLoc;
    }

    /**
     * attempts to get the location of the tile
     * @return location of the tile
     */
    public Loc getLoc()
    {
        return this.loc;
    }

    /**
     * toString method of the Tile
     * @return integer value of tile (2^power), or " " if empty
     */
    public String toString()
    {
         if ( this.isEmpty() ) { return "-"; }
         else { return String.valueOf((int) Math.pow(2, this.power)); }
        // Debug \/
//        return "[<" + this.power + ">, " + this.loc + ", " + this.moved + "]";
    }

    /**
     * checks if tile is empty
     * @return true if tile is empty, else false
     */
    public boolean isEmpty()
    {
        return this.power == EMPTY_POWER;
    }

    /**
     * tries to initiate a new tile at an empty tile
     * @return power of new tile, 0 if empty
     */
    public int initiate()
    {
        if (this.isEmpty())
        {
            this.setPower((int) (1 + Math.random() * 2));
            return this.getPower();
        }
        else { return 0; }
    }

    public boolean canMove()
    {
        return !this.moved;
    }

    public void moved()
    {
        this.moved = true;
    }

    public void resetMoved()
    {
        this.moved = false;
    }

    public void drawMe(Graphics2D g)
    {
        g.setFont(new Font("Dialog", Font.BOLD, 20));
        int xVal = 100 + this.loc.stack * 200 + this.loc.col * 60;
        int yVal = 100 + this.loc.row * 60;
        g.drawImage(myTilesImage[this.getPower()], xVal, yVal, 50, 50, null);
    }
}