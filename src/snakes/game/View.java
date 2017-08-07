package snakes.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A view for the snake puzzle game. This class handles showing the
 * snake game to the end user.
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Kristiana
 *         Papajani
 */
@SuppressWarnings("serial")
public class View extends JFrame {
	
	// This takes care of the constants so there are no magic numbers or constants in code.
	// **************************************************************************
	public static final String GAME_NAME = "SNAKE GAME";
	public static final String NEW_GAME = "NEW GAME";
	public static final String NEW_2_PLAYER_GAME = "MULTIPLAYER GAME";
	public static final String MENU_NAME = "GAME";
	public static final String MENU_HELP = "HELP";
	public static final String HIGH_SCORE = "HIGH SCORES...";
	public static final String EXIT = "EXIT";
	public static final String UP_ICON = "UPHead.gif";
	public static final int GAME_WIDTH = 40;
	public static final int GAME_HEIGHT = 25;
	// **************************************************************************
	
	//This takes care of the grid of labels which represent the grid that the snake will traverse in game.
	public JLabel[][] labels;
	
	//This represents the locations of the food locations and the poison locations, respectively.
	public static final ArrayList<RowCol> foodLocation = new ArrayList<RowCol>();
	public static final ArrayList<RowCol> dieLocation = new ArrayList<RowCol>();

	/**
	 * This constructor handles the creation and display of the game board to the end user. 
	 * The window is first displayed, and then the labels, listeners, and layouts are 
	 * added and packed so that all of its contents are set at a preferred, optimal size. 
	 * @param listener an ActionListener designed to listen for the user's button clicks.
	 * @param keyListener a KeyListener designed to listen for the user's keyboard inputs.
	 */
	public View(ActionListener listener, KeyListener keyListener) {
		super(GAME_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.makeMenu(listener);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/Snake_BG.png")))));
		} catch (IOException e) {
		}
		;
		this.getContentPane().setLayout(new GridLayout(GAME_HEIGHT, GAME_WIDTH));
		this.addKeyListener(keyListener);

		this.makeLabels(listener);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		this.setVisible(true);
		this.pack();

	}
	
	/**
	 * The method shows a dialog box with the option to choose if you want music running while playing the game. The dialog box has the
	 * options "Yes", "No", and an "X" symbol in the corner. 
	 * @postcondition will either play the music, or will stay silent. 
	 * @return an int value corresponding to the option selected or CLOSED_OPTION if the "X" is clicked.
	 */
	
	public int promptForMusic() {
		return JOptionPane.showOptionDialog(this, "Do you want to play some James Brown?", null, 0, 0, null, null,
				"Music");
	}
	
	/**
	 * The method shows a dialog box when the Multiplayer Game option is clicked in the Game menu. The dialog box allows the user to 
	 * enter a 2nd player's name. The dialog box has the options "OK", "Cancel", and an "X" symbol in the corner. 
	 * @postcondition will display the player name entered, or null if nothing is entered. 
	 * @return an int value corresponding to the option selected or CLOSED_OPTION if the "X" is clicked.
	 */
	
	public int promptForTwoPlayer() {
		return JOptionPane.showOptionDialog(this, "Do you want to play 2 Player?", null, 0, 0, null, null,
				"Number of players");
	}
	
	/**
	 * The method shows a dialog box when the Game is launched. The dialog box allows the user to 
	 * enter the 1st/main player's name. The dialog box has the options "OK", "Cancel", and an "X" symbol in the corner. 
	 * If an invalid string name is entered, the user is alerted and then taken back to the original dialog box to enter a name.  
	 * @precondition The inputed String name from the user must not contain a "," as that character is used in the High Score display format. 
	 * @postcondition will display the player name entered, or null if nothing is entered. 
	 * @param name - a string inputed by the user that cannot contain a "," character.
	 * @return the inputed string from the user.
	 */
	
	public String promptForName(String name) {
		String newName = "";
		newName = JOptionPane.showInputDialog(null, "What is your name " + name + "?");
		if (!(newName == null)) {
			while (newName.contains(",")) {
				JOptionPane.showMessageDialog(null, "No special Characters please", "ReEnter you name.",
						JOptionPane.PLAIN_MESSAGE);
				newName = JOptionPane.showInputDialog(null, "What is your name " + name + "?");
			}
		}
		return newName;
	}
	
	/**
	 * This method clears the View and resets the game. The snake position is reset and all shown food are cleared. 
	 * The various art, background, and user name are all reset. 
	 */
	
	public void clear() {

		for (int i = 0; i < GAME_HEIGHT; i++) {
			for (int j = 0; j < GAME_WIDTH; j++) {
				labels[i][j].setText("");
				labels[i][j].setOpaque(false);
				labels[i][j].setBackground(Color.BLUE);
				labels[i][j].setIcon(null);
			}
		}
		foodLocation.clear();
		dieLocation.clear();

	}

	/**
	 * Make the menu bar for the view. The menu bar has 2 menu options named GAME and HELP. The Help menu has a submenu option called
	 * HELP that pops up a dialog box with some useful instructions (press F2 to start new game, Spacebar to pause the game). The Game
	 * menu has 4 submenu options; the first named New Game is to start a new game, the second named Multiplayer Game is to start a 
	 * Multiplayer game, the 3rd is High Scores and shows the current high scores, and finally the Exit options terminates the game.
	 * 
	 * @param listener - a listener object that allows the controller to listen for the menu events (like clicks by user on the menu options)
	 */
	private void makeMenu(ActionListener listener) {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menu = new JMenu();
		menu.setText(View.MENU_NAME);
		JMenu menu2 = new JMenu();
		menu2.setText(View.MENU_HELP);
		JMenuItem helps = new JMenuItem();
		helps.setText(MENU_HELP);
		helps.setActionCommand(MENU_HELP);
		helps.addActionListener(listener);

		JMenuItem newGame = new JMenuItem();
		newGame.setText(View.NEW_GAME);
		newGame.setActionCommand(View.NEW_GAME);
		newGame.addActionListener(listener);
		JMenuItem twoPlayerGame = new JMenuItem();
		twoPlayerGame.setText(View.NEW_2_PLAYER_GAME);
		twoPlayerGame.setActionCommand(View.NEW_2_PLAYER_GAME);
		twoPlayerGame.addActionListener(listener);
		JMenuItem highScores = new JMenuItem();
		highScores.setText(View.HIGH_SCORE);
		highScores.setActionCommand(View.HIGH_SCORE);
		highScores.addActionListener(listener);

		JMenuItem exit = new JMenuItem();
		exit.setText(View.EXIT);
		exit.setActionCommand(View.EXIT);
		exit.addActionListener(listener);
		menuBar.add(menu);
		menuBar.add(menu2);
		menu2.add(helps);
		menu.add(newGame);
		menu.add(twoPlayerGame);
		menu.add(highScores);
		menu.add(exit);
	}

	/**
	 * This method creates the buttons for the view. This method should create (this.rows *this.cols) buttons. 
	 * See the Lab 7 document for the button labels. The action command should be equal to the text of the button label.
	 * 
	 *@param listener - a listener object that allows the controller to listen for the menu events (like clicks by user on the menu options)
	 */
	private void makeLabels(ActionListener listener) {
		this.labels = new JLabel[GAME_HEIGHT][GAME_WIDTH];
		for (int i = 0; i < GAME_HEIGHT; i++) {
			for (int j = 0; j < GAME_WIDTH; j++) {
				JLabel b = new JLabel(" ");
				b.setForeground(Color.BLACK);
				b.setBackground(Color.BLACK);
				b.setPreferredSize(new Dimension(20, 20));// set the preferred
															// size of b
				b.setVisible(true);
				labels[i][j] = b;

				add(b);
			}
		}
	}

	/**
	 * This method creates a random food across the map that the snake traverses. If a food unit is eaten, it will cause the snake's 
	 * body to grow. The food units have their own custom icon. The food unit are added randomly across the map grid, and only 1 is shown
	 * to the user, and a new one is generated at a random location when the previous one is eaten.   
	 * 
	 * @precondition the max int value is never negative. 
	 * @param max - a value determined by from the Model, and starts at 1. After 6 food units are eaten, a poison unit is created. After 10
	 * food units are eaten, the level is advanced. 
	 * @param latitude - a valid latitude value, in the given range.
	 */
	
	public void randomFood(int max) {
		boolean done = false;
		for (int k = 0; k < max; k++) {
			while (!done) {
				Random rdm = new Random();
				int i = rdm.nextInt(GAME_HEIGHT);
				int j = rdm.nextInt(GAME_WIDTH);
				if (labels[i][j].getText().equals("")) {
					labels[i][j].setText("F");
					labels[i][j].setForeground(Color.GREEN);
					labels[i][j].setBackground(Color.GREEN);
					foodLocation.add(new RowCol(i, j));
					File file = new File("images/rat.png");
					ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
					labels[i][j].setIcon(img);
					done = true;
				}
			}
		}
	}

	/**
	 * This method creates random poison/toxic food units across the map that the snake traverses. If a poisoned/toxic food unit is eaten, 
	 * it will cause the game to end, and your score is shown. The poisoned/toxic food units have their own custom icon. 
	 * The poisoned units are added randomly across the map grid, and gradually increase in number as more food units are eaten by the snake.   
	 * 
	 * @precondition the max int value is never negative. 
	 * @param max - a value determined by from the Model, and starts at 5. After 6 food units are eaten, a poison unit is created. Once a 
	 * poison unit is eaten, the game ends. 
	 */
	
	public void randomDie(int max) {
		for (int k = 0; k < max; k++) {
			Random rdm = new Random();
			int i = rdm.nextInt(GAME_HEIGHT);
			int j = rdm.nextInt(GAME_WIDTH);
			labels[i][j].setText("D");
			labels[i][j].setForeground(Color.BLACK);
			labels[i][j].setBackground(Color.BLACK);
			dieLocation.add(new RowCol(i, j));
			File file = new File("images/die.png");
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			labels[i][j].setIcon(img);
		}
	}

}
