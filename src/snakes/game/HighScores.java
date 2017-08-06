package snakes.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HighScores {

	public List<HS> scores = new ArrayList<HS>();
	private static String FILENAME = "highScores.txt";

	public HighScores(List<HS> score) {
		this.scores = score;
		writeHighScores(scores);
	}

	public HighScores() {

	}

	public void setScores(List<HS> scores) {
		this.scores = scores;
		writeHighScores(scores);
	}

	public static void writeHighScores(List<HS> scores) {
		try {
			PrintWriter out = new PrintWriter(FILENAME);
			for (int i = 0; i < scores.size(); i++) {
				out.println(scores.get(i).getName() + "," + scores.get(i).getScore());

			}

			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File containing high scores not found.");
		}

	}

	public static List<HS> getHighScores() {
		List<HS> name = new ArrayList<HS>();
		String line = "1";
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(FILENAME));
			try {
				while (!(line == null)) {
					line = br.readLine();
					if (!(line == null)) {
						name.add(new HS(line.substring(0, line.indexOf(",")),
								Integer.parseInt(line.substring(line.indexOf(",") + 1))));
					}
				}

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
}
