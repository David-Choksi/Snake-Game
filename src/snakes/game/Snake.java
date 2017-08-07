package snakes.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class take care of everything related to the snake itself. Its body, movement, addition to its 
 * body after it eats, as well as its direction and its location on the board. Everything about the a snake
 * object is written here.
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Kristiana
 *         Papajani
 */
public class Snake {
	private int length = 0;
	private RowCol head;
	private ArrayList<RowCol> body = new ArrayList<RowCol>();
	private String direction;
	private String playerName = "";
	Random rand = new Random();



	/**
	 * This parameterized constructor sets everything related to a new snake object.
	 * @param head the head and its position on the game board.
	 * @param body the body and how many segments are added to the snake itself.
	 * @param direction the direction that the snake is heading in.
	 * @param player the name of the player which controls a certain snake.
	 */
	public Snake(RowCol head, RowCol body, String direction, String player) {
		setLength(2);
		this.head = new RowCol(head);
		this.body.add(new RowCol(body));
		this.body.add(new RowCol(body));
		this.direction = direction;
		playerName = player;
	}

	/**
	 * This setter method sets the position of the head on the game board. 
	 * @param row a row value
	 * @param col a column value
	 */
	public void setHead(int row, int col) {
		head = new RowCol(row, col);
	}
	
	/**
	 * This getter method returns a cloned ArrayList of a body of a snake.
	 * @return ArrayList a body of a snake.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<RowCol> getBody() {

		return (ArrayList<RowCol>) body.clone();
	}

	/**
	 * A getter method which returns the position of the head of a snake.
	 * @return RowCol a RowCol object representing the position of the head of a snake.
	 */
	public RowCol getHead() {
		return new RowCol(head);
	}

	/**
	 * A getter method which returns the player's name of a snake.
	 * @return String an appropriate player name.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * A getter method which returns the direction that a snake is heading in.
	 * @return String the direction a snake is heading in, in a string interpretation.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * A getter method which returns the row of the head of a snake.
	 * @return int a row of the snake's head.
	 */
	public int getRow() {
		return head.row();
	}

	/**
	 * A getter method which returns a column integer of a snake's head.
	 * @return int an int representing the column of the snake's head.
	 */
	public int getCol() {
		return head.col();
	}

	/**
	 * Creates a random number, and uses it in order to randomly add 1-3 segments to the snake after 
	 * it has eaten a food item. 
	 */
	public void addLength() {	
		int  n = rand.nextInt(3) + 1;
		if (n == 1){
			body.add(head);
			//.setLength(getLength() + 1);
		}
		
		else if (n == 2){
			body.add(head);
			body.add(head);
			//setLength(getLength() + 2);
		}
		
		else if (n == 3){
			body.add(head);
			body.add(head);
			body.add(head);
			//setLength(getLength() + 3);
		}
//		body.add(head);
		setLength(getLength() + n);
	}

	/**
	 * Checks to see if there is a collision with each of the player's heads in the 
	 * other player's bodies. 
	 * @param p1 the snake for Player 1
	 * @param p2 the snake for Player 2
	 * @return boolean a true or false value depending on if there is a collision.
	 */
	public boolean checkForSnake(Snake p1, Snake p2) {
		if (p2.getBody().contains(p1.getHead()) || p1.getHead().equals(p2.getHead())) {
			return true;
		}

		return false;
	}

	/**
	 * Checks to see if there is a collision of the snake's head with its own body.
	 * @param p1 a snake player
	 * @return boolean a true or false value depending on whether there is a collision or not.
	 */
	public boolean checkForSnake(Snake p1) {
		if (p1.getBody().contains(p1.getHead())) {
			return true;
		}

		return false;
	}

	/**
	 * Checks to see if the snake is out of bounds of the game's borders.
	 * @return boolean a true or false value depending on whether the snake is still in the game's borders, or out of bounds, accordingly.
	 */
	public boolean checkMove() {
		if (direction.equals(Model.RIGHTDIRECTION) || direction.equals(Model.P2RIGHTDIRECTION)) {
			if (head.col() >= View.GAME_WIDTH - 1) {
				return false;
			}
		} else if (direction.equals(Model.LEFTDIRECTION) || direction.equals(Model.P2LEFTDIRECTION)) {
			if (head.col() < 1) {
				return false;
			}
		} else if (direction.equals(Model.UPDIRECTION) || direction.equals(Model.P2UPDIRECTION)) {
			if (head.row() < 1) {
				return false;
			}
		} else if (direction.equals(Model.DOWNDIRECTION) || direction.equals(Model.P2DOWNDIRECTION)) {
			if (head.row() >= View.GAME_HEIGHT - 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Prepares the visual representation of the snake to move in a certain direction.
	 * @return RowCol a new RowCol object representing the next step in the snake's direction.
	 */
	public RowCol nextMove() {
		if (direction.equals(Model.RIGHTDIRECTION) || direction.equals(Model.P2RIGHTDIRECTION)) {
			return new RowCol(head.nextCol());

		} else if (direction.equals(Model.LEFTDIRECTION) || direction.equals(Model.P2LEFTDIRECTION)) {
			return new RowCol(head.prevCol());
		} else if (direction.equals(Model.UPDIRECTION) || direction.equals(Model.P2UPDIRECTION)) {
			return new RowCol(head.prevRow());
		} else if (direction.equals(Model.DOWNDIRECTION) || direction.equals(Model.P2DOWNDIRECTION)) {
			return new RowCol(head.nextRow());
		}
		return new RowCol(head);

	}

	/**
	 * Removes the snake's body from the game.
	 * @return RowCol a RowCol object representing the point of origin in the game.
	 */
	public RowCol removing() {
		return body.get(0);
	}

	/**
	 * Moves the visual representation of the snake in a certain direction, using the 2D coordinates
	 * laid down by the RowCol class. 
	 */
	public void move() {
		body.remove(0);
		body.add(head);

		if (direction.equals(Model.RIGHTDIRECTION) || direction.equals(Model.P2RIGHTDIRECTION)) {
			head = new RowCol(head.nextCol());
		} else if (direction.equals(Model.LEFTDIRECTION) || direction.equals(Model.P2LEFTDIRECTION)) {
			head = new RowCol(head.prevCol());
		} else if (direction.equals(Model.UPDIRECTION) || direction.equals(Model.P2UPDIRECTION)) {
			head = new RowCol(head.prevRow());
		} else if (direction.equals(Model.DOWNDIRECTION) || direction.equals(Model.P2DOWNDIRECTION)) {
			head = new RowCol(head.nextRow());
		} else {
			System.out.println("NOPE"); // Should never happen
		}

	}

	/**
	 * Changes the direction of a snake into a new direction set by this method.
	 * @param direction the new direction to set the snake into.
	 */
	public void changeDirection(String direction) {
		this.direction = direction;
	}

	@Override
	/**
	 * An overridden toString method. Provides an appropriate representation of the snake 
	 * object by providing its Cartesian location on the board.
	 * @return String a string representation of the object.
	 */
	public String toString() {
		return "Row: " + this.getRow() + ", Col: " + this.getCol();
	}

	/**
	 * A getter method to get the length of the snake.
	 * @return int an int representing the length of the snake.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * A setter method to set the length of the snake.
	 * @param length an int to set the length of the snake to.
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
