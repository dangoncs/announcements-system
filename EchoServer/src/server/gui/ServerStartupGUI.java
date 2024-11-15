package server.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.Server;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.io.Serial;

public class ServerStartupGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
    private JTextField txtPort;

	public ServerStartupGUI(Server server) {
		setupGUI(server);
	}

	private void setupGUI(Server server) {
		setTitle("SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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

		txtPort = new JTextField();
		txtPort.setBounds(201, 116, 100, 23);
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		JButton btnStartup = new JButton("Iniciar");
		btnStartup.setBounds(5, 233, 424, 23);
		btnStartup.addActionListener(e -> {
            int port = Integer.parseInt(txtPort.getText());
            server.startup(port);
        });
		contentPane.add(btnStartup);
	}
}
