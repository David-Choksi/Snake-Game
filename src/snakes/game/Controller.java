package snakes.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

/**
 * The controller for a sliding puzzle game. The controller
 * is an aggregation of a model and a view.
 *
 */
public class Controller implements ActionListener, KeyListener {

	private Model model;
	private View view;

	/**
	 * Initializes the controller so that it has no model
	 * and no view.
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
	 * Respond to the user clicking a button or menu item in
	 * the view. See the Lab 7 document for details.
	 * 
	 * @param e the action event to respond to
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(View.NEW_GAME)) {
			model.newGame();


		} 
		else if (action.equals(View.NEW_ADVANCED_GAME)) {
			view.clear();
			View.setDie(10);
			View.setFood(1);
			view.randomFood(View.getFood());
			view.randomDie(View.getDie());

		} 
		else if (action.equals(View.EXIT)) {
		
			int check = JOptionPane.showOptionDialog(view, "Do you want to exit?", action, 0,0, null, null, "EXIT");
	
			if (check == 0 ){
				this.view.dispose();
			}
			
		}
		else if (action.equals(View.MENU_HELP)) {
			JOptionPane.showMessageDialog(view, "USE F2 to start new game. Use SpaceBar to pause!");
		//Show Help
			
		}
		else{

			this.view.dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == 37){
			model.leftPressed();
		}
		else if (e.getKeyCode() == 38){
			model.upPressed();
		}
		else if (e.getKeyCode() == 39){
			model.rightPressed();
		}
		else if (e.getKeyCode() == 40){
			model.downPressed();
		}	else if (e.getKeyCode() == 113){
			model.f2Pressed();  // new game
			
		}
		else if (e.getKeyCode() == 32){
			model.spacePressed();   //pause game using spacebar
		}
	}
		
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}