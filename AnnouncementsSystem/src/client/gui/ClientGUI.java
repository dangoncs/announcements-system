package client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.ServerConnection;

import java.awt.Font;

import java.io.Serial;

public class ClientGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	private final ServerConnection serverConnection;
	JPanel mainContentPane;

    public ClientGUI(ServerConnection serverConnection) {
		super("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		this.serverConnection = serverConnection;
		JPanel startupContentPane = new ClientStartupGUI(serverConnection, this).setup();
		setContentPane(startupContentPane);
	}

	public void setupMainGUI() {
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Bem-vindo(a)");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 440, 23);
		mainContentPane.add(lblWindowTitle);

        JButton btnSignup = new JButton("Cadastro");
		btnSignup.setBounds(5, 120, 424, 23);
		btnSignup.addActionListener(_ -> {
			JPanel signupContentPane = new ClientSignupGUI(serverConnection, this).setup();
			changeContentPane(signupContentPane);
        });
		mainContentPane.add(btnSignup);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(5, 150, 424, 23);
		btnLogin.addActionListener(_ -> {
			JPanel loginContentPane = new ClientLoginGUI(serverConnection, this).setup();
			changeContentPane(loginContentPane);
		});
		mainContentPane.add(btnLogin);

		showMainContentPane();
	}

	public void changeContentPane(JPanel contentPane) {
		setContentPane(contentPane);
		revalidate();
		repaint();
	}

	public void showMainContentPane() {
		changeContentPane(mainContentPane);
	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
}
