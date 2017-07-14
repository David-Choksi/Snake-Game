package PacMan.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



/**
 * A view for the pacMan puzzle game.
 *
 */
@SuppressWarnings("serial")
public class PMView extends JFrame {

	private static final int GAME_HEIGHT = 870;
	private static final int GAME_WIDTH = 780;
	private static final ArrayList<RowCol> FOOD = new ArrayList<RowCol>();


	private PMFoodBoard board = new PMFoodBoard();
	public static final String GAME_NAME = "PACMAN";
	public static final String NEW_GAME = "NEW GAME";
	public static final String MENU_NAME = "GAME";
	public static final String HIGH_SCORE = "HIGH SCORES...";
	public static final String MENU_HELP = "HELP";
	public static final String EXIT = "EXIT";
	private KeyListener keyListener;
	
	public PMView(ActionListener listener)  {
		
		super(GAME_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		this.makeMenu(listener);
		this.makeGameBoard(listener);
		
		this.keyListener = (KeyListener) listener;
		this.addKeyListener(this.keyListener);
		this.pack();
		this.setVisible(true);
	}

	
	private void makeMenu(ActionListener listener) {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu menu = new JMenu();
		menu.setText(PMView.MENU_NAME);
		JMenu menu2 = new JMenu();
		menu2.setText(PMView.MENU_HELP);
		JMenuItem helps = new JMenuItem();
		helps.setText(MENU_HELP);
		helps.setActionCommand(MENU_HELP);
		helps.addActionListener(listener);
		
		JMenuItem one = new JMenuItem();
		one.setText(PMView.NEW_GAME);
		one.setActionCommand(PMView.NEW_GAME);
		one.addActionListener(listener);
		JMenuItem two = new JMenuItem();
		two.setText(PMView.HIGH_SCORE);
		two.setActionCommand(PMView.HIGH_SCORE);
		two.addActionListener(listener);

		JMenuItem exit = new JMenuItem();
		exit.setText(PMView.EXIT);
		exit.setActionCommand(PMView.EXIT);
		exit.addActionListener(listener);
		menuBar.add(menu);
		menuBar.add(menu2);
		menu2.add(helps);
		menu.add(one);
		menu.add(two);
		menu.add(exit);
	}
	
	private void makeGameBoard(ActionListener listener){
		JPanel gameBoard = new JPanel();
		add(BorderLayout.CENTER, gameBoard);
		JPanel scoreBoard = new JPanel();
		scoreBoard.setAlignmentX(CENTER_ALIGNMENT);
		scoreBoard.setAlignmentY(CENTER_ALIGNMENT);
		add(BorderLayout.EAST, scoreBoard);
		JLabel score = new JLabel("Score = 0");
		scoreBoard.add(score);
		
	}
	
}