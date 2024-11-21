package server.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import server.Server;

import java.awt.*;
import java.io.Serial;
import javax.swing.JLabel;

public class ServerGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ServerGUI frame = new ServerGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		});
	}

	public ServerGUI() {
		setTitle("SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setupStartGUI();
		setVisible(true);
	}

	private void setupStartGUI() {
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWindowTitle = new JLabel("Iniciar servidor");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 424, 23);
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
				new Server(port);
			}).start();

			setupMainGUI();
        });
		contentPane.add(btnStartup);
	}

	private void setupMainGUI() {
		JPanel mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainContentPane);
		mainContentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblTitle = new JLabel("A mensagem transformada é:");
		mainContentPane.add(lblTitle, BorderLayout.NORTH);

		JLabel lblTransformedMsg = new JLabel("Aguardando cliente...");
		mainContentPane.add(lblTransformedMsg, BorderLayout.CENTER);

		revalidate();
		repaint();
	}
}
