import java.util.ArrayList;
import java.awt.event.*;
// Scanner for temp input control
import java.util.Scanner;


/**
 * Grid class for 3D-2048 game
 * Basic data-structure of tile objects
 * @author Jiahua Chen
 * @version 0.02 04.18.2019 Milestone 2
 */

public class Grid
{
    /** given maximum size of the game grid (dimensions) */
    private static final int MAX_SIZE = 3;

    /** myGrid object, consisting of 3d Tile object array */
    private Tile[][][] myGrid;

    private int score = 0;

    public int getScore()
    {
        return score;
    }

    /** default constructor constructs a grid of MAX_SIZE */
    public Grid()
    {
        this(MAX_SIZE);
    }

    /** constructs a grid of dimensions/side length size, with all empty tiles */
    public Grid(int size)
    {
        myGrid = new Tile[size][size][size];
        for (int stack = 0; stack < myGrid.length; stack++)
        {
            for (int row = 0; row < myGrid[0].length; row++)
            {
                for (int col = 0; col < myGrid[0][0].length; col++)
                {
                    myGrid[stack][row][col] = new Tile(new Loc(stack, row, col));
                }
            }
        }
    }

    /**
     * gets tile at target location
     * @param target loc of target Tile
     * @return tile at location
     */
    public Tile getTile(Loc target)
    {
        try { return myGrid[target.stack][target.row][target.col]; }
        catch (ArrayIndexOutOfBoundsException e) { return null; }
    }

    /**
     * sets a coordinate in the grid with power pow
     * @param target loc of target Tile
     * @param pow to set target Tile
     * @return if set was successful, i.e. if it changed the power at all
     */
    public boolean setTile(Loc target, int pow)
    {
        try { return myGrid[target.stack][target.row][target.col].setPower(pow); }
        catch (ArrayIndexOutOfBoundsException e) { return false; }
    }

    /**
     * increments a tile at a given location
     * @param target loc of target Tile
     * @return new power of target tile
     */
    public int incrementTile(Loc target)
    {
        return myGrid[target.stack][target.row][target.col].incrementPower();
    }

    /**
     * prints the Grid
     * @return String visual representation of grid
     */
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        for (Tile[][] stack : myGrid)
        {
            for (Tile[] row : stack)
            {
                for (Tile box : row)
                {
                    out.append(box + " ");
                }
                out.append("\n");
            }
            out.append("\n");
        }
        return out.toString();
    }

    /**
     * checks if grid is fully filled
     * @return true if filled, else false
     */
    public boolean isFilled()
    {
        return this.getEmpty().size() == 0;
    }

    /**
     * attempts to insert a random tile at an empty location of an unknown grid,
     * checks if grid if fully filled first
     * @return power of inserted new Tile, or 0 if grid is full
     */
    public int newTile()
    {
        ArrayList<Loc> emptyLocs = this.getEmpty();
        if (emptyLocs.size() == 0) { return 0; }
        Loc iniLoc = emptyLocs.get((int) (Math.random() * emptyLocs.size()));
        return myGrid[iniLoc.stack][iniLoc.row][iniLoc.col].initiate();
    }

    /**
     * finds all the empty tiles in the array
     * @return array of all empty tiles
     */
    public ArrayList<Loc> getEmpty()
    {
        ArrayList<Loc> emptyLocs = new ArrayList<Loc>();
        for (Tile[][] stack : myGrid)
        {
            for (Tile[] row : stack)
            {
                for (Tile box : row)
                {
                    if (box.isEmpty()) { emptyLocs.add(box.getLoc()); }
                }
            }
        }
        System.out.println(" *** getEmpty called; " + emptyLocs);
        return emptyLocs;
    }

    /**
     * tries to shift tile from loc1 to loc2
     * if (r2, c2, s2) is an empty tile
     * @return
     */
    public boolean tryShift(Loc loc1, Loc loc2)
    {
        System.out.println("   * tryShift [" + loc1 + "], [" + loc2 + "]");
        if (!this.getTile(loc1).isEmpty() && this.getTile(loc2).isEmpty())
        {
            myGrid[loc2.stack][loc2.row][loc2.col] = this.getTile(loc1);
            myGrid[loc1.stack][loc1.row][loc1.col] = new Tile();
            return true;
        }
        else { return false; }
    }
    
    public int tryCombine(Loc loc1, Loc loc2)
    {
        System.out.println("   * tryCombine [" + loc1 + "], [" + loc2 + "]");
        Tile t1 = this.getTile(loc1);
        Tile t2 = this.getTile(loc2);
        // Debug code:
//        System.out.println("Loc1:" + t1.canMove() + loc1.stack + "," + loc1.row + "," + loc1.col + "," + t1.getPower() + " Loc2:" + loc2.stack + "," + loc2.row + "," + loc2.col + "," + t2.getPower());
        boolean newMove = t1.canMove() && t2.canMove();
        if (newMove && !t1.isEmpty() && t1.getPower() == t2.getPower())
        {
            myGrid[loc1.stack][loc1.row][loc1.col] = new Tile();
            return incrementTile(loc2);
        }
        else { return 0; }
    }

    public int combineStack(Loc start, Loc dir, int numTimes)
    {
        System.out.println("  ** combineStack [" + start + "], [" + dir + "], " + numTimes);
        Loc tile1;
        Loc tile2 = start;
        int score = 0;
        for (int n = 0; n < numTimes; n++)
        {
            tile1 = tile2;
            tile2 = tile1.add(dir);
            score += tryCombine(tile1,tile2);
        }
        return score;
    }

    public int combine(Loc dir)
    {
        System.out.println(" *** combine [" + dir + "]");
        dir.invert();
        int ret = 0;
        int startStack = 0;
        int maxStack = myGrid.length;
        int startRow = 0;
        int maxRow = myGrid[0].length;
        int startCol = 0;
        int maxCol = myGrid[0][0].length;

        if (dir.stack != 0)
        {
            if (dir.stack == 1) { maxStack = 1; }
            else { startStack = maxStack - 1; }
        }
        if (dir.row != 0)
        {
            if (dir.row == 1) { maxRow = 1; }
            else { startRow = maxRow - 1; }
        }
        if (dir.col != 0)
        {
            if (dir.col == 1) { maxCol = 1; }
            else { startCol = maxCol - 1; }
        }

        for (int stack = startStack; stack < maxStack; stack++)
        {
            for (int row = startRow; row < maxRow; row++)
            {
                for (int col = startCol; col < maxCol; col++)
                {
                    ret += combineStack(new Loc(stack, row, col), dir, 2);
                }
            }
        }
        dir.invert();
        movedAll();
        return ret;
    }
    
    public void movedAll()
    {
        for (Tile[][] stack : myGrid)
        {
            for (Tile[] row : stack)
            {
                for (Tile box : row)
                {
                    box.resetMoved();
                }
            }
        }
    }
    
    public void shiftStack(Loc start, Loc dir, int numTimes)
    {
        System.out.println("  ** shiftStack [" + start + "], [" + dir + "], " + numTimes);
        boolean cont = true;
        while (cont)
        {
            Loc tile1;
            Loc tile2 = start;
            for (int n = 0; n < numTimes; n++)
            {
                cont = false;
                tile1 = tile2;
                tile2 = tile1.add(dir);
                cont = cont || tryShift(tile1,tile2);
            }
        }
    }

    
    public void shift(Loc dir)
    {
        System.out.println(" *** shift [" + dir + "]");
        int startStack = 0;
        int maxStack = myGrid.length;
        int startRow = 0;
        int maxRow = myGrid[0].length;
        int startCol = 0;
        int maxCol = myGrid[0][0].length;

        if (dir.stack != 0)
        {
            if (dir.stack == 1) { maxStack = 1; }
            else { startStack = maxStack - 1; }
        }
        if (dir.row != 0)
        {
            if (dir.row == 1) { maxRow = 1; }
            else { startRow = maxRow - 1; }
        }
        if (dir.col != 0)
        {
            if (dir.col == 1) { maxCol = 1; }
            else { startCol = maxCol - 1; }
        }

        for (int stack = startStack; stack < maxStack; stack++)
        {
            for (int row = startRow; row < maxRow; row++)
            {
                for (int col = startCol; col < maxCol; col++)
                {
                    shiftStack(new Loc(stack, row, col), dir, MAX_SIZE - 1);
                }
            }
        }
    }

    public void resetLoc()
    {
        for (int stack = 0; stack < myGrid.length; stack++)
        {
            for (int row = 0; row < myGrid[0].length; row++)
            {
                for (int col = 0; col < myGrid[0][0].length; col++)
                {
                    myGrid[stack][row][col].setLoc(new Loc(stack, row, col));
                }
            }
        }
    }

    public int doMove(Loc dir)
    {
        System.out.println(" doMove called; dir: " + dir);
        this.shift(dir);
        score += this.combine(dir);
        this.shift(dir);
        this.newTile();
        this.resetLoc();
        return score;
    }


//    public void shiftStack(Loc start, Loc dir, int numTimes)
//    {
//        if (shiftStackOnce())
//    }

    public ArrayList<Tile> getTiles()
    {
        ArrayList<Tile> out = new ArrayList<Tile>();
        for (int stack = 0; stack < myGrid.length; stack++)
        {
            for (int row = 0; row < myGrid[0].length; row++)
            {
                for (int col = 0; col < myGrid[0][0].length; col++)
                {
                    out.add(myGrid[stack][row][col]);
                }
            }
        }
        return out;
    }

    public static void main(String[] args)
    {
        Grid gameGrid = new Grid();
        Scanner in = new Scanner(System.in);
        gameGrid.newTile();
        System.out.println(gameGrid);

        while (true)
        {
            // This working main method demonstrates all the methods in my milestone #2 plan, the debug code of each method is printed in the console.
             Loc dir;
             System.out.println("Please enter the direction you want to move (w,a,s,d,q,z) for (up,left,down,right,forward,backward) \n >");
             char direction = in.next().charAt(0);
             if (direction == 'w') { dir = Move.UP; }
             else if (direction == 'a') { dir = Move.LEFT; }
             else if (direction == 's') { dir = Move.DOWN; }
             else if (direction == 'd') { dir = Move.RIGHT; }
             else if (direction == 'q') { dir = Move.FORWARD; }
             else if (direction == 'z') { dir = Move.BACKWARD; }
             else { dir = new Loc(0,0,0); }

             gameGrid.doMove(dir);
             System.out.println(gameGrid);
        }
    }
}