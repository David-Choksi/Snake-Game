package snakes.game;

/**
 * The main class which actually runs the game using PVSM.
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Kristiana
 *         Papajani
 *
 */
public class Game {
	/**
	 * The main method which handles the execution of the snake game. Here, the model, view, and controller
	 * are all put together to handle the running of the game itself.
	 * 
	 * @param args default expression
	 */
	public static void main(String[] args) {

		Model model = new Model();
		Controller controller = new Controller();
		View view = new View(controller, controller);
		model.setView(view);
		controller.set(model, view);
		Networking.runServer();
		view.setVisible(true);
	}
}
