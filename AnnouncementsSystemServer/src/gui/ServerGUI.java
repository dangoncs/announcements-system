package gui;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Server;

import java.awt.Font;
import java.net.ServerSocket;

public class ServerGUI extends JFrame {
	private JTextField txtPort;

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
		Server server = new Server();
		int port = Integer.parseInt(txtPort.getText());

		new Thread(() -> {
			try (ServerSocket _ = server.createSocket(port)) {
				setupMainGUI(port);
				server.startConnectionLoop();
			} catch (Exception e) {
				showErrorMessage("Falha ao escutar na porta", e.getLocalizedMessage());
			}
		}).start();
	}

	private void setupMainGUI(int port) {
		JPanel mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Servidor iniciado");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 424, 23);
		mainContentPane.add(lblWindowTitle);

		JLabel lblPort = new JLabel("Escutando na porta " + port + "...");
		lblPort.setBounds(5, 50, 440, 23);
		mainContentPane.add(lblPort);

		setContentPane(mainContentPane);
		revalidate();
		repaint();
	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
