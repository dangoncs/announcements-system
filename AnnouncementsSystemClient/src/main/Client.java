package main;

import gui.ConnectionGUI;

import javax.swing.*;

import java.awt.*;

public class Client extends JFrame {
	private ServerConnection serverConnection;
	private String loggedInUserToken;
	private String loggedInUserId;

	public static void main(String[] ignoredArgs) {
		EventQueue.invokeLater(() -> {
			try {
				Client client = new Client();
				new ConnectionGUI(client).setup();
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		});
	}

    public Client() {
		super("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setVisible(true);
	}

	public void showContentPane(JPanel contentPane) {
		setContentPane(contentPane);
		revalidate();
		repaint();
	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	public ServerConnection getServerConnection() {
		if (serverConnection == null)
			serverConnection = new ServerConnection();

		return serverConnection;
	}

	public String getLoggedInUserToken() {
		return loggedInUserToken;
	}

	public String getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserToken(String loggedInUserToken) {
		this.loggedInUserToken = loggedInUserToken;
	}

	public void setLoggedInUserId(String loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
}
