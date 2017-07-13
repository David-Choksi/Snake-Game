package PacMan.game;

import java.util.ArrayList;

public class PacMan {
	private int length=1;
	private  RowCol head;
	private String direction = "RIGHT";
	public void setHead(int row, int col){
		head = new RowCol(row, col);
	}
	public PacMan(){
		setLength(1);
		head = new RowCol(22, 12);
	}
	public String getDirection(){
		return direction;
	}
	public int getRow(){
		return head.row();
	}
	public int getCol(){
		return head.col();
	}
	public boolean checkMove(int rows, int cols){
		if (direction.equals("RIGHT")){
			if (head.col()+1>=cols){
				return false;
			}
		}
		if (direction.equals("LEFT")){
			if (head.col()-1<0){
				return false;
			}
		}
		if (direction.equals("UP")){
			if (head.row()-1<0){
				return false;
			}
		}
		if (direction.equals("DOWN")){
			if (head.row()+1>=rows){
				return false;
			}
		}
		return true;
	}
	
	public RowCol removing(){
		return head;
	}
	
	
	public void move(){

		if (direction.equals("RIGHT")){
			head = head.nextCol();
		}
		if (direction.equals("LEFT")){
			head=head.prevCol();
		}
		if (direction.equals("UP")){
			head = head.prevRow();
		}
		if (direction.equals("DOWN")){
			head = head.nextRow();
		}
	}
	public void changeDirection(String direction){
		this.direction = direction;
	}
	@Override
	public String toString(){
		return "Row: " + this.getRow() + ", Col: " + this.getCol();
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
//	public static void main(String[] args){
//		Snake snake = new Snake();
//		System.out.println(snake);
//		snake.move();
//		snake.changeDirection("UP");
//		snake.move();
//		System.out.println(snake);
//	}
}






