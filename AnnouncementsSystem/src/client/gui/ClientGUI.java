package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.Serial;

public class ClientGUI extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
    private JTextField txtMessage;

    public ClientGUI(Client client) {
		setupGUI(client);
	}

	private void setupGUI(Client client) {
		setTitle("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		txtMessage = new JTextField();
		contentPane.add(txtMessage, BorderLayout.CENTER);
		txtMessage.setColumns(10);

        JButton btnTransform = new JButton("Transformar");
		btnTransform.addActionListener(_ -> {
            String userInput = txtMessage.getText();
            txtMessage.setText("");
            client.sendToServer(userInput);
        });
		contentPane.add(btnTransform, BorderLayout.SOUTH);

        JLabel lblTitle = new JLabel("Digite uma mensagem:");
		contentPane.add(lblTitle, BorderLayout.NORTH);
	}
}
