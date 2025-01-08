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
					new ServerThread(serverSocket.accept()).start();
				}
				catch(IOException e) {
                    System.err.printf("[AVISO] Não foi possível conectar com um cliente: %s%n", e.getLocalizedMessage());
				}
			}
		}
		catch(IOException e) {
			serverGUI.showErrorMessage("[ERRO] Falha ao escutar na porta " + port, e.getLocalizedMessage());
		}
		finally {
			try {
				if(serverSocket != null) serverSocket.close();
			} catch (IOException e) {
				serverGUI.showErrorMessage("[ERRO] Não foi possível fechar a porta " + port, e.getLocalizedMessage());
				System.exit(1);
			}
		}
	}
}
