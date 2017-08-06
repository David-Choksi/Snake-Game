package snakes.game;

public class HS<S, N> {
	
	public S name;
	public N score;

	public HS(S name, N score) {
		this.name = name;
		this.score = score;
	}

	public HS() {
	}

	public S getName() {
		return name;
	}

	public void setName(S name) {
		this.name = name;
	}

	public N getScore() {
		return score;
	}

	public void setScore(N score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return this.name + ",  " + this.score;
	}
}
