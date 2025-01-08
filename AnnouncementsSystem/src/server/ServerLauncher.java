package server;

import java.awt.EventQueue;

import server.gui.ServerGUI;

public class ServerLauncher {
	
	public static void main(String[] ignoredArgs) {
		EventQueue.invokeLater(() -> {
			try {
				ServerGUI frame = new ServerGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		});
	}
}
