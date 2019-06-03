/**
 Static Move class for 3D-2048 game
 Basic data-structure of directions, library of directions
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 12:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */

public class Move
{
	/** Loc obj for moving forward */
	public static final Loc FORWARD = new Loc(-1, 0, 0);
	/** Loc obj for moving backward */
	public static final Loc BACKWARD = new Loc(1, 0, 0);
	/** Loc obj for moving up */
	public static final Loc UP = new Loc(0, -1, 0);
	/** Loc obj for moving down */
	public static final Loc DOWN = new Loc(0, 1, 0);
	/** Loc obj for moving left */
	public static final Loc LEFT = new Loc(0, 0, -1);
	/** Loc obj for moving right */
	public static final Loc RIGHT = new Loc(0, 0, 1);
	
	/** Default empty constructor, Move is a datapack */
	public Move()
	{
	
	}
}
