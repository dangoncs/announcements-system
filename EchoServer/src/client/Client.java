package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import server.Server;

public class Client {
	
	public static void main(String[] args) {
		String serverHostname = "127.0.0.1";
		int serverPort = 23456;
		
		System.out.printf("Conectando: %s/%d\n", serverHostname, serverPort);

		try (Socket echoSocket = new Socket(serverHostname, serverPort);
			 PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
		) {
			System.out.println("Conexão estabelecida com sucesso!");

			String userInput;
			System.out.println("Input: ");

			while((userInput = stdIn.readLine()) != null) {
				if (userInput.equalsIgnoreCase("bye")) break;

				out.println(userInput);
				System.out.println("Echo: " + in.readLine());
				//if((String response = in.readLine()) != null) {
				//	System.out.println("Eco: " + response);
				//}
				//else {
				//	System.out.println("Conexão com o servidor perdida.");
				//	break;
				//}
				System.out.println("Input: ");
			}
		} catch (UnknownHostException e) {
			System.out.println("ERRO: Host não encontrado:");
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERRO de entrada ou saída:");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	private Server server;

	public Client(Server server) {
		this.server = server;
	}
	
	private String readString() {
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		scanner.close();
		return str;
	}

	public void transformString() {
		String message = readString();
		String uppercaseMessage = server.echo(message);
		System.out.println(uppercaseMessage);
	}
}
