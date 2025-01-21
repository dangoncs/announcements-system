package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.Server;

import java.awt.Font;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerWindow extends JFrame {
	private JTextField txtPort;

	public ServerWindow() {
		setTitle("SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		setVisible(true);

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

		txtPort = new JTextField();
		txtPort.setBounds(201, 116, 100, 23);
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		JButton btnStartup = new JButton("Iniciar");
		btnStartup.setBounds(5, 233, 424, 23);
		btnStartup.addActionListener(_ -> startServer());
		contentPane.add(btnStartup);
	}

	private void startServer() {
		int port = Integer.parseInt(txtPort.getText());

		new Thread(() -> {
			Server server = new Server();

			try (ServerSocket serverSocket = new ServerSocket(port)) {
				SwingUtilities.invokeLater(() -> setupMainGUI(server, port));
				server.startConnectionLoop(serverSocket);
			} catch (Exception e) {
				showErrorMessage("Erro ao escutar na porta " + port, e.getLocalizedMessage());
			}
		}).start();
	}

	private void setupMainGUI(Server server, int serverPort) {
		JPanel mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Servidor iniciado");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 424, 23);
		mainContentPane.add(lblWindowTitle);

		JLabel lblPort = new JLabel("Escutando na porta " + serverPort + "...");
		lblPort.setBounds(5, 50, 440, 23);
		mainContentPane.add(lblPort);

		JButton btnStartup = new JButton("Encerrar");
		btnStartup.setBounds(5, 233, 424, 23);
		btnStartup.addActionListener(_ -> exitActionHandler(server));
		mainContentPane.add(btnStartup);

		setContentPane(mainContentPane);
		revalidate();
		repaint();
	}

	private void exitActionHandler(Server server) {
		try {
			server.closeSocket();
		} catch (IOException e) {
			showErrorMessage("Erro ao fechar o socket", e.getLocalizedMessage());
		} finally {
			dispose();
		}
	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
