package server.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.gui.ClientStartupGUI;
import server.Server;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;

public class ServerStartupGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPort;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerStartupGUI frame = new ServerStartupGUI(new Server());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerStartupGUI(Server server) {
		setTitle("SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
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
		btnStartup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = Integer.valueOf(txtPort.getText());
				server.startup(port);
			}
		});
		contentPane.add(btnStartup);
	}
}
