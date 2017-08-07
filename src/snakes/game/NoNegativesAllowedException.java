package snakes.game;

public class NoNegativesAllowedException extends RuntimeException {
	
	public NoNegativesAllowedException(){
		super ("No negatives are valid here.");
	}
	
	public NoNegativesAllowedException(String message){
		super (message);
	}

}
