package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.Serial;

public class ClientGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	private Client client;

    public ClientGUI() {
		client = new Client();
		setTitle("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setupStartGUI();
	}

	private void setupStartGUI() {
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Insira os dados do servidor");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 424, 23);
		contentPane.add(lblWindowTitle);

		JLabel lblAddr = new JLabel("Endereço IP:");
		lblAddr.setBounds(5, 73, 135, 23);
		contentPane.add(lblAddr);

		JTextField txtAddr = new JTextField();
		txtAddr.setBounds(150, 73, 100, 23);
		contentPane.add(txtAddr);
		txtAddr.setColumns(10);

		JLabel lblPort = new JLabel("Número da porta:");
		lblPort.setBounds(5, 116, 135, 23);
		contentPane.add(lblPort);

		JTextField txtPort = new JTextField();
		txtPort.setBounds(150, 116, 100, 23);
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		JButton btnStartup = new JButton("Conectar");
		btnStartup.setBounds(5, 233, 424, 23);
		btnStartup.addActionListener(_ -> {
            String addr = txtAddr.getText();
            int port = Integer.parseInt(txtPort.getText());
            client.connectToServer(addr, port);
			setupMainGUI();
        });
		contentPane.add(btnStartup);
	}

	private void setupMainGUI() {
        JPanel mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainContentPane);
		mainContentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblTitle = new JLabel("Bem-vindo(a)");
		mainContentPane.add(lblTitle, BorderLayout.NORTH);

        JButton btnSignup = new JButton("Cadastrar");
		btnSignup.addActionListener(_ -> {
			JPanel newContentPane = new ClientSignupGUI().setup(client);
			setContentPane(newContentPane);
			revalidate();
        	repaint();
        });
		mainContentPane.add(btnSignup, BorderLayout.SOUTH);

		revalidate();
        repaint();
	}
}
