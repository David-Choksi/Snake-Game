package snakes.game;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Model {
	public static final String UPDIRECTION = "images/up.png";
	public static final String DOWNDIRECTION = "images/down.png";
	public static final String LEFTDIRECTION = "images/left.png";
	public static final String RIGHTDIRECTION = "images/right.png";
	public static final String BODY = "images/body.png";
	public static final int NUMBER_OF_FOOD = 1;
	public static final int NUMBER_OF_DIE = 5;
	public static final int SNAKE_SPEED = 100;
	public static int SNAKE_SPEED_TIME = SNAKE_SPEED;
	public static int TIME_TO_SPEED_UP = 45; // in seconds
	public static int SPEED_TIME_DECREASE = -10;
	private static String PLAYERNAME1 = "Player 1";
	private static String PLAYERNAME2 = "Player 2";
	private View view;
	private Snake player1 = new Snake(new RowCol(12, 38), new RowCol(12, 39), LEFTDIRECTION, PLAYERNAME1);
	private Snake player2 = new Snake(new RowCol(-10, -10), new RowCol(-1, -1), RIGHTDIRECTION, PLAYERNAME2);
	private Thread timer;
	private Thread snakeTimer;
	private boolean paused = true;
	private AudioFilePlayer audio;
	private int numberOfPlayers = 1;
	private boolean doneOnce = false;
	private int counter = 0;

	public Model() {

	}

	@SuppressWarnings("deprecation")
	public void newGame() {
		paused = false;
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
					if (numberOfPlayers == 2) {
						changeHeadToBody(player2);
						showSnake(player2);
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
		view.labels[snake.getRow()][snake.getCol()].setOpaque(true);
		view.labels[snake.getRow()][snake.getCol()].setBackground(Color.RED);
		view.labels[snake.getRow()][snake.getCol()].setForeground(Color.RED);
		File file = new File(BODY);
		ImageIcon img = new ImageIcon(file.getAbsolutePath().toString());
		view.labels[snake.getRow()][snake.getCol()].setIcon(img);

	}

	public void showSnakeGone(Snake snake) {

		view.labels[snake.removing().row()][snake.removing().col()].setText("");
		view.labels[snake.removing().row()][snake.removing().col()].setOpaque(false);
		view.labels[snake.removing().row()][snake.removing().col()].setBackground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setForeground(Color.BLUE);
		view.labels[snake.removing().row()][snake.removing().col()].setIcon(null);

	}

	public void showSnake(Snake snake) {

		view.labels[snake.getRow()][snake.getCol()].setText("S");
		view.labels[snake.getRow()][snake.getCol()].setOpaque(true);
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
		PLAYERNAME1 = view.promptForName();
		if (view.promptForTwoPlayer() == 0) {

			PLAYERNAME2 = view.promptForName();
			numberOfPlayers = 2;
			showSnake(player2);
		}
		showSnake(player1);
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
			if (!(player2.getDirection().equals(LEFTDIRECTION) || player2.getDirection().equals(RIGHTDIRECTION))) {
				player2.changeDirection(LEFTDIRECTION);
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
			if (!(player2.getDirection().equals(LEFTDIRECTION) || player2.getDirection().equals(RIGHTDIRECTION))) {
				player2.changeDirection(RIGHTDIRECTION);
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
			if (!(player2.getDirection().equals(UPDIRECTION) || player2.getDirection().equals(DOWNDIRECTION))) {
				player2.changeDirection(UPDIRECTION);
			}
		}
		doneOnce = true;
	}

	public int getScore(Snake snake) {
		return (snake.getLength() - 2) * counter;
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
			if (!(player2.getDirection().equals(UPDIRECTION) || player2.getDirection().equals(DOWNDIRECTION))) {
				player2.changeDirection(DOWNDIRECTION);
			}
		}
		doneOnce = true;
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
			snakeTimer.resume();
			timer.resume();
			paused = false;
		}
	}

	@SuppressWarnings("deprecation")
	public void checkFinish(Snake snake) {
		boolean check = true;

		if (!(snake.checkMove())) {

			timer.suspend();
			JOptionPane.showMessageDialog(null,
					snake.getPlayerName() + " YOU LOSE.CM  Your score is: " + getScore(snake), "GAME OVER",
					JOptionPane.PLAIN_MESSAGE);
			snakeTimer.suspend();

		} else {
			if (View.foodLocation.contains(new RowCol(snake.getRow(), snake.getCol()))) {
				snake.addLength();
				View.foodLocation.remove(new RowCol(snake.getRow(), snake.getCol()));
				view.randomFood(1);
			} else if (View.dieLocation.contains(new RowCol(snake.getRow(), snake.getCol()))) {
				timer.stop();
				JOptionPane.showMessageDialog(null,
						snake.getPlayerName() + " YOU LOSE.DL  Your score is: " + getScore(snake), "GAME OVER",
						JOptionPane.PLAIN_MESSAGE);

				snakeTimer.stop();

			}

			else {

				if (snake.checkForSnake(player1, player2)) {
					timer.stop();
					JOptionPane.showMessageDialog(null,
							snake.getPlayerName() + " YOU LOSE.SN  Your score is: " + getScore(snake), "GAME OVER",
							JOptionPane.PLAIN_MESSAGE);

					snakeTimer.stop();

					check = false;
				} else if (check) {
					showSnakeGone(snake);
					showSnake(snake);
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