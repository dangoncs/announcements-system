package client;

import java.awt.EventQueue;

import client.gui.ClientGUI;

public class ClientLauncher {
	
	public static void main(String[] args) {
		ServerConnection serverConnection = new ServerConnection();

		EventQueue.invokeLater(() -> {
			try {
				ClientGUI frame = new ClientGUI(serverConnection);
				frame.setVisible(true);
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		});
	}
}
