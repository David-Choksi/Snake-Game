package snakes.game;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * Adhering to the MVC software architecture, this is the model; the model class separates
 * the logic of a program from the rest of the user interface. It manages the data and fundamental 
 * behaviors of a program. it is the data and data-management portion of the program. 
 * 
 * Specifically applying to this instance, the model takes care of...
 * 
 ******************HAVE TO FINISH COMMENT HERE******************
 * 
 * 
 * @author Jason Skinner, Leroy Nguyen, Dawood Choksi, Alessa Ivascu, Christiana Papajani
 *
 */
public class Model {

	//This handles all of the pictures of the snake body and head as it moves around the game.
	//**************************************************************************
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
	//**************************************************************************
	
	//This takes care of constants so there are no magic numbers or strings being used within code. 
	//************************************************************************
	public static final int NUMBER_OF_FOOD = 1;
	public static int NUMBER_OF_DIE = 5;
	public static int levelNumber = 1;
	public static final int SNAKE_SPEED = 100;
	public static int SNAKE_SPEED_TIME = SNAKE_SPEED;
	public static int TIME_TO_SPEED_UP = 7; // in seconds
	public static int SPEED_TIME_DECREASE = -5;
	private static String PLAYERNAME1 = "Player 1";
	private static String PLAYERNAME2 = "Player 2";
	//************************************************************************
	
	//This list takes care of other fields which are required by the Model class and all of its instances.
	//************************************************************************
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
	//************************************************************************


	/**
	 * This is an empty Model class constructor.
	 * It is here to adhere to the MVC software architecture.
	 */
	public Model() {
		
	}

	/**
	 * This class gets the High Scores from the HighScores class
	 * and displays them in a pop-up message dialog.
	 */
	public void showHighScores() {
		List<HS> scores = HighScores.getHighScores();
		String display = "";

		//This loops through the scores list and displays them in a clean, concise, manner.
		for (int i = 0; i < scores.size(); i++) {
			display += "(" + (i + 1) + ")   " + scores.get(i) + "\n";

		}
		JOptionPane.showMessageDialog(null, display, "HIGH SCORES", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * This sets the high scores which are found in a file, and sorts them from
	 * highest score to lowest score. If there are more than 10 scores, the list 
	 * takes the lowest score and erases it, making space for the newest high score. 
	 * @param snake a snake object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setHighScores(Snake snake) {

		//The list of scores are held in a List object. 
		List<HS> scores = HighScores.getHighScores();
		if (scores.size() < 10) {
			scores.add(new HS(snake.getPlayerName(), getScore(snake)));
		} else {
			
			//If the list is larger than it takes the smallest score, removes it, and adds the newest high score.
			if (scores.get(scores.size() - 1).getScore() < getScore(snake)) {
				scores.add(new HS(snake.getPlayerName(), getScore(snake)));
				Collections.sort(scores, new Comparator() {
					@Override
					public int compare(Object score1, Object score2) {
						
						//So does this have to be done or is it already done??
						return (((HS) score2).score - (((HS) score1).score)); // reverse
																				// order
																				// of
																				// scores
					}

				});
				scores.remove(scores.size() - 1);
			}

		}
		Collections.sort(scores, new Comparator() {
			@Override
			public int compare(Object score1, Object score2) {
				return (((HS) score2).score - (((HS) score1).score)); // reverse
																		// order
																		// of
																		// scores
			}

		});

		HighScores.writeHighScores(scores);

	}

	/**
	 * This starts and sets up the game for two players. The second player is placed 
	 * across from the first player, and a new game is set up for both players.
	 */
	public void newTwoPlayerGame() {
		player2 = new Snake(new RowCol(12, 1), new RowCol(12, 0), P2RIGHTDIRECTION, PLAYERNAME2);
		PLAYERNAME2 = view.promptForName(PLAYERNAME2);
		numberOfPlayers = 2;
		showSnake(player2);
		showSnakeBody(player2);
		newGame();
	}

	/**
	 * This method sets up the new game, first erasing everything in the game board, then 
	 * going back to level one, setting all of the settings back to their default original, and 
	 * setting the players back to their original positions.
	 */
	@SuppressWarnings("deprecation")
	public void newGame() {
		//This sets the difficulty back to the beginning, as well as clears the gameboard.
		levelNumber = 1;
		view.clear();

		//Sets a random number of food as well as number of lethal objects, or "die"
		view.randomFood(NUMBER_OF_FOOD);
		view.randomDie(NUMBER_OF_DIE);
		//The game is not paused, and started.
		paused = false;
		gameStarted = true;

		//The snakes are placed in their rightful places. 
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
			 * This is the run method that takes care of the timer of the game, which shows at 
			 * the title of the menu.
			 */
			@Override
			public void run() {

				while (true) {
					try {
						//As a counter ticks ahead, there is a timer in the game that shows the 
						//update of the timer in the menu. It ticks every second.
						counter++;
						view.setTitle("Time:  " + counter);
						if (counter % TIME_TO_SPEED_UP == 0) {
							SNAKE_SPEED_TIME += (SPEED_TIME_DECREASE);
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		};
		//A new thread is run for the time of the game.
		timer = new Thread(time);
		timer.start();

		Runnable snakeTime = new Runnable() {

			@Override
			/**
			 * This is the run method as the snake game runs. This takes care of the snakes and 
			 * how they are shown on the game board. 
			 */
			public void run() {
				try {
					//This takes care of the movement of the snake, as well as the 
					//visualizing the snakes head and body on the game board.
					changeHeadToBody(player1);
					showSnake(player1);
					showSnakeBody(player1);
					if (numberOfPlayers == 2) {
						changeHeadToBody(player2);
						showSnake(player2);
						showSnakeBody(player2);
					}
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					try {
						//This is the same thing but for the second player.
						doneOnce = false;
						if (numberOfPlayers == 2) {
							changeHeadToBody(player2);
							player2.move();
							showSnakeGone(player2);
							showSnake(player2);
						}
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
						e.printStackTrace();
					}
				}

			}

		};
		//This creates and starts the thread which takes care of the snakes on the game board.
		snakeTimer = new Thread(snakeTime);

		snakeTimer.start();

	}

	/**
	 * This method takes care of the image of the snake as it moves across the game board. 
	 * As the snake moves, the head moves forward, and this takes care of moving the body
	 * in the position that the head just was in. 
	 * @param snake takes in a snake object
	 */
	public void changeHeadToBody(Snake snake) {
		
		//The background that the snake is set in is first set with some red color
		//just in case the image for the snake does not load through.
		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);
		
		//If the snake is heading up or down, the vertical picture of the snake's body is shown.
		if (snake.getDirection() == UPDIRECTION || snake.getDirection() == DOWNDIRECTION) {
			File file = new File(BODYUP);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);
		
		//If the snake is heading left or right, the sideways picture of the snake's body is shown.
		} else if (snake.getDirection() == LEFTDIRECTION || snake.getDirection() == RIGHTDIRECTION) {
			File file = new File(BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);
		
			//This is the same vertical picture logic for the snake but for player 2.
		} else if (snake.getDirection() == P2UPDIRECTION || snake.getDirection() == P2DOWNDIRECTION) {
			File file = new File(P2BODYUP);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);
			
			//This is the same horizontal picture logic for the snake but for player 2.
		} else if (snake.getDirection() == P2LEFTDIRECTION || snake.getDirection() == P2RIGHTDIRECTION) {
			File file = new File(P2BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);
			
		}
	}

	/**
	 * If  the snake is dead or for some reason the snake needs to be taken off
	 * the game board, this method erases it.
	 * @param snake a snake object
	 */
	public void showSnakeGone(Snake snake) {

		view.labels[snake.removing().row()][snake.removing().col()].setText("");
		view.labels[snake.removing().row()][snake.removing().col()].setBackground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setForeground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setIcon(null);

	}

	/**
	 * This method takes care of the movement of the snake's body as it moves across the game board.
	 * @param snake
	 */
	public void showSnakeBody(Snake snake) {
		//If the snake's body cannot show, a red block is shown in it's place.
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setText("S");
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setBackground(Color.RED);
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setForeground(Color.RED);
		
		//If the snake is heading up or down, the vertical picture of the snake's body is shown.
		if (snake.getDirection() == RIGHTDIRECTION || snake.getDirection() == LEFTDIRECTION) {
			File file = new File(BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setIcon(img);
			
			//If the snake is heading left or right, the sideways picture of the snake's body is shown.
		} else {
			File file = new File(P2BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setIcon(img);

		}

	}

	/**
	 * This method shows the snake's head in a specific direction it is heading.
	 * @param snake a snake object
	 */
	public void showSnake(Snake snake) {

		//If the snake image is not available, it shows a red background instead.
		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);
		
		//This is getting the image for the head.
		File file = new File(snake.getDirection());
		ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
		view.labels[snake.getRow()][snake.getCol()].setIcon(img);

	}

	/**
	 * 
	 * @param view a view object
	 */
	public void setView(View view) {
		this.view = view;
		if (view.promptForMusic() == 0) {
			audio = new AudioFilePlayer();
		}
		String name = view.promptForName(PLAYERNAME1);
		if (!(name == null)) {
			PLAYERNAME1 = name;
		}
		view.clear();
		showSnake(player1);
		showSnakeBody(player1);
	}

	public void leftPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(LEFTDIRECTION) || player1.getDirection().equals(RIGHTDIRECTION))) {
				player1.changeDirection(LEFTDIRECTION);
			}
		} else {
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2LEFTDIRECTION)
						|| player2.getDirection().equals(P2RIGHTDIRECTION))) {
					player2.changeDirection(P2LEFTDIRECTION);
				}
			}
		}

		doneOnce = true;
	}

	public void rightPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(LEFTDIRECTION) || player1.getDirection().equals(RIGHTDIRECTION))) {
				player1.changeDirection(RIGHTDIRECTION);

			}
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

	public void upPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(UPDIRECTION) || player1.getDirection().equals(DOWNDIRECTION))) {
				player1.changeDirection(UPDIRECTION);
			}
		} else {
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2UPDIRECTION) || player2.getDirection().equals(P2DOWNDIRECTION))) {
					player2.changeDirection(P2UPDIRECTION);
				}
			}
		}
		doneOnce = true;
	}

	public void downPressed(String snake) {
		if (doneOnce) {
			try {
				Thread.sleep(SNAKE_SPEED_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (snake.equals(Controller.P1)) {
			if (!(player1.getDirection().equals(UPDIRECTION) || player1.getDirection().equals(DOWNDIRECTION))) {
				player1.changeDirection(DOWNDIRECTION);

			}
		} else {
			if (numberOfPlayers == 2) {
				if (!(player2.getDirection().equals(P2UPDIRECTION) || player2.getDirection().equals(P2DOWNDIRECTION))) {
					player2.changeDirection(P2DOWNDIRECTION);
				}
			}
		}
		doneOnce = true;
	}

	public int getScore(Snake snake) {
		return (snake.getLength() - 2) * counter;

	}

	@SuppressWarnings("deprecation")
	public void f2Pressed() {
		if (!paused) {
			snakeTimer.suspend();
			timer.suspend();
		}
		int check = JOptionPane.showOptionDialog(view, "Do you want to start a new game?", null, 0, 0, null, null,
				"New Game");
		if (check == 0) {
			newGame();
		} else {
			snakeTimer.resume();
			timer.resume();
		}
	}

	@SuppressWarnings("deprecation")
	public void spacePressed() {
		if (!paused) {
			snakeTimer.suspend();
			timer.suspend();
			paused = true;
		} else {
			if (gameStarted) {
				snakeTimer.resume();
				timer.resume();
				paused = false;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void checkFinish(Snake snake) {
		boolean check = true;

		if (!(snake.checkMove())) {

			timer.suspend();
			JOptionPane.showMessageDialog(null,
					snake.getPlayerName() + " YOU LOSE.  Out of bounds.  Your score is: " + getScore(snake),
					"GAME OVER", JOptionPane.PLAIN_MESSAGE);
			setHighScores(snake);
			snakeTimer.suspend();

		} else {
			if (View.foodLocation.contains(new RowCol(snake.getRow(), snake.getCol()))) {
				snake.addLength();
				View.foodLocation.remove(new RowCol(snake.getRow(), snake.getCol()));
				view.randomFood(1);
				if (numberOfPlayers == 1) {
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
			} else if (View.dieLocation.contains(new RowCol(snake.getRow(), snake.getCol()))) {
				timer.stop();
				JOptionPane.showMessageDialog(null,
						snake.getPlayerName() + " YOU LOSE.  You hit poison.  Your score is: " + getScore(snake),
						"GAME OVER", JOptionPane.PLAIN_MESSAGE);
				setHighScores(snake);
				snakeTimer.stop();

			}

			else {
				if (numberOfPlayers == 2) {
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

	public AudioFilePlayer getAudio() {
		return audio;
	}

	public void setAudio(AudioFilePlayer audio) {
		this.audio = audio;
	}

}