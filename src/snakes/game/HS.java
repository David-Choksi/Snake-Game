package snakes.game;

public class HS {
	public String name;
	public int score;

	public HS(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public HS() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return this.name + ",  " + this.score;
	}
}
