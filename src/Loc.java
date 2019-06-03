/**
 * Location class for 3D-2048 game
 * Basic data-structure of locations/coordinates
 *
 * @author Jiahua Chen
 * @version Final-1.0 06.02.2019 2:00pm
 *
 * COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved.
 */

public class Loc
{
	/**
	 * Coords of Location
	 */
	public int stack;
	public int row;
	public int col;
	
	/**
	 * Constructor for new location
	 */
	public Loc()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Constructor for Loc at specific location
	 *
	 * @param stack stack of Loc
	 * @param row   row of Loc
	 * @param col   col of Loc
	 */
	public Loc(int stack, int row, int col)
	{
		this.stack = stack;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Adds a Loc to this Loc
	 *
	 * @param summand Loc to add to this loc
	 * @return the sum of two Locs
	 */
	public Loc add(Loc summand)
	{
		return new Loc(this.stack + summand.stack,
				this.row + summand.row, this.col + summand.col);
	}
	
	/**
	 * Inverts this Loc
	 */
	public void invert()
	{
		stack = - stack;
		row = - row;
		col = - col;
	}
	
	/**
	 * Prints this loc to string
	 *
	 * @return string representation of this Loc
	 */
	public String toString()
	{
		return "[" + this.stack + ", " + this.row + ", " + this.col + "]";
	}
}
