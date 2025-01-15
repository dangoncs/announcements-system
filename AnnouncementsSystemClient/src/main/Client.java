package main;

import gui.ConnectionGUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Client extends JFrame {
	private ServerConnection serverConnection;
	private String loggedInUserToken;
	private String loggedInUserId;
	private boolean isAdmin;

	public static void main(String[] ignoredArgs) {
		SwingUtilities.invokeLater(() -> {
			Client client = new Client();
			new ConnectionGUI(client).setup();
		});
	}

    public Client() {
		super("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setLoggedInUserToken(String loggedInUserToken) {
		this.loggedInUserToken = loggedInUserToken;
	}

	public void setLoggedInUserId(String loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	public void setAdmin(boolean admin) {
		this.isAdmin = admin;
	}
}
