package snakes.game;


public class Game {
	public static void main(String[] args) throws InterruptedException{
		Model model = new Model(25,40);
		Controller controller = new Controller();
		View view = new View(25,40,controller);
		controller.set(model, view);
		view.setVisible(true);


	}
}
