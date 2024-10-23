package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	public static void main(String[] args) {
		int port = 23456;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port);
			System.out.printf("INFO: Escutando na porta %d.\n", port);

			while(true) {
				try {
					new ServerThread(serverSocket.accept());
					System.out.println("INFO: Conexão estabelecida com sucesso!");
				}
				catch(IOException e) {
					System.err.println("ERRO: Falha ao conectar com o cliente: " + e.getMessage());
					break;
				}
			}
		}
	    catch(IOException e) {
	    	System.err.printf("ERRO: Falha ao escutar na porta %d: %s\n", port, e.getMessage());
	    }
		finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.printf("ERRO: Não foi possível fechar a porta %d.\n", port);
			}
		}
	}
}
