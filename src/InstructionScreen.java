import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;

/**
 * Instructions Screen class for 3D-2048 game
 * Instructions Screen for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */
public class InstructionScreen extends JPanel
{
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    private static BufferedImage myBackground;
    private static BufferedImage myPlayButton;
    private static BufferedImage myPlayButtonHover;
    private static BufferedImage myMenuButton;
    private static BufferedImage myMenuButtonHover;

    private GameApp myApp;

    private Rectangle2D.Double myGameButton;

    private Grid myGrid;

    public InstructionScreen(GameApp app)
    {
        myApp = app;
        myGrid = new Grid(2);
        this.addMouseListener(new MyButtonListener());
        FieldUpdater up = new FieldUpdater();
        this.addKeyListener(new GameKeyHandler(myGrid, this));
        this.setFocusable(true);
        this.requestFocusInWindow();

        try
        {
            InputStream is = getClass().getResourceAsStream("/screens" +
                    "/insScreen.png");
            myBackground = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/PlayButton.png");
            myPlayButton = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/PlayHover.png");
            myPlayButtonHover = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/InstructionsButton.png");
            myMenuButton = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/InstructionsHover.png");
            myMenuButtonHover = ImageIO.read(is);
        }
        catch(IOException ioe)
        {
            System.out.println("InputStream ERROR");
        }

        up.start();
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
                null);

//        int buttonX = (GameApp.WIDTH / 2) - (BUTTON_WIDTH / 2);
//        int buttonY = (GameApp.HEIGHT / 2) - (BUTTON_HEIGHT / 2) + 100;
//
//        myGameButton = new Rectangle2D.Double(buttonX, buttonY,
//                BUTTON_WIDTH, BUTTON_HEIGHT);
//        g2.draw(myGameButton);
//        g2.setFont(new Font("Arial", Font.BOLD, 18));
//        g2.drawString("Play the real game!", buttonX + 20, buttonY + 30);
        myGrid.drawBoard(g2);
        for (Tile t : myGrid.getTiles())
        {
            t.drawMe(g2);
        }
    }

    private class MyButtonListener implements MouseListener
    {
        public void mousePressed(MouseEvent e)
        {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (myGameButton.contains(mouseX, mouseY))
            {
                myApp.loadGameScreen();
            }
        }

        public void mouseReleased(MouseEvent e)
        {

        }

        public void mouseClicked(MouseEvent e)
        {

        }

        public void mouseEntered(MouseEvent e)
        {

        }

        public void mouseExited(MouseEvent e)
        {

        }
    }

    private class FieldUpdater extends Thread {
        public void run()
        {
            while (true)
            {
                repaint();
                try
                {
                    sleep(1);
                }
                catch (InterruptedException ie)
                {

                }
            }
        }
    }
}
