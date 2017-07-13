package snakes.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * The controller for a sliding puzzle game. The controller
 * is an aggregation of a model and a view.
 *
 */
public class Controller implements ActionListener {

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
			view.clear();
			View.setDie(5);
			View.setFood(1);
			view.randomFood(View.getFood());
			view.randomDie(View.getDie());
			model.shuffle();

			view.setTimer(100);
			view.run();

		} 
		else if (action.equals(View.NEW_ADVANCED_GAME)) {
			view.clear();
			View.setDie(10);
			View.setFood(1);
			view.randomFood(View.getFood());
			view.randomDie(View.getDie());
			model.shuffle();

			view.setTimer(60);
			view.run();
		} 
		else if (action.equals(View.EXIT)) {
		
			int check = JOptionPane.showOptionDialog(view, "Do you want to exit?", action, 0,0, null, null, "EXIT");
			System.out.println(check);
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

}