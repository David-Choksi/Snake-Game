package snakes.game;


public class Game {
	/**
	 * Program to run the snakes game
	 * @param args default expression
	 * @throws InterruptedException  
	 */
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller();
		View view = new View(controller, controller);
		model.setView(view);
		controller.set(model, view);
		view.setVisible(true);
		
	}
}
