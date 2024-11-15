package server.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.Server;

import java.awt.BorderLayout;
import java.io.Serial;
import javax.swing.JLabel;

public class ServerGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
    private JLabel lblTransformedMsg;

	public ServerGUI(Server server) {
		setupGUI(server);
	}

	private void setupGUI(Server server) {
		setTitle("SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(750, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblTitle = new JLabel("A mensagem transformada é:");
		contentPane.add(lblTitle, BorderLayout.NORTH);

		lblTransformedMsg = new JLabel("Aguardando cliente...");
		contentPane.add(lblTransformedMsg, BorderLayout.CENTER);
	}

	public void setLabel(String msg) {
		this.lblTransformedMsg.setText(msg);
	}
}
