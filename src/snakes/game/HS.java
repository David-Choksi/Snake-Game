package snakes.game;

/**
 * The high score class is a generic class which stores a name and a number representing the player
 * and the score the player has, respectively. 
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Christiana
 *         Papajani
 *
 * @param <S> the name of the player
 * @param <N> the score of the player
 */
public class HS<S, N> {
	
	public S name;
	public N score;

	
	/**
	 * EXAMPLE OF OVERLOADED CONSTRUCTOR
	 * 
     * This constructor takes in a name and a score which are held
     * as attributes within the instance of the class.
     * 
     * @param name is the players name 
     * @param score is the score that will be tied to the specific player
     */
	public HS(S name, N score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * EXAMPLE OF OVERLOADED CONSTRUCTOR
	 * This is a default constructor of the HS class. 
	 * Fields name and score are set to their default values.
	 * 
	 */
	public HS() {
	}

	/**
	 * This getter method returns the name of the player.
	 * @return name the name of the player
	 */
	public S getName() {
		return name;
	}

	/**
	 * This setter method sets the name of the player.
	 * @param name the new name of the player.
	 */
	public void setName(S name) {
		this.name = name;
	}

	/**
	 * The getter method which returns the score of a player.
	 * @return score the score of a player
	 */
	public N getScore() {
		return score;
	}

	/**
	 *  A setter method which sets the score of a player
	 * @param score the new score of the player
	 */
	public void setScore(N score) {
		this.score = score;
	}

	@Override
	/**
	 * Overrides the to-string method of this class, which 
	 * returns a string as a representation of this class. 
	 */
	public String toString() {
		return this.name + ",  " + this.score;
	}
}
