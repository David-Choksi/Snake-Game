package snakes.game;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Networking {
	private static final int SOCKET_BUFFER_SIZE = 4 * 1024;

	private Networking() {

	}

	public static void runServer() {
		// network listener
		Runnable www = new Runnable() {
			@Override
			public void run() {
				try {
					ServerSocket serverSocket = new ServerSocket(88);
					while (true) {
						Socket clientSocket = serverSocket.accept();
						PrintWriter outWeb = new PrintWriter(clientSocket.getOutputStream(), true);
						BufferedInputStream inWeb = new BufferedInputStream(clientSocket.getInputStream());

						// read all input from socket to string
						byte[] buffer = new byte[SOCKET_BUFFER_SIZE];
						String webText = "";
						for (;;) {
							int read = inWeb.read(buffer);
							if (read == -1)
								break;
							String chunk = new String(buffer);
							webText += chunk;
							if (read < buffer.length)
								break;
						}

						// send output to browser
						String respx = "";
						respx += "<html>\n";
						respx += "<body>\n";
						respx += "   <h1>The High Scores are:  </h1>\n";
						List<HS> highScores = Model.HighScores.getHighScores();
						for (int i = 0; i < highScores.size(); i++) {
							respx += "(" + (i + 1) + ")   " + highScores.get(i) + "<br> \n";
						}
						respx += "   </body>\n";
						respx += "</html>\n";

						String resp = "";
						resp += "HTTP/1.1 200 OK\n";
						resp += "Content-Length: " + respx.length() + "\n";
						resp += "Content-Type: text/html\n";
						resp += "\n";
						resp += respx;
						outWeb.print(resp);

						// end of read/write = close web streams and sockets
						outWeb.close();
						inWeb.close();
						clientSocket.close();

						// log web input/output to a file
						PrintWriter log = new PrintWriter(new FileWriter("log.txt", true));
						log.println(webText);
						log.println("==========");
						log.println(resp);
						log.println("==========");
						log.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread wwwThread = new Thread(www);
		wwwThread.start();
	}

}
