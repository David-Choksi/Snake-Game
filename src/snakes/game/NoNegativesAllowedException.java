package snakes.game;

/**
 * An unchecked exception when handling unusual cases of negative values. 
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Kristiana
 *         Papajani
 *
 */
public class NoNegativesAllowedException extends RuntimeException {
	
	/**
	 * A default constructor which calls upon the parent's class constructor.
	 */
	public NoNegativesAllowedException(){
		super ("No negatives are valid here.");
	}
	
	/**
	 * A parameterized constructor which calls upon the parent's class constructor.
	 * @param message a string to alert the user
	 */
	public NoNegativesAllowedException(String message){
		super (message);
	}

}
