package client;

import java.awt.EventQueue;

import client.gui.ClientGUI;
import client.gui.ClientStartupGUI;

import javax.swing.*;

public class ClientLauncher {
	
	public static void main(String[] ignoredArgs) {
		ServerConnection serverConnection = new ServerConnection();

		EventQueue.invokeLater(() -> {
			try {
				ClientGUI clientGUI = new ClientGUI(serverConnection);
				clientGUI.setVisible(true);

				JPanel connectionContentPane = new ClientStartupGUI(serverConnection, clientGUI).setup();
				clientGUI.setContentPane(connectionContentPane);
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		});
	}
}
