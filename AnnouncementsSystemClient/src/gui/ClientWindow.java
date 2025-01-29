package gui;

import gui.connection.ConnectionGUI;
import main.Client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.IOException;

public class ClientWindow extends JFrame {

    public ClientWindow() {
		super("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		setVisible(true);

		new ConnectionGUI(this);
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
		JOptionPane.showMessageDialog(null,
				message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	public int showConfirmationPrompt(String title, String message) {
		return JOptionPane.showConfirmDialog(null,
				message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
	}

	public void showConnectionErrorHandler(Client client, String errorMessage) {
		String[] options = {"Encerrar conexão", "OK"};

		int userOption = JOptionPane.showOptionDialog(null, errorMessage,
				"Erro ao comunicar com o servidor", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
				null, options, "OK");

		if (userOption == 0) {
			try {
				client.disconnect();
				new ConnectionGUI(this);
			} catch (IOException ie) {
				showErrorMessage("Erro ao desconectar", ie.getLocalizedMessage());
				dispose();
			}
		}
	}
}
