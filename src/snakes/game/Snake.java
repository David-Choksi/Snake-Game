package snakes.game;

import java.util.ArrayList;

public class Snake {
	private int length=0;
	private  RowCol head;
	private  ArrayList<RowCol> body = new ArrayList<RowCol>();
	private String direction = Model.RIGHTDIRECTION;
	public void setHead(int row, int col){
		head = new RowCol(row, col);
	}
	public Snake(){
		setLength(2);
		head = new RowCol(12, 1);
		body.add(new RowCol(12,0));
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
//		if (body.contains(head)){
//			return true;
//		}
//		
		return false;
	}
	
	public boolean checkMove(){
		if (direction.equals(Model.RIGHTDIRECTION)){
			if (head.col()>=View.GAME_WIDTH){
				System.out.println(View.GAME_WIDTH);
				return false;
			}
		}
		if (direction.equals(Model.LEFTDIRECTION)){
			if (head.col()<0){
				System.out.println("0 width");
				return false;
			}
		}
		if (direction.equals(Model.UPDIRECTION)){
			if (head.row()<0){
				System.out.println("0 height");
				return false;
			}
		}
		if (direction.equals(Model.DOWNDIRECTION)){
			if (head.row()>=View.GAME_HEIGHT){
				System.out.println(View.GAME_HEIGHT);
				return false;
			}
		}
		return true;
	}
	public RowCol nextMove(){
		if (direction.equals(Model.RIGHTDIRECTION)){
			return head.nextCol();
		}
		if (direction.equals(Model.LEFTDIRECTION)){
			return head.prevCol();
		}
		if (direction.equals(Model.UPDIRECTION)){
			return head.prevRow();
		}
		if (direction.equals(Model.DOWNDIRECTION)){
			return head.nextRow();
		}
		return head;
		
	}
	public RowCol removing(){
		return body.get(0);
	}
	
	
	public void move(){
		body.remove(0);
		body.add(head);
		
		if (direction.equals(Model.RIGHTDIRECTION)){
			head = head.nextCol();
		}
		if (direction.equals(Model.LEFTDIRECTION)){
			head=head.prevCol();
		}
		if (direction.equals(Model.UPDIRECTION)){
			head = head.prevRow();
		}
		if (direction.equals(Model.DOWNDIRECTION)){
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

}






