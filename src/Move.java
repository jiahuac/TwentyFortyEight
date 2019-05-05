/**
 * Static Move class for 3D-2048 game
 * Basic data-structure of directions
 * @author Jiahua Chen
 * @version 0.02 04.18.2019 Milestone 2
 */

public class Move {
    public static final Loc FORWARD = new Loc(-1,0,0);
    public static final Loc BACKWARD = new Loc(1,0,0);
    public static final Loc UP = new Loc(0,-1,0);
    public static final Loc DOWN = new Loc(0,1,0);
    public static final Loc LEFT = new Loc(0,0,-1);
    public static final Loc RIGHT = new Loc(0,0,1);
}
