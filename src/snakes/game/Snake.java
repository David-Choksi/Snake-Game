package snakes.game;

import java.util.ArrayList;

public class Snake {
	private int length = 0;
	private RowCol head;
	private ArrayList<RowCol> body = new ArrayList<RowCol>();
	private String direction;
	private String playerName = "";

	public void setHead(int row, int col) {
		head = new RowCol(row, col);
	}

	public Snake(RowCol head, RowCol body, String direction, String player) {
		setLength(2);
		this.head = new RowCol(head);
		this.body.add(new RowCol(body));
		this.body.add(new RowCol(body));
		this.direction = direction;
		playerName = player;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<RowCol> getBody() {

		return (ArrayList<RowCol>) body.clone();
	}

	public RowCol getHead() {
		return new RowCol(head);
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getDirection() {
		return direction;
	}

	public int getRow() {
		return head.row();
	}

	public int getCol() {
		return head.col();
	}

	public void addLength() {
		body.add(head);
		setLength(getLength() + 1);
	}

	public boolean checkForSnake(Snake p1, Snake p2) {
		if (p1.getBody().contains(p1.getHead()) || p2.getBody().contains(p2.getHead())
				|| p2.getBody().contains(p1.getHead()) || p1.getHead().equals(p2.getHead())) {
			return true;
		}

		return false;
	}

	public boolean checkForSnake(Snake p1) {
		if (p1.getBody().contains(p1.getHead())) {
			return true;
		}

		return false;
	}

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

	public RowCol removing() {
		return body.get(0);
	}

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

	public void changeDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Row: " + this.getRow() + ", Col: " + this.getCol();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
