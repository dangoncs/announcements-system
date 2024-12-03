package server;

import server.gui.ServerGUI;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private final int port;
	private final ServerGUI serverGUI;
	private ServerSocket serverSocket;

	public Server(int port, ServerGUI serverGUI) {
		this.serverSocket = null;
		this.port = port;
		this.serverGUI = serverGUI;
		this.start();
	}
	
	public void start() {
		try {
			serverSocket = new ServerSocket(port);

			while(true) {
				try {
					new ServerThread(serverSocket.accept(), serverGUI).start();
				}
				catch(IOException e) {
					System.err.println("ERRO ao conectar com um cliente: " + e.getLocalizedMessage());
				}
			}
		}
		catch(IOException e) {
			serverGUI.showErrorMessage("Falha ao escutar na porta " + port, e.getLocalizedMessage());
		}
		finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				serverGUI.showErrorMessage("Não foi possível fechar a porta " + port, e.getLocalizedMessage());
			}
		}
	}
}
