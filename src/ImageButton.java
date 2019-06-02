import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;

/**
 * Image Button class for 3D-2048 game
 * Used for image buttons for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */
public class ImageButton {
    private int x, y, w, h;
    private Rectangle2D.Double button;
    private BufferedImage buttonImage;
    private BufferedImage buttonImageHover;

    public ImageButton(int x, int y, int w, int h, String image, String hover)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        try
        {
            InputStream is = getClass().getResourceAsStream(image);
            buttonImage = ImageIO.read(is);
            is = getClass().getResourceAsStream(hover);
            buttonImageHover = ImageIO.read(is);
        }
        catch(IOException ioe)
        {
            System.out.println("InputStream ERROR");
        }
    }

    public void drawButton(Graphics2D g)
    {
        g.drawImage(buttonImage, x, y, w, h, null);
    }

    public void drawHover(Graphics2D g)
    {
        g.drawImage(buttonImageHover, x, y, w, h, null);
    }

    public void draw(Graphics2D g, boolean hover)
    {
        if (hover)
        {
            drawHover(g);
        }
        else
        {
            drawButton(g);
        }
    }

    public boolean doesContain(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY();

        return (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h);
    }
}
