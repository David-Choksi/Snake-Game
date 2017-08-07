package snakes.game;

import java.util.Objects;

/**
 *  
 * Creates a simple Row and column coordinates object, which handles cartesian coordinates
 * on a 2D plane.
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Christiana
 * Papajani
 * 
 */
public class RowCol {
	private int row;
	private int column;

	/**
	 * A parameterized constructor which takes and sets the attributes of an instance 
	 * of the RowCol class.
	 * @param row an int representing the number of rows
	 * @param col an int representing the number of columns
	 */
	public RowCol(int row, int col) {
		this.row = row;
		this.column = col;
	}

	/**
	 * A copy constructor which takes another RowCol object's attributes and copys it 
	 * into a new RowCol object.
	 * @param other another RowCol object.
	 */
	public RowCol(RowCol other) {
		this.row = other.row();
		this.column = other.col();
	}

	/**
	 * This final method returns the row's coordinate.
	 * @return row the number of rows from a RowCol object.
	 */
	public final int row() {
		return row;
	}

	/**
	 * This final method returns the col's coordinate.
	 * @return col the number of cols from a RowCol object.
	 */
	public final int col() {
		return column;
	}

	/**
	 * This is a method which will return the next row after we have already gotten the previous row
	 * @return RowCol a RowCol object with an extra row from the previous object.
	 */
	public RowCol nextRow() {
		RowCol nextRow = new RowCol(this.row() + 1, this.col());
		return nextRow;
	}

	/**
	 * This is a method which will return the previous row after we have already gotten a row
	 * @return RowCol a RowCol object with a previous row from the other object.
	 */
	public RowCol prevRow() {
		RowCol preRow = new RowCol((this.row() - 1), this.col());
		return preRow;
	}

	/**
	 * This is a method which will return the next column after we have already gotten a column
	 * @return RowCol a RowCol object with a next column from the other object.
	 */
	public RowCol nextCol() {
		RowCol nextCol = new RowCol(this.row(), this.col() + 1);
		return nextCol;
	}

	/**
	 * This is a method which will return the previous column after we have already gotten a column
	 * @return RowCol a RowCol object with a previous column from the other object.
	 */
	public RowCol prevCol() {
		RowCol preCol = new RowCol(this.row(), (this.col() - 1));
		return preCol;
	}

	@Override
	/**
	 * An overriden equals method, made to properly compare two instances of RowCol objects.
	 * @param Object an object to compare the local instance to.
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RowCol)) {
			return false;
		}
		RowCol object = (RowCol) obj;
		if (this.row() == object.row() && this.col() == object.col()) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * An overriden hashCode method used to generate appropriate hashcode for a particular 
	 * RowCol object.
	 * @return int an int representing the hasCode of a particular RowCol object.
	 */
	public int hashCode() {
		return Objects.hash(this.row(), this.col());
	}

	@Override
	/**
	 * An overriden toString method used to return an appropriate string representation
	 * of a RowCol object. In this case the String object contains a row and column
	 * @return String a string representation of the object.
	 */
	public String toString() {
		return "[row = " + this.row + ", col = " + this.column + "]";
	}
}
