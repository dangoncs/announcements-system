package main;

import client.Client;
import server.Server;

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		Client client = new Client(server);

		client.transformString();
	}

}
