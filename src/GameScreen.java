import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;

/**
 * GameScreen class for 3D-2048 game
 * Graphics and drawing for 2048 game
 * @author Jiahua Chen
 * @version beta-1.0 05.23.2019
 */

public class GameScreen extends JPanel {

    /**
     * GameApp and Grid objects
     */
    private GameApp myApp;
    private Grid myGrid;

    /**
     * Pause Button
     */
    private Rectangle2D.Double myPauseButton;

    private ImageButton remindButton;
    private boolean remindHover;

    private ImageButton pauseButton;
    private boolean pauseHover;

    private ImageButton scoreButton;
    private boolean scoreHover;
    private boolean hoverTooltip = true;

    private BufferedImage winBackground;
    private boolean winScreen = true;

    private ImageButton winContinueButton;
    private boolean winContinueHover;

    private ImageButton winMenuButton;
    private boolean winMenuHover;

    private BufferedImage loseBackground;

    private ImageButton loseMenuButton;
    private boolean loseMenuHover;

    private static BufferedImage myBackground;
    private static BufferedImage myTooltip;
    private static BufferedImage myScoreTooltip;

    private static final Color tileGrey = new Color(119, 110, 101);


    /**
     * GameScreen Constructor
     *
     * @param grid Grid of the game
     * @param app  GameApp of the Game (for changing screens)
     */
    public GameScreen(Grid grid, GameApp app) {
        myGrid = grid;
        myApp = app;
        this.addKeyListener(new GameKeyHandler(myGrid, this));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(new MyButtonListener());
        this.addMouseMotionListener(new MyHoverListener());

        remindButton = new ImageButton(535, 316, 201, 12,
                "/screens/RemindButton.png", "/screens/RemindHover.png");
        pauseButton = new ImageButton(662, 24, 74, 38, "/screens/GamePauseButton" +
                ".png", "/screens/GamePauseHover.png");
        scoreButton = new ImageButton(455, 24, 200, 38, "/screens/ScoreButton" +
                ".png", "/screens/ScoreHover.png");

        winContinueButton = new ImageButton(252, 193, 138, 38, "/screens" +
                "/WinContinueButton.png", "/screens/WinContinueHover.png");
        winMenuButton = new ImageButton(413, 193, 104, 38, "/screens" +
                "/MenuButton.png", "/screens/MenuHover.png");

        loseMenuButton = new ImageButton(335, 193, 104, 38, "/screens" +
                "/MenuButton.png", "/screens/MenuHover.png");

        try {
            InputStream is = getClass().getResourceAsStream("/screens" +
                    "/GameScreen.png");
            myBackground = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/ControlTooltip.png");
            myTooltip = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/ScoreTooltip.png");
            myScoreTooltip = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/WinPrompt.png");
            winBackground = ImageIO.read(is);
            is = getClass().getResourceAsStream("/screens" +
                    "/LosePrompt.png");
            loseBackground = ImageIO.read(is);
        } catch (IOException ioe) {
            System.out.println("InputStream ERROR");
        }

        FieldUpdater up = new FieldUpdater();
        up.start();
    }

    /**
     * Paints the screen
     *
     * @param g Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(myBackground, 0, 0, GameApp.WIDTH, GameApp.HEIGHT - 20,
                null);

        remindButton.draw(g2, remindHover);
        pauseButton.draw(g2, pauseHover);

        scoreButton.draw(g2, scoreHover);

        myGrid.drawBoard(g2);
        for (Tile t : myGrid.getTiles()) {
            t.drawMe(g2);
        }

        if (remindHover) {
            g2.drawImage(myTooltip, 202, 69, 364,
                    225,
                    null);
        }

        g2.setFont(new Font("PT Sans Caption", Font.BOLD, 17));
        g2.setColor(tileGrey);
        if (scoreHover)
        {
            g2.setColor(Color.BLACK);
            g2.drawImage(myScoreTooltip, 227, 12, 222,
                    67,
                    null);
        }
        g2.drawString(Integer.toString(myGrid.getScore()), 534, 49);

        if (hoverTooltip)
        {
            g2.setFont(new Font("PT Sans Caption", Font.PLAIN, 8));
            g2.drawString("Hover over me to see an explanation of scores", 463,
                    74);
        }

        if (winScreen && myGrid.hasWon())
        {
            g2.drawImage(winBackground, -1, 0, GameApp.WIDTH,
                    GameApp.HEIGHT - 20,
                    null);
            winContinueButton.draw(g2, winContinueHover);
            winMenuButton.draw(g2, winMenuHover);
        }

        if (myGrid.gridLocked())
        {
            g2.drawImage(loseBackground, -1, 0, GameApp.WIDTH,
                    GameApp.HEIGHT - 20,
                    null);
            loseMenuButton.draw(g2, loseMenuHover);
        }
    }

    public void resetWin()
    {
        winScreen = true;
    }

    private class FieldUpdater extends Thread {
        public void run() {
            while (true) {
                repaint();
                try {
                    sleep(1);
                } catch (InterruptedException ie) {

                }
            }
        }
    }


    private class MyHoverListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            if (winScreen && myGrid.hasWon())
            {
                resetHover();
                if (winContinueButton.doesContain(e)) {
                    winContinueHover = true;
                    winMenuHover = false;
                } else if (winMenuButton.doesContain(e)) {
                    winContinueHover = false;
                    winMenuHover = true;
                } else {
                    winContinueHover = false;
                    winMenuHover = false;
                }
            }
            else if (myGrid.gridLocked())
            {
                if (loseMenuButton.doesContain(e))
                {
                    loseMenuHover = true;
                }
                else
                {
                    loseMenuHover = false;
                }
            }
            else {
                if (remindButton.doesContain(e)) {
                    remindHover = true;
                    pauseHover = false;
                    scoreHover = false;
                } else if (pauseButton.doesContain(e)) {
                    remindHover = false;
                    pauseHover = true;
                    scoreHover = false;
                } else if (scoreButton.doesContain(e)) {
                    hoverTooltip = false;
                    remindHover = false;
                    pauseHover = false;
                    scoreHover = true;
                } else {
                    resetHover();
                }
            }
        }
    }

    public void resetHover()
    {
        remindHover = false;
        pauseHover = false;
        scoreHover = false;
    }

    private class MyButtonListener implements MouseListener
    {
        public void mousePressed(MouseEvent e)
        {
            if (winScreen && myGrid.hasWon())
            {
                if (winContinueButton.doesContain(e))
                {
                    winScreen = false;
                }
                else if (winMenuButton.doesContain(e))
                {
                    winContinueHover = false;
                    winMenuHover = false;
                    myApp.loadTitleScreen();
                }
            }
            else if (myGrid.gridLocked())
            {
                if (loseMenuButton.doesContain(e))
                {
                    loseMenuHover = false;
                    myApp.loadTitleScreen();
                }
            }
            else
            {
                if (pauseButton.doesContain(e))
                {
                    resetHover();
                    myApp.loadPauseScreen();
                }
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
}