package PacMan.game;


public class PMGame {
	public static void main(String[] args) throws InterruptedException{
		Model model = new Model(29,26);
		PMController pMController = new PMController();
		PMView view = new PMView(29,26,pMController);
		pMController.set(model, view);
		view.setVisible(true);
	}
}
