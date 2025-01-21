package main;

import gui.ServerWindow;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket serverSocket;

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
		if(serverSocket == null || serverSocket.isClosed())
			return;

		serverSocket.close();
	}
}
