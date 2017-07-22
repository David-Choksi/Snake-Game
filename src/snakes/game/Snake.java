package snakes.game;

import java.util.ArrayList;

public class Snake {
	private int length=0;
	private  RowCol head;
	private  ArrayList<RowCol> body = new ArrayList<RowCol>();
	private String direction = Model.LEFTDIRECTION;
	public void setHead(int row, int col){
		head = new RowCol(row, col);
	}
	public Snake(){
		setLength(2);
		head = new RowCol(12, 38);
		body.add(new RowCol(12,39));
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
	public void addLength(){
		body.add(head);
		setLength(getLength() + 1);
	}
	
	public boolean checkForSnake(){
		if (body.contains(head)){
			return true;
		}
		
		return false;
	}
	
	public boolean checkMove(){
		if (direction.equals(Model.RIGHTDIRECTION)){
			if (head.col()>=View.GAME_WIDTH-1){
				System.out.println(View.GAME_WIDTH);
				return false;
			}
		}
		if (direction.equals(Model.LEFTDIRECTION)){
			if (head.col()<1){
				System.out.println("0 width");
				return false;
			}
		}
		if (direction.equals(Model.UPDIRECTION)){
			if (head.row()<1){
				System.out.println("0 height");
				return false;
			}
		}
		if (direction.equals(Model.DOWNDIRECTION)){
			if (head.row()>=View.GAME_HEIGHT-1){
				System.out.println(View.GAME_HEIGHT);
				return false;
			}
		}
		return true;
	}
	public RowCol nextMove(){
		if (direction.equals(Model.RIGHTDIRECTION)){
			return new RowCol(head.nextCol());
		}
		if (direction.equals(Model.LEFTDIRECTION)){
			return new RowCol(head.prevCol());
		}
		if (direction.equals(Model.UPDIRECTION)){
			return new RowCol(head.prevRow());
		}
		if (direction.equals(Model.DOWNDIRECTION)){
			return new RowCol(head.nextRow());
		}
		return new RowCol( head);
		
	}
	public RowCol removing(){
		return body.get(0);
	}
	
	
	public void move(){
		body.remove(0);
		body.add(head);

		if (direction.equals(Model.RIGHTDIRECTION)){
			head = new RowCol(head.nextCol());
		}
		else if (direction.equals(Model.LEFTDIRECTION)){
			head= new RowCol(head.prevCol());
		}
		else if (direction.equals(Model.UPDIRECTION)){
			head = new RowCol(head.prevRow());
		}
		else if (direction.equals(Model.DOWNDIRECTION)){
			head = new RowCol(head.nextRow());
		}
		else {
			System.out.println("NOPE");  //Should never happen
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

}






