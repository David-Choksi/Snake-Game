package PacMan.game;


public class PMGame {
	public static void main(String[] args) throws InterruptedException{
		PMModel model = new PMModel();
		PMController controller = new PMController();
		PMView view = new PMView(controller);
		controller.set(model, view);
		view.setVisible(true);
	}
}
