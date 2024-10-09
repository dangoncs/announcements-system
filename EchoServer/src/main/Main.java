package main;

import gui.ClientGUI;
import gui.ServerGUI;
import server.Server;

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		ServerGUI serverGUI = new ServerGUI(server);
		serverGUI.setVisible(true);
		
		//Client client = new Client(server);
		ClientGUI clientGUI = new ClientGUI(server, serverGUI);
		clientGUI.setVisible(true);
	}

}
