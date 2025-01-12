package main;

import gui.ServerGUI;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket serverSocket;

	public static void main(String[] ignoredArgs) {
		EventQueue.invokeLater(() -> {
			try {
				ServerGUI frame = new ServerGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		});
	}

	public ServerSocket createSocket(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		return serverSocket;
	}

	public void startConnectionLoop() {
		while (serverSocket != null) {
			try {
				new ServerThread(serverSocket.accept()).start();
			} catch (IOException e) {
				System.err.printf("[AVISO] Não foi possível conectar com um cliente: %s%n", e.getLocalizedMessage());
			}
		}
	}
}
