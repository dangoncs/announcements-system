package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import entities.User;
import gui.ServerWindow;

public class Server {
	private final int port;
	private ServerSocket socket;
	private static final List<User> loggedInUsers = new ArrayList<>();

	public static void main(String[] ignoredArgs) {
		SwingUtilities.invokeLater(ServerWindow::new);
	}

	public Server(int port) {
		this.port = port;
	}

	public void startConnectionLoop() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			this.socket = serverSocket;

			while (!serverSocket.isClosed()) {
				try {
					new ServerThread(serverSocket.accept()).start();
				} catch (IOException e) {
					System.err.printf("[WARNING] Socket did not accept connection (%s)%n", e.getLocalizedMessage());
				}
			}
		}
	}

	public void closeSocket() {
		if (socket == null || socket.isClosed())
			return;

		try {
			socket.close();
		} catch (IOException e) {
			System.err.printf("[ERROR] Could not close gracefully: %s%n", e.getMessage());
			System.err.println("Exiting the program now.");
		}

		System.exit(0);
	}

	public static synchronized void addToLoggedInUsers(User user) {
		loggedInUsers.add(user);
	}

	public static synchronized void removeFromLoggedInUsers(User user) {
		loggedInUsers.remove(user);
	}

	public static List<User> getLoggedInUsers() {
		return loggedInUsers;
	}

	public int getPort() {
		return port;
	}
}
