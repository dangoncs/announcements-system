package server.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import server.Server;

import java.awt.*;
import java.io.Serial;

public class ServerGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	private JLabel lblMessage;

	public ServerGUI() {
		setTitle("SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setupStartGUI();
	}

	private void setupStartGUI() {
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Iniciar servidor");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 440, 23);
		contentPane.add(lblWindowTitle);

		JLabel lblPort = new JLabel("Número da porta a ser utilizada:");
		lblPort.setBounds(5, 116, 186, 23);
		contentPane.add(lblPort);

		JTextField txtPort = new JTextField();
		txtPort.setBounds(201, 116, 100, 23);
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		JButton btnStartup = new JButton("Iniciar");
		btnStartup.setBounds(5, 233, 424, 23);
		btnStartup.addActionListener(_ -> {
            int port = Integer.parseInt(txtPort.getText());

			new Thread(() -> {
				new Server(port, this);
			}).start();

			setupMainGUI(port);
        });
		contentPane.add(btnStartup);
	}

	private void setupMainGUI(int port) {
		JPanel mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainContentPane);
		mainContentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Servidor iniciado");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 424, 23);
		mainContentPane.add(lblWindowTitle);

		JLabel lblPort = new JLabel("Escutando na porta " + port);
		lblPort.setBounds(5, 50, 440, 23);
		mainContentPane.add(lblPort);

        JLabel lblInfo = new JLabel("Mensagem mais recente recebida:");
		lblInfo.setBounds(5, 70, 440, 23);
		mainContentPane.add(lblInfo);

		lblMessage = new JLabel("Aguardando cliente...");
		lblMessage.setBounds(5, 90, 440, 50);
		mainContentPane.add(lblMessage);

		revalidate();
		repaint();
	}

	public void setMessageText(String text) {
		if(text != null)
			this.lblMessage.setText(text);
	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
