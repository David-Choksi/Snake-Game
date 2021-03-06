package snakes.game;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Adhering to the MVC software architecture, this is the model; the model class
 * separates the logic of a program from the rest of the user interface. It
 * manages the data and fundamental behaviors of a program. it is the data and
 * data-management portion of the program.
 * 
 * Specifically applying to this instance, the model takes care of the backbone of the
 * rest of the game. It puts together all of the logic which handles the highscores, 
 * movement of the snake game, as well as resetting and setting up the game board,
 * to name a few.
 * 
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Kristiana
 *         Papajani
 *
 */
public class Model {

	// This handles all of the pictures of the snake body and head as it moves
	// around the game.
	// **************************************************************************
	public static final String UPDIRECTION = "images/up.png";
	public static final String DOWNDIRECTION = "images/down.png";
	public static final String LEFTDIRECTION = "images/left.png";
	public static final String RIGHTDIRECTION = "images/right.png";
	public static final String BODY = "images/body.png";
	public static final String BODYUP = "images/bodyup.png";
	public static final String P2UPDIRECTION = "images/up_red.png";
	public static final String P2DOWNDIRECTION = "images/down_red.png";
	public static final String P2LEFTDIRECTION = "images/left_red.png";
	public static final String P2RIGHTDIRECTION = "images/right_red.png";
	public static final String P2BODY = "images/body_red.png";
	public static final String P2BODYUP = "images/bodyup_red.png";
	// **************************************************************************

	// This takes care of constants so there are no magic numbers or strings
	// being used within code.
	// ************************************************************************
	public static final int NUMBER_OF_FOOD = 1;
	public static int NUMBER_OF_DIE = 5;
	public static int levelNumber = 1;
	public static final int SNAKE_SPEED = 100;
	public static int SNAKE_SPEED_TIME = SNAKE_SPEED;
	public static int TIME_TO_SPEED_UP = 7; // in seconds
	public static int SPEED_TIME_DECREASE = -5;
	private static String PLAYERNAME1 = "Player 1";
	private static String PLAYERNAME2 = "Player 2";
	// ************************************************************************

	// This list takes care of other fields which are required by the Model
	// class and all of its instances.
	// ************************************************************************
	private View view;
	private Snake player1 = new Snake(new RowCol(12, 38), new RowCol(12, 39), LEFTDIRECTION, PLAYERNAME1);
	private Snake player2;
	private Thread timer;
	private Thread snakeTimer;
	private boolean paused = true;
	private boolean gameStarted = false;
	private AudioFilePlayer audio;
	private int numberOfPlayers = 1;
	private boolean doneOnce = false;
	private int counter = 0;
	// ************************************************************************

	/**
	 * This is an empty Model class constructor. It is here to properly instantiate any 
	 * objects of the Model class.
	 */
	public Model() {

	}

	/**
	 * This class gets the High Scores from the HighScores class and displays
	 * them in a pop-up message dialog.
	 */

	public void showHighScores() {
		List<HS> scores = HighScores.getHighScores();
		String display = "";

		// This loops through the scores list and displays them in a clean,
		// concise, manner.
		for (int i = 0; i < scores.size(); i++) {
			display += "(" + (i + 1) + ")   " + scores.get(i) + "\n";

		}
		JOptionPane.showMessageDialog(null, display, "HIGH SCORES", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * This sets the high scores which are found in a file, and sorts them from
	 * highest score to lowest score. If there are more than 10 scores, the list
	 * takes the lowest score and erases it, making space available, for the
	 * newest high score.
	 * 
	 * @param snake
	 *            a snake object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setHighScores(Snake snake) {

		// The list of scores are held in a List object.
		List<HS> scores = HighScores.getHighScores();
		if (scores.size() < 10) {
			scores.add(new HS(snake.getPlayerName(), getScore(snake)));
		} else {

			// If the list is larger then it takes the smallest score, removes
			// it, and adds the newest high score.
			if (Integer.valueOf((scores.get(scores.size() - 1).getScore()).toString()) < getScore(snake)) {
				scores.add(new HS(snake.getPlayerName(), getScore(snake)));
				Collections.sort(scores, new Comparator() {
					@Override
					public int compare(Object score1, Object score2) {

						Integer score1Int = Integer.valueOf((((HS) score1).score).toString());
						Integer score2Int = Integer.valueOf((((HS) score2).score).toString());

						// So does this have to be done or is it already done??
						return (score2Int - score1Int); // reverse
														// order
														// of
														// scores
					}

				});
				scores.remove(scores.size() - 1);
			}

		}
		scores.sort((score1, score2) -> Integer.compare(Integer.valueOf((((HS) score2).score).toString()), Integer.valueOf((((HS) score1).score).toString())));
//		
//		Collections.sort(scores, new Comparator(){
//			@Override
//
//			public int compare(Object score1, Object score2) {
//
//				Integer score1Int = Integer.valueOf((((HS) score1).score).toString());
//				Integer score2Int = Integer.valueOf((((HS) score2).score).toString());
//
//				return (score2Int - score1Int); // reverse
//												// order
//												// of
//												// scores
//			}
//
//		}
//		);

		HighScores.writeHighScores(scores);

	}

	/**
	 * This starts and sets up the game for two players. The second player is
	 * placed across from the first player, and a new game is set up for both
	 * players.
	 */
	public void newTwoPlayerGame() {
		player2 = new Snake(new RowCol(12, 1), new RowCol(12, 0), P2RIGHTDIRECTION, PLAYERNAME2);
		PLAYERNAME2 = view.promptForName(PLAYERNAME2);
		numberOfPlayers = 2;
		showSnake(player2);
		showSnakeBody(player2);
		newGame();
	}

	public void resetNumberOfPlayers() {
		numberOfPlayers = 1;
	}

	/**
	 * This method sets up the new game, first erasing everything in the game
	 * board, then going back to level one, setting all of the settings back to
	 * their default original, and setting the players back to their original
	 * positions.
	 */
	@SuppressWarnings("deprecation")
	public void newGame() {
		// This sets the difficulty back to the beginning, as well as clears the
		// gameboard.
		levelNumber = 1;
		view.clear();

		// Sets a random number of food as well as number of lethal objects, or
		// "die"
		view.randomFood(NUMBER_OF_FOOD);
		view.randomDie(NUMBER_OF_DIE);
		// The game is not paused, and started.
		paused = false;
		gameStarted = true;

		// The snakes are placed in their rightful places.
		player1 = new Snake(new RowCol(12, 38), new RowCol(12, 39), LEFTDIRECTION, PLAYERNAME1);
		if (numberOfPlayers == 2) {
			player2 = new Snake(new RowCol(12, 1), new RowCol(12, 0), P2RIGHTDIRECTION, PLAYERNAME2);
		}
		if (!(timer == null)) {
			timer.stop();
			snakeTimer.stop();
		}
		counter = 0;
		SNAKE_SPEED_TIME = SNAKE_SPEED;
		Runnable time = new Runnable() {

			/**
			 * This is the run method that takes care of the timer of the game,
			 * which shows at the title of the menu.
			 */
			@Override
			public void run() {

				while (true) {
					try {
						// As a counter ticks ahead, there is a timer in the
						// game that shows the
						// update of the timer in the menu. It ticks every
						// second.
						counter++;
						view.setTitle("Time:  " + counter);
						if (counter % TIME_TO_SPEED_UP == 0) {
							SNAKE_SPEED_TIME += (SPEED_TIME_DECREASE);
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("Thread was interrupted.");
					}
				}

			}

		};
		// A new thread is run for the time of the game.
		timer = new Thread(time);
		timer.start();

		Runnable snakeTime = new Runnable() {

			@Override
			/**
			 * This is the run method as the snake game runs. This takes care of
			 * the snakes and how they are shown on the game board.
			 */
			public void run() {
				try {
					// This takes care of the movement of the snake, as well
					// visualizing the snakes head and body on the game board.
					changeHeadToBody(player1);
					showSnake(player1);
					showSnakeBody(player1);

					// If there are two players, take care of showing the second
					// player.
					if (numberOfPlayers == 2) {
						changeHeadToBody(player2);
						showSnake(player2);
						showSnakeBody(player2);
					}
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					System.out.println("Thread was interrupted.");
				}
				while (true) {
					try {
						// This is the moving the second snake player
						doneOnce = false;
						if (numberOfPlayers == 2) {
							changeHeadToBody(player2);
							player2.move();
							showSnakeGone(player2);
							showSnake(player2);
						}
						// This takes care of movement for the first player
						changeHeadToBody(player1);
						player1.move();
						showSnakeGone(player1);
						showSnake(player1);
						Thread.sleep(Model.SNAKE_SPEED_TIME);
						checkFinish(player1);

						if (numberOfPlayers == 2) {
							checkFinish(player2);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("Thread was interrupted.");
					}
				}

			}

		};
		// This creates and starts the thread which takes care of the snakes on
		// the game board.
		snakeTimer = new Thread(snakeTime);

		snakeTimer.start();

	}

	/**
	 * This method takes care of the image of the snake as it moves across the
	 * game board. As the snake moves, the head moves forward, and this takes
	 * care of moving the body in the position that the head just was in.
	 * 
	 * @param snake takes in a snake object
	 */
	public void changeHeadToBody(Snake snake) {

		// The background that the snake is set in is first set with some red color
		// just in case the image for the snake does not load through. 
		//EXAMPLE OF AN ARRAY
		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);

		// If the snake is heading up or down, the vertical picture of the
		// snake's body is shown.
		if (snake.getDirection() == UPDIRECTION || snake.getDirection() == DOWNDIRECTION) {
			File file = new File(BODYUP);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);

			// If the snake is heading left or right, the sideways picture of
			// the snake's body is shown.
		} else if (snake.getDirection() == LEFTDIRECTION || snake.getDirection() == RIGHTDIRECTION) {
			File file = new File(BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);

			// This is the same vertical picture logic for the snake but for
			// player 2.
		} else if (snake.getDirection() == P2UPDIRECTION || snake.getDirection() == P2DOWNDIRECTION) {
			File file = new File(P2BODYUP);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);

			// This is the same horizontal picture logic for the snake but for
			// player 2.
		} else if (snake.getDirection() == P2LEFTDIRECTION || snake.getDirection() == P2RIGHTDIRECTION) {
			File file = new File(P2BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);

		}
	}

	/**
	 * If the snake is dead or for some reason the snake needs to be taken off
	 * the game board, this method erases it.
	 * 
	 * @param snake a snake object
	 */
	public void showSnakeGone(Snake snake) {

		view.labels[snake.removing().row()][snake.removing().col()].setText("");
		view.labels[snake.removing().row()][snake.removing().col()].setBackground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setForeground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setIcon(null);

	}

	/**
	 * This method takes care of the movement of the snake's body as it moves
	 * across the game board.
	 * 
	 * @param snake a snake object
	 */
	public void showSnakeBody(Snake snake) {
		// If the snake's body cannot show, a red block is shown in it's place.
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setText("S");
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setBackground(Color.RED);
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setForeground(Color.RED);

		// If the snake is heading up or down, the vertical picture of the
		// snake's body is shown.
		if (snake.getDirection() == RIGHTDIRECTION || snake.getDirection() == LEFTDIRECTION) {
			File file = new File(BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setIcon(img);

			// If the snake is heading left or right, the sideways picture of
			// the snake's body is shown.
		} else {
			File file = new File(P2BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setIcon(img);

		}

	}

	/**
	 * This method shows the snake's head in a specific direction it is heading.
	 * 
	 * @param snake a snake object
	 */
	public void showSnake(Snake snake) {

		// If the snake image is not available, it shows a red background
		// instead.
		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);

		// This is getting the image for the head.
		File file = new File(snake.getDirection());
		ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
		view.labels[snake.getRow()][snake.getCol()].setIcon(img);

	}

	/**
	 * This sets up the game board for the player. It take's the view, sets it,
	 * prompts the player for music as well as the player's name, then resets
	 * the game board and places the fresh snake into it.
	 * 
	 * @param view a view object
	 */
	public void setView(View view) {
		this.view = view;
		// Ask's the player if they'd like to hear some music.
		if (view.promptForMusic() == 0) {
			AudioFilePlayer.runner();
		}
		// Ask's the player for the name
		String name = view.promptForName(PLAYERNAME1);
		if (!(name == null)) {
			PLAYERNAME1 = name;
		}
		// Resets the view and places the snake back on the board.
		view.clear();
		showSnake(player1);
		showSnakeBody(player1);
	}

	/**
	 * Manages the movement of the players' snakes. Depending on which direction the snake is
	 * heading it will change it to the left direction.
	 * 
	 * @param snake an instance of the snake class
	 */
	public void leftPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Thread was interrupted.");
			}
		}
		//If the snake is moving in any other direction other than the right direction, it will change to the left direction.
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(LEFTDIRECTION) || player1.getDirection().equals(RIGHTDIRECTION))) {
				player1.changeDirection(LEFTDIRECTION);
			}
		} else {
			
			//This takes care of the second player.
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2LEFTDIRECTION)
						|| player2.getDirection().equals(P2RIGHTDIRECTION))) {
					player2.changeDirection(P2LEFTDIRECTION);
				}
			}
		}

		doneOnce = true;
	}

	/**
	 * Manages the movement of the players' snakes. Depending on which direction the snake is
	 * heading it will change it to the right direction. 
	 * @param snake a snake object
	 */
	public void rightPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Thread was interrupted.");
			}
		}
		//If the snake is moving in any other direction other than the left or the right direction, 
		//it will change to the right direction.
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(LEFTDIRECTION) || player1.getDirection().equals(RIGHTDIRECTION))) {
				player1.changeDirection(RIGHTDIRECTION);

			}
			//This takes care of the second player and its right direction.
		} else {
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2LEFTDIRECTION)
						|| player2.getDirection().equals(P2RIGHTDIRECTION))) {
					player2.changeDirection(P2RIGHTDIRECTION);
				}
			}
		}
		doneOnce = true;
	}

	/**
	 * Manages the movement of the players' snakes. Depending on which direction the snake is
	 * heading it will change it to the up direction. 
	 * @param snake a snake object
	 */
	public void upPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Thread was interrupted.");
			}
		}
		//If the snake is moving in any other direction other than the up or the down direction, 
		//it will change to the up direction.
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(UPDIRECTION) || player1.getDirection().equals(DOWNDIRECTION))) {
				player1.changeDirection(UPDIRECTION);
			}
		} else {
			
			//The same but for the second player.
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2UPDIRECTION) || player2.getDirection().equals(P2DOWNDIRECTION))) {
					player2.changeDirection(P2UPDIRECTION);
				}
			}
		}
		doneOnce = true;
	}

	/**
	 * Manages the movement of the players' snakes. Depending on which direction the snake is
	 * heading it will change it to the down direction. 
	 * @param snake a snake object
	 */
	public void downPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Thread was interrupted.");
			}
		}
		
		//If the snake is moving in any other direction other than the up or the down direction, 
		//it will change to the down direction.
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(UPDIRECTION) || player1.getDirection().equals(DOWNDIRECTION))) {
				player1.changeDirection(DOWNDIRECTION);

			}
		} else {
			//This is the same but for the second player.
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2UPDIRECTION) || player2.getDirection().equals(P2DOWNDIRECTION))) {
					player2.changeDirection(P2DOWNDIRECTION);
				}
			}
		}
		doneOnce = true;
	}

	/**
	 * Gets the score for the snake object. 
	 * @precondition that the snake's length - 2 multiplied by the counter doesn't reach the max value for integers.
	 * @precondition that there are no negative values being used
	 * @postcondition returns an int value representing the score.
	 * @param snake a snake object
	 * @return int an int representing the length of the snake + the counter of the game time.
	 */
	public int getScore(Snake snake){
		
		int score = (snake.getLength() - 2) * counter;
		
		//If the score is too big, make it an arbitrarily large number.
		if (score > 10000000 || counter > 100000 || snake.getLength() > 10000){
			score = 1000000000;
		}
		
		//If any of the values being used are negative, throw an exception.
		if (score < 0 || snake.getLength() < 0 || counter < 0){
			throw new NoNegativesAllowedException("There cannot be negative values");
		}
		
		return score;

	} 
	
	/**
	 * A getter method for the counter field.
	 * @return int a counter for the game's time.
	 */
	public int getCounter(){
		return this.counter; 
	}
	
	/**
	 * A setter method for the game's time, counter.
	 * @param newCount a new value to set the count. 
	 */
	public void setCounter(int newCount){
		 this.counter = newCount;
	}

	/**
	 * This is the button to start a new game. If a new game is agreed to start, then the old game is stopped,
	 * the timers are reset, and then a new game is started.
	 */
	@SuppressWarnings("deprecation")
	public void f2Pressed() {
		
		//If the game is not paused, stop the game.
		if (!paused) {
			snakeTimer.suspend();
			timer.suspend();
		}
		
		//This is the prompt for a new game
		int check = JOptionPane.showOptionDialog(view, "Do you want to start a new game?", null, 0, 0, null, null,
				"New Game");
		if (check == 0) {
			newGame();
			
			//If the game is paused, resume the game
		} else {
			snakeTimer.resume();
			timer.resume();
		}
	}

	/**
	 * This is the pause button for the game. If f2 is pressed, it pauses the game. It works by pausing 
	 * the timer that moves the snake as well as the timer in the game itself.
	 */
	@SuppressWarnings("deprecation")
	public void spacePressed() {
		
		//If the game is not paused, pause the game.
		if (!paused) {
			snakeTimer.suspend();
			timer.suspend();
			paused = true;
		} else {
			
			//If the game is paused, resume the game.
			if (gameStarted) {
				snakeTimer.resume();
				timer.resume();
				paused = false;
			}
		}
	}

	/**
	 * This method checks where the snake is on the game board and acts according to 
	 * its status. If the snake is over the bounds of the game, it's game over.
	 * If the snake is over a food object, it grows larger. If the snake hits a 
	 * certain length, then the next level is loaded. If the snake hits poison, it's game 
	 * over as well.
	 * @param snake a snake object
	 */
	@SuppressWarnings("deprecation")
	public void checkFinish(Snake snake) {
		boolean check = true;

		//If the snake goes over the bounds of the game, the game is over.
		if (!(snake.checkMove())) {

			timer.suspend();
			JOptionPane.showMessageDialog(null,
					snake.getPlayerName() + " YOU LOSE.  Out of bounds.  Your score is: " + getScore(snake),
					"GAME OVER", JOptionPane.PLAIN_MESSAGE);
			setHighScores(snake);
			snakeTimer.suspend();

			//If the snake's head goes over a food object, the snake grows by one length.
		} else {
			if (View.foodLocation.contains(new RowCol(snake.getRow(), snake.getCol()))) {
				snake.addLength();
				View.foodLocation.remove(new RowCol(snake.getRow(), snake.getCol()));
				view.randomFood(1);
				
				//This occurs if there is only 1 player.
				if (numberOfPlayers == 1) {
					
					//If the snake's length hits 10, the level's move on to a new one,
					//with more poison objects on the game board.
					if ((player1.getLength() - 1) % 10 == 0) {
						JOptionPane.showMessageDialog(null,
								"Congratulations " + player1.getPlayerName() + " you have completed level "
										+ levelNumber + ".  Press OK to continue to next level.",
								"LEVEL UP", JOptionPane.PLAIN_MESSAGE);
						levelNumber++;
						NUMBER_OF_DIE = 3;
						view.randomDie(NUMBER_OF_DIE);
					}
				}
				
				//If the snakes hit poison, then the game is over.
			} else if (View.dieLocation.contains(new RowCol(snake.getRow(), snake.getCol()))) {
				timer.stop();
				JOptionPane.showMessageDialog(null,
						snake.getPlayerName() + " YOU LOSE.  You hit poison.  Your score is: " + getScore(snake),
						"GAME OVER", JOptionPane.PLAIN_MESSAGE);
				setHighScores(snake);
				snakeTimer.stop();

			}

			else {
				//If there are two players
				if (numberOfPlayers == 2) {
					
					//If the players hit themselves or the other snake, then game over happens.
					if (snake.checkForSnake(player1, player2)) {
						timer.stop();
						JOptionPane.showMessageDialog(null, snake.getPlayerName()
								+ " YOU LOSE.  You hit the snake.  Your score is: " + getScore(snake), "GAME OVER",
								JOptionPane.PLAIN_MESSAGE);
						setHighScores(snake);
						snakeTimer.stop();

						check = false;
					} else if (check) {
						showSnakeGone(snake);
						showSnake(snake);
					}
				} else {
					if (snake.checkForSnake(player1)) {
						timer.stop();
						JOptionPane.showMessageDialog(null, snake.getPlayerName()
								+ " YOU LOSE.  You hit the snake.  Your score is: " + getScore(snake), "GAME OVER",
								JOptionPane.PLAIN_MESSAGE);
						setHighScores(snake);
						snakeTimer.stop();

						check = false;
					} else if (check) {
						showSnakeGone(snake);
						showSnake(snake);
					}
				}
			}

		}

	}

	/**
	 * This is a getter method for the audio file for the game.
	 * @return AudioFilePlayer a file that can play music!
	 */
	public AudioFilePlayer getAudio() {
		return audio;
	}

	/**
	 * This is a setter method for the audio file for the game.
	 * @param audio an audio file that has music!
	 */
	public void setAudio(AudioFilePlayer audio) {
		this.audio = audio;
	}

	// ******************************************************************
	// ******************************************************************
	// ******************************************************************
	
	//INNER CLASS HIGHSCORES

	/**
	 * The class that manages I/O of the high scores of the game.
	 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Christiana
     * Papajani
	 *
	 */
	public static final class HighScores {

		// Polymorphism is used here. The List class is the declared class, but the 
		//actual class is ArrayList.
		private static List<HS> scores = new ArrayList<HS>();
		private static String FILENAME = "highScores.txt";

		/**
		 * One of the constructors that takes in a list of the generic class
		 * type, HS.
		 * @param score
		 */
		private HighScores(List<HS> score) {
			this.scores = score;
			//Writes the highscores to a file.
			writeHighScores(scores);
		}

		/**
		 * Default constructor for HighScores.
		 */
		private HighScores() {

		}

		/**
		 * A setter method which sets the high scores of the game,
		 * then writes it onto a file.
		 * @param scores a list which holds all of the scores.
		 */
		private void setScores(List<HS> scores) {
			this.scores = scores;
			writeHighScores(scores);
		}

		/**
		 * A method which writes the high scores from a list onto a text file.
		 *  @param scores a list which holds all of the scores.
		 */
		private static void writeHighScores(List<HS> scores) {
			try {
				//Writes to a text file.
				PrintWriter out = new PrintWriter(FILENAME);
				
				//Goes through the arraylist which holds the scores and writes each to the file.
				for (int i = 0; i < scores.size(); i++) {
					out.println(scores.get(i).getName() + "," + scores.get(i).getScore());

				}
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File containing high scores not found.");
			}

		}

		/**
		 * Looks at the file which contains the high scores and reads it,
		 * then stores it in an arrayList. 
		 * @return List a list which contains HS, or also high score objects.
		 */
		@SuppressWarnings("rawtypes")
		public static List<HS> getHighScores() {
			
			List<HS> name = new ArrayList<HS>();

			//Tries to read off of the file which contains the high scores.
			try {
				BufferedReader br = new BufferedReader(new FileReader(FILENAME));
				try {
					name = getLines(br, name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("File not available.");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File containing high scores not found.");
			}
			return name;
		}

		/**
		 * AN EXAMPLE OF RECURSION
		 * 
		 * This recursive method walks through the lines in a file 
		 * and adds it into an ArrayList of type HS. It then returns that arrayList.
		 * 
		 * @param br the bufferedReader which reads the lines in the file
		 * @param name an ArrayList which holds the high scores.
		 * @return List an arrayList which holds the new high scores.
		 * @throws IOException an exception if there is an error in input/output
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static List<HS> getLines(BufferedReader br, List<HS> name) throws IOException {
			String line = br.readLine();
			if (!(line == null)) {
				name.add(new HS(line.substring(0, line.indexOf(",")),
						Integer.parseInt(line.substring(line.indexOf(",") + 1))));
				name = getLines(br, name);
			}
			return name;
		}
	}

}