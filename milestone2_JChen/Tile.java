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

    public static void main(String[] args)
    {
        // Creates a new, empty Tile object
        Tile tile1 = new Tile();

        System.out.print("tile1: ");
        // Prints the empty tile object, checking the toString method (when empty)
        // We expect the empty output "-" here
        System.out.println(tile1);

        System.out.print("tile1 isEmpty: ");
        // Checks if tile1 is empty, checking the isEmpty method
        // Expected return true
        System.out.println(tile1.isEmpty());

        System.out.print("tile1 setPower(3): ");
        // Using the setPower method, sets the power of tile1 to 3
        // then prints the output, expected true as it was successful
        System.out.println(tile1.setPower(3));

        System.out.print("tile1 setPower(3) - second time: ");
        // When we set the power to 3 again, as it is already 3, expected false
        System.out.println(tile1.setPower(3));

        System.out.print("tile1 toString: ");
        // Prints tile1 again, we expect the toString method to return 2^3=8
        System.out.println(tile1);

        System.out.print("tile1 getPower: ");
        // Prints the *power* of tile1, which should return 3
        System.out.println(tile1.getPower());

        // Creates a new, empty Tile, tile2
        Tile tile2 = new Tile();

        System.out.print("tile2: ");
        // Prints tile2, we expect it to be empty
        System.out.println(tile2);

        System.out.print("tile2 initiate: ");
        // Initiates tile2, which initiates tile2 with a random power 1 or 2
        // the function should return the power of tile2
        System.out.println(tile2.initiate());

        System.out.print("tile2 toString: ");
        // Now we expect tile2 to equal 2^p, where p was the random power above
        System.out.println(tile2);

    }
}