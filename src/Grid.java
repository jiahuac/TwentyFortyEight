import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
// Scanner for temp input control
import java.util.Scanner;


/**
 * Grid class for 3D-2048 game
 * Basic data-structure of tile objects
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */

public class Grid
{
    /** given maximum size of the game grid (dimensions) */
    private int size;

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
        this(3);
    }

    /**
     * constructs a grid of dimensions/side length size
     * with all empty tiles
     * @param dim Dimension of the grid.
     * */
    public Grid(int dim)
    {
        size = dim;
        myGrid = new Tile[size][size][size];
        rebuildGrid();
    }

    public void rebuildGrid()
    {
        score = 0;
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
//        System.out.println(" *** getEmpty called; " + emptyLocs);
        return emptyLocs;
    }

    /**
     * tries to shift tile from loc1 to loc2
     * if (r2, c2, s2) is an empty tile
     * @return if it's shifted or not
     */
    public boolean tryShift(Loc loc1, Loc loc2)
    {
//        System.out.println("   * tryShift [" + loc1 + "], [" + loc2 + "]");
        if (!this.getTile(loc1).isEmpty() && this.getTile(loc2).isEmpty())
        {
            myGrid[loc2.stack][loc2.row][loc2.col] = myGrid[loc1.stack][loc1.row][loc1.col];
            myGrid[loc1.stack][loc1.row][loc1.col] = new Tile();
            return true;
        }
        else { return false; }
    }

    /**
     * tries to combine loc1 tile and loc2 tile
     * @param loc1 location of first tile
     * @param loc2 location of last tile
     * @return score if combined
     */
    public int tryCombine(Loc loc1, Loc loc2)
    {
//        System.out.println("   * tryCombine [" + loc1 + "], [" + loc2 + "]");
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

    /**
     * tries to combine a whole stack
     * @param start the starting coordinate of the stack
     * @param dir the direction that the stack is being combined in
     * @param numTimes the number of times to tryCombine
     * @return the score of the combination
     */
    public int combineStack(Loc start, Loc dir, int numTimes)
    {
//        System.out.println("  ** combineStack [" + start + "], [" + dir + "], " + numTimes);
        Loc tile1;
        Loc tile2 = start;
        int score = 0;
        for (int n = 0; n < numTimes; n++)
        {
            tile1 = tile2;
            tile2 = tile1.add(dir);
            int addScore = tryCombine(tile1, tile2);
            if (addScore != 0)
            { score += Math.pow(2, addScore); }
        }
        return score;
    }

    /**
     * Combining the whole grid in a certain direction
     * @param dir the direction to combine in
     * @return the score of the combination
     */
    public int combine(Loc dir)
    {
//        System.out.println(" *** combine [" + dir + "]");
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
                    ret += combineStack(new Loc(stack, row, col), dir, size - 1);
                }
            }
        }
        dir.invert();
        movedAll();
        return ret;
    }

    /**
     * resets the 'moved' status of every tile
     */
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

    /**
     * Attemps to shift a stack
     * @param start the starting coordinate of a stack
     * @param dir the direction to shift in
     * @param numTimes the number of times to tryShift
     */
    public void shiftStack(Loc start, Loc dir, int numTimes)
    {
//        System.out.println("  ** shiftStack [" + start + "], [" + dir + "], " + numTimes);
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

    /**
     * Tries to shift the entire grid
     * @param dir the direction to shift in
     */
    public void shift(Loc dir)
    {
//        System.out.println(" *** shift [" + dir + "]");
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
                    shiftStack(new Loc(stack, row, col), dir, size - 1);
                }
            }
        }
    }

    /**
     * Resets the location of all grid entries (Tiles)
     */
    public void resetLoc()
    {
        for (int stack = 0; stack < myGrid.length; stack++)
        {
            for (int row = 0; row < myGrid[0].length; row++)
            {
                for (int col = 0; col < myGrid[0][0].length; col++)
                {
                    myGrid[stack][row][col].setNewLoc(new Loc(stack, row, col));
                }
            }
        }
    }

    /**
     * Makes a move in a certain direction, which involves trying to shift, 
     * then trying to combine, and then shifting again. 
     * @param dir the direction to move in
     * @return the score added from combining
     */
    public int doMove(Loc dir)
    {
//        System.out.println(" doMove called; dir: " + dir);
        this.shift(dir);
        score += this.combine(dir);
        this.shift(dir);
        this.newTile();
//        this.resetLoc();
        return score;
    }

    /**
     * Gets a list of all tiles
     * @return ArrayList of all tiles
     */
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

    /**
     * Draws an empty tile in the back
     * @param g graphics object passed down from higher classes
     */
    public void drawBoard(Graphics2D g)
    {
        for (int stack = 0; stack < size; stack++)
        {
            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++)
                {
                    Loc loc = new Loc(stack, row, col);
                    int x = Tile.getXCoord(loc);
                    int y = Tile.getYCoord(loc);
                    g.drawImage(Tile.myTilesImage[0], x, y, 50, 50, null);
                }
            }
        }
    }


    public static void main(String[] args)
    {
//        Grid gameGrid = new Grid();
//        Scanner in = new Scanner(System.in);
//        gameGrid.newTile();
//        System.out.println(gameGrid);
//
//        while (true)
//        {
//            // This working main method demonstrates all the methods in my milestone #2 plan, the debug code of each method is printed in the console.
//             Loc dir;
//             System.out.println("Please enter the direction you want to move (w,a,s,d,q,z) for (up,left,down,right,forward,backward) \n >");
//             char direction = in.next().charAt(0);
//             if (direction == 'w') { dir = Move.UP; }
//             else if (direction == 'a') { dir = Move.LEFT; }
//             else if (direction == 's') { dir = Move.DOWN; }
//             else if (direction == 'd') { dir = Move.RIGHT; }
//             else if (direction == 'q') { dir = Move.FORWARD; }
//             else if (direction == 'z') { dir = Move.BACKWARD; }
//             else { dir = new Loc(0,0,0); }
//
//             gameGrid.doMove(dir);
//             System.out.println(gameGrid);
//        }
    }
}