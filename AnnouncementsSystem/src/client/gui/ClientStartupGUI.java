package client.gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.Client;

public class ClientStartupGUI {

	public void setup(Client client, ClientGUI clientGUI) {
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
			clientGUI.setupMainGUI();
        });
		contentPane.add(btnStartup);

		clientGUI.setContentPane(contentPane);
	}
}
