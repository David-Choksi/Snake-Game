package snakes.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A view for the snake puzzle game.
 *
 */
@SuppressWarnings("serial")
public class View extends JFrame {
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
	public JLabel[][] labels;
	public static final ArrayList<RowCol> foodLocation = new ArrayList<RowCol>();
	public static final ArrayList<RowCol> dieLocation = new ArrayList<RowCol>();

	public View(ActionListener listener, KeyListener keyListener) {
		super(GAME_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.makeMenu(listener);
		this.getContentPane().setLayout(new GridLayout(GAME_HEIGHT, GAME_WIDTH));
		this.addKeyListener(keyListener);
		this.makeLabels(listener);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		this.setVisible(true);
		this.pack();

	}

	public int promptForMusic() {
		return JOptionPane.showOptionDialog(this, "Do you want to play some James Brown?", null, 0, 0, null, null,
				"Music");

	}

	public int promptForTwoPlayer() {
		return JOptionPane.showOptionDialog(this, "Do you want to play 2 Player?", null, 0, 0, null, null,
				"Number of players");

	}

	public String promptForName(String name) {
		String newName = JOptionPane.showInputDialog(null, "What is your name " + name + "?");
		while (newName.contains(",")) {
			JOptionPane.showMessageDialog(null, "No special Characters please", "ReEnter you name.",
					JOptionPane.PLAIN_MESSAGE);
			newName = JOptionPane.showInputDialog(null, "What is your name " + name + "?");
		}
		return newName;

	}

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
	 * Make the menu bar for the view. The menu bar has one menu named "Game",
	 * and that menu has two menu items named "New" and "Exit".
	 * 
	 * @param listener
	 *            the controller to listen for the menu events
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
	 * Creates the buttons for the view. This method should create (this.rows *
	 * this.cols) buttons. See the Lab 7 document for the button labels. The
	 * action command should be equal to the text of the button label.
	 * 
	 * @param listener
	 *            the controller that listens for button press events
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
