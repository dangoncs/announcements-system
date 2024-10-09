package client;

import java.util.Scanner;

import server.Server;

public class Client {
	
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
