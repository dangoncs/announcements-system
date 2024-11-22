package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.Serial;

public class ClientGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	private final Client client;
	JPanel mainContentPane;

    public ClientGUI() {
		client = new Client();
		setTitle("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		new ClientStartupGUI().setup(client, this);
	}

	public void setupMainGUI() {
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblWindowTitle = new JLabel("Bem-vindo(a)");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		mainContentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JButton btnSignup = new JButton("Cadastrar");
		btnSignup.addActionListener(_ -> {
			JPanel newContentPane = new ClientSignupGUI(client, this).setup();
			setContentPane(newContentPane);
			revalidate();
        	repaint();
        });
		mainContentPane.add(btnSignup, BorderLayout.SOUTH);

		showMainContentPane();
	}

	public void showMainContentPane() {
		setContentPane(mainContentPane);
		revalidate();
		repaint();
	}
}
