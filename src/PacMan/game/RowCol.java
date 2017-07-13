package PacMan.game;

import java.util.Objects;

public class RowCol {
	private int row;
	private int column;
	public RowCol(int row, int col){
		this.row = row;
		this.column= col;
	}
	public RowCol(RowCol other){
		this.row = other.row();
		this.column = other.col();
	}
	public final int row() {
		return row;
	}
	public final int col() {
		return column;
	}
	public RowCol nextRow(){
		RowCol nextRow = new RowCol(this.row()+1, this.col());
		return nextRow;
	}
	public RowCol prevRow(){
		RowCol preRow = new RowCol((this.row()-1), this.col());
		return preRow;
	}
	public RowCol nextCol(){
		RowCol nextCol = new RowCol(this.row(), this.col()+1);
		return nextCol;
	}
	public RowCol prevCol(){
		RowCol preCol = new RowCol(this.row(), (this.col()-1));
		return preCol;
	}
	@Override
	public boolean equals(Object obj){
		if (obj == null){
			return false;
		}
     if (!(obj instanceof RowCol)) {
            return false;
        }
       RowCol object = (RowCol) obj;
		if (this.row() == object.row() && this.col() == object.col()){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return  Objects.hash(this.row(), this.col());
	}
	
	@Override
	public String toString(){
		return "[row = " + this.row + ", col = " + this.column + "]";
	}
}
