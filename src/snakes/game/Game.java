package snakes.game;


public class Game {
	public static void main(String[] args) throws InterruptedException{
		Model model = new Model();
		Controller controller = new Controller();
		View view = new View(controller, controller);
		model.setView(view);
		controller.set(model, view);
		view.setVisible(true);
	}
}
