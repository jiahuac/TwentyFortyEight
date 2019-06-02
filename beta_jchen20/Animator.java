import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Animation class for 3D-2048 game
 * Animation for 2048 game
 * @author Jiahua Chen
 * @version beta-0.1
 */

public class Animator {

    private static int animationTime;
    private static int animationStep;
    private Graphics2D myGraphics;
    private Tile myTile;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int x;
    private int y;
    private int xInt;
    private int yInt;
    private static final int interval = 100;

    public Animator(Graphics2D g, Tile tile, int xStart, int yStart, int xEnd, int yEnd)
    {
        this.myGraphics = g;
        this.myTile = tile;

        this.xStart = xStart;
        x = this.xStart;
        this.yStart = yStart;
        y = this.yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.xInt = (xEnd - xStart) / (1000 / interval);
        this.yInt = (yEnd - yStart) / (1000 / interval);
    }

    public void run()
    {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new UpdateTask(), 0, interval);
    }

    private class UpdateTask extends TimerTask
    {
        public void run()
        {
            myTile.drawTile(myGraphics, x, y);
            x += xInt;
            y += yInt;
        }
    }
}