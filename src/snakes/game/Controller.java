package snakes.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;


/**
 * The controller for a snake puzzle game. The controller is an aggregation of
 * a model and a view. It handles logic figured out from the Model and changes 
 * the view for the user accordingly.
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Christiana
 *         Papajani
 *
 */
public class Controller implements ActionListener, KeyListener {
	public static final String P1 = "player 1";
	public static final String P2 = "player 2";

	private Model model;
	private View view;

	/**
	 * Initializes the controller so that it has no model and no view.
	 */
	public Controller() {
		this.model = null;
		this.view = null;
	}

	/**
	 * Set the model and view for this controller.
	 * 
	 * @param model the model
	 * @param view the view
	 */
	public void set(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * 
	 * This is an implementation of the method from the ActionListener interface. It
	 * is used in order to respond to the user clicking a button or menu item in the view.
	 * 
	 * @param e the action event to respond to.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		//If the player chooses a new game, then a new game is created.
		if (action.equals(View.NEW_GAME)) {
			model.resetNumberOfPlayers();
			model.newGame();

		//If the player chooses to view the high scores, then the list of high scores are displayed.
		} else if (action.equals(View.HIGH_SCORE)) {
			model.showHighScores();
			
			//If the player chooses to have a multiplayer game, then a 2-player game is set up.
		} else if (action.equals(View.NEW_2_PLAYER_GAME)) {
			model.newTwoPlayerGame();
			
			//If the player wants to exit the game, then a prompt is shown asking if the player truly wants
			//to exit. If yes, then the game closes.
		} else if (action.equals(View.EXIT)) {

			int check = JOptionPane.showOptionDialog(view, "Do you want to exit?", action, 0, 0, null, null, "EXIT");

			if (check == 0) {
				this.view.dispose();
			}

			//If the player asks for help, then instructions are displayed in a dialog box.
		} else if (action.equals(View.MENU_HELP)) {
			JOptionPane.showMessageDialog(view, "USE F2 to start new game. Use SpaceBar to pause!");
			// Show Help

		} else {

			this.view.dispose();
		}
	}

	@Override
	/**
	 * Handles the button presses of the use across their keyboard. 
	 * Depending on which button is pressed, the according method in the model is
	 * called to handle an action associated with that button.
	 * @param e the value representing the button pressed on the keyboard.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			model.leftPressed(P1);
		} else if (e.getKeyCode() == 38) {
			model.upPressed(P1);
		} else if (e.getKeyCode() == 39) {
			model.rightPressed(P1);
		} else if (e.getKeyCode() == 40) {
			model.downPressed(P1);
		} else if (e.getKeyCode() == 113) {
			model.f2Pressed(); // new game
		} else if (e.getKeyCode() == 32) {
			model.spacePressed();
		}
		// This is for Player 2
		else if (e.getKeyCode() == 65) {
			model.leftPressed(P2);
		} else if (e.getKeyCode() == 87) {
			model.upPressed(P2);
		} else if (e.getKeyCode() == 68) {
			model.rightPressed(P2);
		} else if (e.getKeyCode() == 83) {
			model.downPressed(P2);
		} else {
		}
	}

	@Override
	/**
	 * An empty method, used for handling keyReleased events.
	 */
	public void keyReleased(KeyEvent e) {

	}

	@Override
	/**
	 * An empty method, used for handling keyTyped events.
	 */
	public void keyTyped(KeyEvent arg0) {

	}

}