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
	public boolean checkMove(int rows, int cols){
		if (direction.equals(Model.RIGHTDIRECTION)){
			if (head.col()+1>=cols){
				return false;
			}
		}
		if (direction.equals(Model.LEFTDIRECTION)){
			if (head.col()-1<0){
				return false;
			}
		}
		if (direction.equals(Model.UPDIRECTION)){
			if (head.row()-1<0){
				return false;
			}
		}
		if (direction.equals(Model.DOWNDIRECTION)){
			if (head.row()+1>=rows){
				return false;
			}
		}
		return true;
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






