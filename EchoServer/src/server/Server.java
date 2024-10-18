package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		int port = 23456;

		try(ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.printf("Escutando na porta %d.\n", port);

			while(true) {
				try(Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
					) {
					System.out.println("Conexão estabelecida com sucesso!");

					String inputLine;
					System.out.println("Aguardando cliente...");
					while((inputLine = in.readLine()) != null) {
						if (inputLine.equalsIgnoreCase("bye")) break;

						System.out.println("Cliente diz: " + inputLine);
						out.println(inputLine.toUpperCase());
						System.out.println("Aguardando cliente...");
					}
					System.out.println("Conexão fechada pelo cliente.");
				}
				catch(IOException e) {
					System.out.println("Falha ao conectar com o cliente:");
					System.out.println(e.getMessage());
					System.exit(1);
				}
			}
		}
	    catch(IOException e) {
	    	System.out.printf("Falha ao escutar na porta %d:\n", port);
			System.out.println(e.getMessage());
	    	System.exit(1);
	    }
	}

	public String echo(String msg) {
		return msg.toUpperCase();
	}
}
