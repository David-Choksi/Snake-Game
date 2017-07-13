package PacMan.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;



/**
 * A view for the pacMan puzzle game.
 *
 */
@SuppressWarnings("serial")
public class PMView extends JFrame implements KeyListener, ActionListener{
	private Timer timer;
	private int rows;
	private int cols;
	public JLabel[][] labels;
	private static int food;
	private static int die;
	private static PacMan pacMan = new PacMan();
	public static void setFood(int food) {
		PMView.food = food;
	}

	public static void setDie(int die) {
		PMView.die = die;
	}

	private PMGameBoard board = new PMGameBoard();
	/**
	 * The action command for a new game.
	 */
	public static final String GAME_NAME = "PACMAN";
	/**
	 * The action command for a new game.
	 */
	public static final String NEW_GAME = "NEW GAME";

	/**
	 * The action command for a new game.
	 */
	public static final String MENU_NAME = "GAME";
	/**
	 * The action command for a new game.
	 */
	public static final String MENU_HELP = "HELP";
	

	/**
	 * The action command for a new advanced game.
	 */
	public static final String NEW_ADVANCED_GAME = "NEW ADVANCED GAME";
	/**
	 * The action command to exit the application. 
	 */
	public static final String EXIT = "EXIT";

	/**
	 * Initializes the view for a sliding puzzle game played
	 * on a grid with the given number of rows and columns.
	 * 
	 * @param rows the number of rows in the sliding puzzle
	 * @param cols the number of columns in the sliding puzzle
	 * @param listener the action listener (controller)
	 * @throws InterruptedException 
	 */
	public PMView(int rows, int cols, ActionListener listener)  {
		super(GAME_NAME);
		this.rows = rows;
		this.cols = cols;
		this.timer = new Timer(100 , this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.makeMenu(listener);
		this.getContentPane().setLayout(new GridLayout(this.rows, this.cols));
		this.addKeyListener(this);
		this.makeLabels(listener);
		this.pack();
		this.setVisible(true);
	}
	
	public void setTimer(int time){
		this.timer = new Timer(time , this);
	}
	
	public void run() {
		timer.start();
	}

	public void clear(){
		for (int i = 0 ; i<this.rows; i++){
			for (int j = 0; j<this.cols; j++){
				labels[i][j].setOpaque(false);
				labels[i][j].setBackground(Color.BLUE);
				labels[i][j].setText("" +board.getFood(i, j));
				
			}
		}
		pacMan = new PacMan();
		showPM();
		
	}
	/**
	 * Make the menu bar for the view. The menu bar has one menu
	 * named "Game", and that menu has two menu items named
	 * "New" and "Exit".
	 * 
	 * @param listener the controller to listen for the menu events
	 */
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
		two.setText(PMView.NEW_ADVANCED_GAME);
		two.setActionCommand(PMView.NEW_ADVANCED_GAME);
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
	
	/**
	 * Creates the buttons for the view. This method should
	 * create (this.rows * this.cols) buttons. See the Lab 7
	 * document for the button labels. The action command
	 * should be equal to the text of the button label.
	 *  
	 * @param listener the controller that listens for button press
	 * events
	 */
	private void makeLabels(ActionListener listener) {
		this.labels = new JLabel[rows][cols];
		for (int i = 0 ; i<this.rows; i++){
			for (int j = 0; j<this.cols; j++){
				JLabel b = new JLabel("");
		
				b.setForeground(Color.BLACK);
				if (!(board.getFood(i+1, j+1)==0)){
					String la = "" + board.getFood(i+1, j+1);
					b.setText(la);
				}
				b.setBackground(Color.BLACK);
				b.setPreferredSize(new Dimension(20, 20));// set the preferred size of b
				labels[i][j] = b;
				add(b);
			}
		}
		showPM();
		
		
	}
	
	public void showPMGone(){

		labels[pacMan.removing().row()][pacMan.removing().col()].setText("");
		labels[pacMan.removing().row()][pacMan.removing().col()].setOpaque(false);
		labels[pacMan.removing().row()][pacMan.removing().col()].setBackground(Color.BLUE);
		labels[pacMan.removing().row()][pacMan.removing().col()].setForeground(Color.BLUE);
	}
	
	public void showPM(){
		
		labels[pacMan.getRow()][pacMan.getCol()].setText("S");
		labels[pacMan.getRow()][pacMan.getCol()].setOpaque(true);		

		labels[pacMan.getRow()][pacMan.getCol()].setBackground(Color.RED);
		labels[pacMan.getRow()][pacMan.getCol()].setForeground(Color.RED);
		}
	
	public static int getFood() {
		return food;
	}

	public static int getDie() {
		return die;
	}

	public void randomFood(int max){
		boolean done = false;
		for (int k=0; k<max ;k++){
			while (!done){
				Random rdm = new Random();
				int i = rdm.nextInt(this.rows);
				int j = rdm.nextInt(this.cols);
				if (labels[i][j].getText().equals("")){
					labels[i][j].setText("F");
					labels[i][j].setForeground(Color.GREEN);
					labels[i][j].setOpaque(true);
					labels[i][j].setBackground(Color.GREEN);
					done = true;
				}
			}
		}
	}
	
	public void randomDie(int max){
		for (int k=0; k<max ;k++){
			Random rdm = new Random();
			int i = rdm.nextInt(this.rows);
			int j = rdm.nextInt(this.cols);
			labels[i][j].setText("D");
			labels[i][j].setForeground(Color.BLACK);
			labels[i][j].setOpaque(true);
			labels[i][j].setBackground(Color.BLACK);
		}
	}

	/**
	 * Sets the button at the given location to the empty tile.
	 * The button text for the empty tile is the empty string.
	 * Also sets the action command string for the button to
	 * the empty string.
	 * 
	 * @param loc the row and column of the button to set as
	 * the empty tile
	 */
	public void setEmpty(RowCol loc) {
			labels[loc.row()][loc.col()].setText("");
	}
	
	/**
	 * Sets the text of the button at the given location to the
	 * given text. Also sets the action command string for the
	 * button to the given text.
	 * 
	 * @param loc the row and column of the button to set the text of
	 * @param label the text for the button label
	 */
	public void setLocation(RowCol loc, String label) {
		labels[loc.row()][loc.col()].setText(label);
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		if (e.getKeyCode() == 37){
			if(!(pacMan.getDirection().equals("LEFT") ||pacMan.getDirection().equals("RIGHT"))){
//				System.out.println("LEFT");
//				System.out.println(e);
				pacMan.changeDirection("LEFT");
//				showSnakeGone();
				checkFinish();
//				pacMan.move();
//				showSnake();
			}
		}
		else if (e.getKeyCode() == 38){
			if(!(pacMan.getDirection().equals("UP") ||pacMan.getDirection().equals("DOWN"))){
				
//				System.out.println("UP");
//				System.out.println(e);
				pacMan.changeDirection("UP");
//				showSnakeGone();
				checkFinish();
//				pacMan.move();
//				showSnake();;
			}
		}
		else if (e.getKeyCode() == 39){
			if(!(pacMan.getDirection().equals("LEFT") ||pacMan.getDirection().equals("RIGHT"))){
				
//				System.out.println("RIGHT");
//				System.out.println(e);
				pacMan.changeDirection("RIGHT");
//				showSnakeGone();
				checkFinish();
//				pacMan.move();
//				showSnake();

			}
		}
		else if (e.getKeyCode() == 40){
			if(!(pacMan.getDirection().equals("UP") ||pacMan.getDirection().equals("DOWN"))){
				
//				System.out.println("DOWN");
//				System.out.println(e);
				pacMan.changeDirection("DOWN");
//				showSnakeGone();
				checkFinish();
//				pacMan.move();
//				showSnake();
		
			}
		}	else if (e.getKeyCode() == 113){
				clear();
				PMView.setDie(5);
				PMView.setFood(1);
				randomFood(PMView.getFood());
				randomDie(PMView.getDie());
				timer.stop();
				timer = new Timer(100, this);
				run();

		
			
		}
		else if (e.getKeyCode() == 32){
			if (timer.isRunning()){
			
				timer.stop();
			}
			else{
				timer.start();
			}
		}
	}
	private boolean checkDeath(){
		//check to see if ate a death piece.
		return false;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	private void checkFinish(){
		boolean check = true;
		if (checkDeath() || (!(pacMan.checkMove(rows, cols)))){
			timer.stop();
			JOptionPane.showMessageDialog(null, "YOU LOSE.  Your score is: " + (pacMan.getLength()-2), "GAME OVER", JOptionPane.PLAIN_MESSAGE );

		}
		else{
			
				showPMGone();
				pacMan.move();
	
				if (labels[pacMan.getRow()][pacMan.getCol()].getBackground().equals(Color.RED)){
					timer.stop();
					JOptionPane.showMessageDialog(null, "YOU LOSE", "GAME OVER", JOptionPane.PLAIN_MESSAGE );

					check = false;
				}
				if (check){
					showPM();
				}
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		checkFinish();
	}
	
}