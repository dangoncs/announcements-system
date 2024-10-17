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
		
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			echoSocket = new Socket(serverHostname, serverPort);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("ERRO: Host não encontrado!");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERRO de entrada ou saída!");
			e.printStackTrace();
			System.exit(1);
		}
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		System.out.println("Input: ");
		try {
			while((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println("Echo: " + in.readLine());
				System.out.println("Input: ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.close();
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stdIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			echoSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
