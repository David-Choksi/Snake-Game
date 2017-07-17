package snakes.game;


import java.awt.Color;

import javax.swing.JOptionPane;

public class Model {
	public static final String UPDIRECTION = "UP";
	public static final String DOWNDIRECTION = "DOWN";
	public static final String LEFTDIRECTION = "LEFT";
	public static final String RIGHTDIRECTION = "RIGHT";
	private int row;
	private int col;
	private View view;
	private Snake player1= new Snake();
	private Thread timer;
	private Thread snakeTimer;
	private boolean paused = true;
	
	public Model (){
		this.row = View.GAME_WIDTH-1;
		this.col = View.GAME_HEIGHT-1;

	}

	@SuppressWarnings("deprecation")
	public void newGame(){
		paused = false;
		player1 = new Snake();
		view.clear();
		View.setDie(5);
		View.setFood(1);
		view.randomFood(View.getFood());
		view.randomDie(View.getDie());
		if (!(timer == null)){
			timer.stop();
			snakeTimer.stop();
		}
		
		Runnable time = new Runnable(){
			int counter = 0;
			
			@Override
			public void run() {
				while (true){
					try {
						counter++;
						view.setTitle("Time:  " + counter);

						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
			}
			
		};
		timer = new Thread(time);
		timer.start();
		
		Runnable snakeTime = new Runnable(){

			
			@Override
			public void run() {
				while (true){
					try {
						player1.move();
						showSnake();
						showSnakeGone();
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
			}
			
		};
		snakeTimer = new Thread(snakeTime);

		snakeTimer.start();
		
	}
	
	public void showSnakeGone(){

		view.labels[player1.removing().row()][player1.removing().col()].setText("");
		view.labels[player1.removing().row()][player1.removing().col()].setOpaque(false);
		view.labels[player1.removing().row()][player1.removing().col()].setBackground(Color.BLUE);
		view.labels[player1.removing().row()][player1.removing().col()].setForeground(Color.BLUE);
	}
	
	public void showSnake(){
		
		view.labels[player1.getRow()][player1.getCol()].setText("S");
		view.labels[player1.getRow()][player1.getCol()].setOpaque(true);		

		view.labels[player1.getRow()][player1.getCol()].setBackground(Color.RED);
		view.labels[player1.getRow()][player1.getCol()].setForeground(Color.RED);
	}
	
	public void setView(View view){
		this.view = view;
		showSnake();
	}
	public int rows(){
		return this.row;
	}
	
	public int cols(){
		return this.col;
	}

	
	public void leftPressed(){
		if(!(player1.getDirection().equals(LEFTDIRECTION) ||player1.getDirection().equals(RIGHTDIRECTION))){
			player1.changeDirection(LEFTDIRECTION);
			checkFinish();
		}
	}
	public void rightPressed(){
		if(!(player1.getDirection().equals(LEFTDIRECTION) ||player1.getDirection().equals(RIGHTDIRECTION))){
			player1.changeDirection(RIGHTDIRECTION);
			checkFinish();
		
		}
	}
	public void upPressed(){
		if(!(player1.getDirection().equals(UPDIRECTION) ||player1.getDirection().equals(DOWNDIRECTION))){
			player1.changeDirection(UPDIRECTION);
			checkFinish();
			
		}
	}
	public void downPressed(){
		if(!(player1.getDirection().equals(UPDIRECTION) ||player1.getDirection().equals(DOWNDIRECTION))){
			player1.changeDirection(DOWNDIRECTION);
			checkFinish();
			
		}
	}
	@SuppressWarnings("deprecation")
	public void f2Pressed(){
		if (!paused){
			snakeTimer.suspend();
			timer.suspend();
		}
		int check = JOptionPane.showOptionDialog(view,  "Do you want to start a new game?", null, 0,0, null, null, "New Game");
		if (check == 0 ){
			newGame();
		}
		else{
			snakeTimer.resume();
			timer.resume();
		}
	}
	@SuppressWarnings("deprecation")
	public void spacePressed(){
		if (!paused){
			snakeTimer.suspend();
			timer.suspend();
			paused = true;
		}
		else{
			snakeTimer.resume();
			timer.resume();
			paused = false;
		}
	}

	@SuppressWarnings("deprecation")
	public void checkFinish(){
		boolean check = true;
		if ((!(player1.checkMove(rows(),cols())))){
			timer.stop();
			snakeTimer.stop();
			JOptionPane.showMessageDialog(null, "YOU LOSE.  Your score is: " + (player1.getLength()-2), "GAME OVER", JOptionPane.PLAIN_MESSAGE );
	
		}
		else{
			if (view.foodLocation.contains(new RowCol(player1.getRow(), player1.getCol()))){
				player1.addLength();
				view.foodLocation.remove(new RowCol(player1.getRow(), player1.getCol()));
				view.randomFood(1);
			}
			else if (view.dieLocation.contains(new RowCol(player1.getCol(), player1.getRow()))){
				timer.stop();
				snakeTimer.stop();
				JOptionPane.showMessageDialog(null, "YOU LOSE.  Your score is: " + (player1.getLength()-2), "GAME OVER", JOptionPane.PLAIN_MESSAGE );
	
				
			}
		
			else{
				showSnakeGone();
				player1.move();
	
				if (view.labels[player1.getRow()][player1.getCol()].getBackground().equals(Color.RED)){
					timer.stop();
					snakeTimer.stop();
					JOptionPane.showMessageDialog(null, "YOU LOSE.  Your score is: " + (player1.getLength()-2), "GAME OVER", JOptionPane.PLAIN_MESSAGE );
	
					check = false;
				}
				if (check){
					showSnakeGone();
					showSnake();
				}
			}
		}

	}
}