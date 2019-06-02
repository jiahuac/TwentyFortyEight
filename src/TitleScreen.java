import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;

/**
 * Title Screen class for 3D-2048 game
 * Introductory Title Screen for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */
public class TitleScreen extends JPanel
{
    public static final int playX = 70;
    public static final int playY = 178;
    public static final int playW = 78;
    public static final int playH = 38;

    public static final int insX = 70;
    public static final int insY = 254;
    public static final int insW = 223;
    public static final int insH = 29;

    private GameApp myApp;
    
    private Rectangle2D.Double gameButton;

    private Rectangle2D.Double insButton;

    private static BufferedImage myBackground;
    private static BufferedImage myPlayButton;
    private static BufferedImage myPlayButtonHover;
    private static BufferedImage myInstructionsButton;
    private static BufferedImage myInstructionsButtonHover;

    private boolean playButtonHover;
    private boolean instructionsButtonHover;

    public TitleScreen(GameApp app)
    {
        myApp = app;
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(new MyButtonListener());
        this.addMouseMotionListener(new MyHoverListener());


        gameButton = new Rectangle2D.Double(playX, playY, playW, playH);

        insButton = new Rectangle2D.Double(insX, insY, insW, insH);

        try
        {
            InputStream is = getClass().getResourceAsStream("/screens" +
                    "/TitleScreenBackground.png");
            myBackground = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                "/PlayButton.png");
            myPlayButton = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                "/PlayHover.png");
            myPlayButtonHover = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                "/InstructionsButton.png");
            myInstructionsButton = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                "/InstructionsHover.png");
            myInstructionsButtonHover = ImageIO.read(is);
        }
        catch(IOException ioe)
        {
            System.out.println("InputStream ERROR");
        }
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
                null);

        if (playButtonHover)
        {
            g2.drawImage(myPlayButtonHover, playX - 2, playY, playW, playH,
                    null);
        }
        else
        {
            g2.drawImage(myPlayButton, playX, playY, playW - 4, playH, null);
        }

        if (instructionsButtonHover)
        {
            g2.drawImage(myInstructionsButtonHover, insX, insY, insW, insH,
                    null);
        }
        else
        {
            g2.drawImage(myInstructionsButton, insX, insY, insW, insH, null);
        }
    }


    private class MyHoverListener implements MouseMotionListener
    {
        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (mouseX > playX && mouseX < playX + playW && mouseY > playY && mouseY < playY + playH)
            {
                playButtonHover = true;
                instructionsButtonHover = false;
                repaint();
            }
            else if (mouseX > insX && mouseX < insX + insW && mouseY > insY && mouseY < insY + insH)
            {
                playButtonHover = false;
                instructionsButtonHover = true;
                repaint();
            }
            else
            {
                playButtonHover = false;
                instructionsButtonHover = false;
                repaint();
            }
        }
    }

    private class MyButtonListener implements MouseListener
    {
        public void mousePressed(MouseEvent e)
        {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (gameButton.contains(mouseX, mouseY))
            {
                myApp.loadGameScreen();
            }
            else if (insButton.contains(mouseX, mouseY))
            {
                myApp.loadInstructionScreen();
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
