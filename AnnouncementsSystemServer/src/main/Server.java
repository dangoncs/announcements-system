package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import entities.User;
import gui.ServerWindow;

public class Server {
	private ServerSocket serverSocket;
	private static List<User> loggedInUsers;

	public static void main(String[] ignoredArgs) {
		SwingUtilities.invokeLater(ServerWindow::new);
	}

	public void startConnectionLoop(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;

		while (serverSocket != null && !serverSocket.isClosed()) {
			try {
				new ServerThread(serverSocket.accept()).start();
			} catch (IOException e) {
				System.err.printf("[AVISO] Socket não aceitou conexão (%s)%n", e.getLocalizedMessage());
			}
		}
	}

	public void closeSocket() throws IOException {
		if (serverSocket == null || serverSocket.isClosed())
			return;

		serverSocket.close();
	}

	public static synchronized void addToLoggedInUsers(User user) {
		if (loggedInUsers == null)
			loggedInUsers = new ArrayList<>();

		loggedInUsers.add(user);
	}

	public static synchronized void removeFromLoggedInUsers(User user) {
		if (loggedInUsers == null)
			loggedInUsers = new ArrayList<>();

		loggedInUsers.remove(user);
	}

	public static List<User> getLoggedInUsers() {
		return loggedInUsers;
	}
}
