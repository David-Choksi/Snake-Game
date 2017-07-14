package PacMan.game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;


public class PMController implements KeyListener, ActionListener {

	private PMModel model;
	private PMView view;


	public PMController() {
		this.model = null;
		this.view = null;
	}
	public void set(PMModel model, PMView view) {
		this.model = model;
		this.view = view;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals(PMView.NEW_GAME)) {
				
		} 
		else if (action.equals(PMView.HIGH_SCORE)) {
	

		} 
		else if (action.equals(PMView.EXIT)) {
		
			int check = JOptionPane.showOptionDialog(view, "Do you want to exit?", action, 0,0, null, null, "EXIT");
			System.out.println(check);
			if (check == 0 ){
				this.view.dispose();
			}
			
		}
		else if (action.equals(PMView.MENU_HELP)) {
			JOptionPane.showMessageDialog(view, "USE F2 to start new game. Use SpaceBar to pause!");
		//Show Help
			
		}
		else{

			this.view.dispose();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		if (e.getKeyCode() == 37){   // left key pressed
			if(!(model.pmGetDirection().equals("LEFT") || model.pmGetDirection().equals("RIGHT"))){
				model.pmDirectionChange("LEFT");
			}
		}
		else if (e.getKeyCode() == 38){  // up key pressed
			if(!(model.pmGetDirection().equals("UP") || model.pmGetDirection().equals("DOWN"))){
				
				model.pmDirectionChange("UP");

			}
		}
		else if (e.getKeyCode() == 39){  // right key pressed
			if(!(model.pmGetDirection().equals("LEFT") || model.pmGetDirection().equals("RIGHT"))){
				model.pmDirectionChange("RIGHT");
			}
		}
		else if (e.getKeyCode() == 40){  // down key pressed
			if(!(model.pmGetDirection().equals("UP") || model.pmGetDirection().equals("DOWN"))){
				
				model.pmDirectionChange("DOWN");
		
		
			}
		
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}