package client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.Font;

import java.io.Serial;

public class ClientGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	private final Client client;
	JPanel mainContentPane;

    public ClientGUI() {
		super("CLIENTE");
		client = new Client(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JPanel setupContentPane = new ClientStartupGUI().setup(client);
		setContentPane(setupContentPane);
	}

	public void setupMainGUI(String serverHostname, int serverPort) {
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Bem-vindo(a)");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 440, 23);
		mainContentPane.add(lblWindowTitle);

        JButton btnSignup = new JButton("Cadastrar");
		btnSignup.setBounds(5, 233, 424, 23);
		btnSignup.addActionListener(_ -> {
			JPanel newContentPane = new ClientSignupGUI(client, this).setup();
			setContentPane(newContentPane);
			revalidate();
        	repaint();
        });
		mainContentPane.add(btnSignup);

		showMainContentPane();
	}

	public void showMainContentPane() {
		setContentPane(mainContentPane);
		revalidate();
		repaint();
	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
