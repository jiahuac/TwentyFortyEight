/**
 * Static Move class for 3D-2048 game
 * Basic data-structure of directions, library of directions
 * @author Jiahua Chen
 * @version Final-1.0 06.02.2019 2:00pm
 *
 * COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved.
 */

public class Move {
    /**
     * Various move directions
     */
    public static final Loc FORWARD = new Loc(-1,0,0);
    public static final Loc BACKWARD = new Loc(1,0,0);
    public static final Loc UP = new Loc(0,-1,0);
    public static final Loc DOWN = new Loc(0,1,0);
    public static final Loc LEFT = new Loc(0,0,-1);
    public static final Loc RIGHT = new Loc(0,0,1);
}
