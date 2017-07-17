package snakes.game;


import javax.swing.JOptionPane;

public class Model {
	private static final String UPDIRECTION = "UP";
	private static final String DOWNDIRECTION = "DOWN";
	private static final String LEFTDIRECTION = "LEFT";
	private static final String RIGHTDIRECTION = "RIGHT";
	private int row;
	private int col;
	private View view;
	private Snake player1= new Snake();

	
	public Model (){
		this.row = View.GAME_WIDTH;
		this.col = View.GAME_HEIGHT;

	}

	public void newGame(){
		view.clear();
		View.setDie(5);
		View.setFood(1);
		view.randomFood(View.getFood());
		view.randomDie(View.getDie());
	}
	
	public void setView(View view){
		this.view = view;
	}
	public int rows(){
		return this.row;
	}
	
	public int cols(){
		return this.col;
	}

	private void checkFinish(){

	}
	
	public void leftPressed(){
		if(!(player1.getDirection().equals(LEFTDIRECTION) ||player1.getDirection().equals(RIGHTDIRECTION))){
			checkFinish();
		}
	}
	public void rightPressed(){
		if(!(player1.getDirection().equals(LEFTDIRECTION) ||player1.getDirection().equals(RIGHTDIRECTION))){
			checkFinish();
		
		}
	}
	public void upPressed(){
		if(!(player1.getDirection().equals(UPDIRECTION) ||player1.getDirection().equals(DOWNDIRECTION))){
			checkFinish();
			
		}
	}
	public void downPressed(){
		if(!(player1.getDirection().equals(UPDIRECTION) ||player1.getDirection().equals(DOWNDIRECTION))){
			checkFinish();
			
		}
	}
	public void f2Pressed(){
		int check = JOptionPane.showOptionDialog(view,  "Do you want to start over?", null, 0,0, null, null, "Quit");
		System.out.println(check);
		if (check == 0 ){
			// new game
		}
	}
	public void spacePressed(){
		checkFinish();

	}


}
