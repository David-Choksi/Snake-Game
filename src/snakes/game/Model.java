package snakes.game;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Model {

	public static final String UPDIRECTION = "images/up.png";
	public static final String DOWNDIRECTION = "images/down.png";
	public static final String LEFTDIRECTION = "images/left.png";
	public static final String RIGHTDIRECTION = "images/right.png";
	public static final String BODY = "images/body.png";
	public static final String BODYUP = "images/bodyup.png";
	public static final int NUMBER_OF_FOOD = 1;
	public static final int NUMBER_OF_DIE = 5;
	public static final int SNAKE_SPEED = 100;
	public static int SNAKE_SPEED_TIME = SNAKE_SPEED;
	public static int TIME_TO_SPEED_UP = 10; // in seconds
	public static int SPEED_TIME_DECREASE = -5;
	private static String PLAYERNAME1 = "Player 1";
	private static String PLAYERNAME2 = "Player 2";
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

	public Model() {

	}

	public void showHighScores() {
		List<HS> scores = HighScores.getHighScores();
		String display = "";

		for (int i = 0; i < scores.size(); i++) {
			display += "(" + (i + 1) + ")   " + scores.get(i) + "\n";

		}
		JOptionPane.showMessageDialog(null, display, "HIGH SCORES", JOptionPane.PLAIN_MESSAGE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setHighScores(Snake snake) {

		List<HS> scores = HighScores.getHighScores();
		if (scores.size() < 10) {
			scores.add(new HS(snake.getPlayerName(), getScore(snake)));
		} else {

			if (scores.get(scores.size() - 1).getScore() < getScore(snake)) {
				scores.add(new HS(snake.getPlayerName(), getScore(snake)));
				Collections.sort(scores, new Comparator() {
					@Override
					public int compare(Object score1, Object score2) {
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

	public void newTwoPlayerGame() {
		player2 = new Snake(new RowCol(12, 1), new RowCol(12, 0), RIGHTDIRECTION, PLAYERNAME2);
		PLAYERNAME2 = view.promptForName(PLAYERNAME2);
		numberOfPlayers = 2;
		showSnake(player2);

		showSnakeBody(player2);
		newGame();
	}

	@SuppressWarnings("deprecation")
	public void newGame() {
		paused = false;
		gameStarted = true;
		player1 = new Snake(new RowCol(12, 38), new RowCol(12, 39), LEFTDIRECTION, PLAYERNAME1);
		if (numberOfPlayers == 2) {
			player2 = new Snake(new RowCol(12, 1), new RowCol(12, 0), RIGHTDIRECTION, PLAYERNAME2);
		}
		view.clear();
		view.randomFood(NUMBER_OF_FOOD);
		view.randomDie(NUMBER_OF_DIE);
		if (!(timer == null)) {
			timer.stop();
			snakeTimer.stop();
		}
		counter = 0;
		SNAKE_SPEED_TIME = SNAKE_SPEED;
		Runnable time = new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
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
		timer = new Thread(time);
		timer.start();

		Runnable snakeTime = new Runnable() {

			@Override
			public void run() {
				try {
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
		snakeTimer = new Thread(snakeTime);

		snakeTimer.start();

	}

	public void changeHeadToBody(Snake snake) {
		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);
		if (snake.getDirection() == UPDIRECTION || snake.getDirection() == DOWNDIRECTION) {
			File file = new File(BODYUP);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);
		} else {
			File file = new File(BODY);
			ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
			view.labels[snake.getRow()][snake.getCol()].setIcon(img);
		}
	}

	public void showSnakeGone(Snake snake) {

		view.labels[snake.removing().row()][snake.removing().col()].setText("");
		view.labels[snake.removing().row()][snake.removing().col()].setBackground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setForeground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setIcon(null);

	}

	public void showSnakeBody(Snake snake) {
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setText("S");
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setBackground(Color.RED);
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setForeground(Color.RED);
		File file = new File(BODY);
		ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
		view.labels[snake.getBody().get(0).row()][snake.getBody().get(0).col()].setIcon(img);
	}

	public void showSnake(Snake snake) {

		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);
		File file = new File(snake.getDirection());
		ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
		view.labels[snake.getRow()][snake.getCol()].setIcon(img);

	}

	public void setView(View view) {
		this.view = view;
		if (view.promptForMusic() == 0) {
			audio = new AudioFilePlayer();
		}
		String name = view.promptForName(PLAYERNAME1);
		if (!(name.equals(""))) {
			PLAYERNAME1 = name;
		}
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
				if (!(player2.getDirection().equals(LEFTDIRECTION) || player2.getDirection().equals(RIGHTDIRECTION))) {
					player2.changeDirection(LEFTDIRECTION);
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
				if (!(player2.getDirection().equals(LEFTDIRECTION) || player2.getDirection().equals(RIGHTDIRECTION))) {
					player2.changeDirection(RIGHTDIRECTION);
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
				if (!(player2.getDirection().equals(UPDIRECTION) || player2.getDirection().equals(DOWNDIRECTION))) {
					player2.changeDirection(UPDIRECTION);
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
				if (!(player2.getDirection().equals(UPDIRECTION) || player2.getDirection().equals(DOWNDIRECTION))) {
					player2.changeDirection(DOWNDIRECTION);
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