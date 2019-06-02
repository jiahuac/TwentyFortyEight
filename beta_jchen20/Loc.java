/**
 * Location class for 3D-2048 game
 * Basic data-structure of locations/coordinates
 * @author Jiahua Chen
 * @version 0.02 04.18.2019 Milestone 2
 */

public class Loc {
    public int stack;
    public int row;
    public int col;

    public Loc()
    {
        this(0,0,0);
    }

    public Loc(int stack, int row, int col)
    {
        this.stack = stack;
        this.row = row;
        this.col = col;
    }

    public Loc add(Loc summand)
    {
        return new Loc(this.stack + summand.stack, this.row + summand.row, this.col + summand.col);
    }

    public void invert()
    {
        stack = -stack;
        row = -row;
        col = -col;
    }
    
    public String toString()
    {
        return "[" + this.stack + ", " + this.row + ", " + this.col + "]";
    }
}
