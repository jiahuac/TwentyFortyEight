import java.awt.*;
import javax.swing.*;

/**
 GameApp class for 3D-2048 game
 Runs the app for 2048 game
 @author Jiahua Chen
 @version Final-1.2 06.03.2019 12:00pm
 
 COPYRIGHT (C) 2019 Jiahua Chen. All Rights Reserved. */
public class GameApp
{
	
	/** width of the screen */
	public static final int WIDTH = 770;
	
	/** height of the screen */
	public static final int HEIGHT = 390;
	
	/** the window of the app */
	private JPanel myPanel;
	
	/** name of title screen */
	private static final String TITLE = "Title";
	
	/** name of game screen */
	private static final String GAME = "Game";
	
	/** name of instruction screen */
	private static final String INSTRUCTION = "Instruction";
	
	/** name of pause screen */
	private static final String PAUSE = "Pause";
	
	/** the game screen of the app */
	private GameScreen myGameScreen;
	
	/** the title screen of the app */
	private TitleScreen myTitleScreen;
	
	/** the instruction screen of the app */
	private InstructionScreen myInstructionScreen;
	
	/** the pause screen of the app */
	private PauseScreen myPauseScreen;
	
	/** Grid object */
	private Grid gameGrid;
	
	/**
	 Default constructor, creates a new GameApp
	 */
	public GameApp()
	{
		myTitleScreen = new TitleScreen(this);
		gameGrid = new Grid();
		myGameScreen = new GameScreen(gameGrid, this);
		myInstructionScreen = new InstructionScreen(this);
		myPauseScreen = new PauseScreen(this);
	}
	
	/**
	 Runs the GameApp, this is the main code that runs the game
	 */
	public void run()
	{
		JFrame myWindow;
		myWindow = new JFrame();
		myWindow.setResizable(false);
		
		myWindow.setSize(WIDTH, HEIGHT);
		myWindow.setTitle("2048^3");
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		myPanel = new JPanel(new CardLayout());
		
		myPanel.add(myGameScreen, GAME);
		myPanel.add(myTitleScreen, TITLE);
		myPanel.add(myInstructionScreen, INSTRUCTION);
		myPanel.add(myPauseScreen, PAUSE);
		CardLayout layout = (CardLayout) myPanel.getLayout();
		layout.show(myPanel, TITLE);
		
		myWindow.add(myPanel);
		
		myWindow.setVisible(true);
	}
	
	/**
	 Changes the screen to the GameScreen
	 */
	public void loadGameScreen()
	{
		CardLayout layout = (CardLayout) myPanel.getLayout();
		layout.show(myPanel, GAME);
		myGameScreen.requestFocusInWindow();
	}
	
	/**
	 Changes the screen to the InstructionScreen
	 */
	public void loadInstructionScreen()
	{
		CardLayout layout = (CardLayout) myPanel.getLayout();
		layout.show(myPanel, INSTRUCTION);
		myInstructionScreen.requestFocusInWindow();
	}
	
	/**
	 Changes the screen to the TitleScreen
	 */
	public void loadTitleScreen()
	{
		CardLayout layout = (CardLayout) myPanel.getLayout();
		layout.show(myPanel, TITLE);
		myTitleScreen.requestFocusInWindow();
		reset();
	}
	
	/**
	 Resets the game and clears the board
	 */
	public void reset()
	{
		myGameScreen.resetWin();
		gameGrid.rebuildGrid();
	}
	
	/**
	 Loads PauseScreen
	 */
	public void loadPauseScreen()
	{
		CardLayout layout = (CardLayout) myPanel.getLayout();
		layout.show(myPanel, PAUSE);
		myPauseScreen.requestFocusInWindow();
	}
	
	/**
	 Main Code, runs the game
	 @param args Java main args
	 */
	public static void main(String[] args)
	{
		GameApp main = new GameApp();
		main.run();
	}
}
