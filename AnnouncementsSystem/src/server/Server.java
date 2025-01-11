package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	ServerSocket serverSocket;

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
