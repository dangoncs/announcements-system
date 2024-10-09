package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.Server;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMessage;
	private JButton btnTransform;
	private JLabel lblTitle;

	public ClientGUI(Server server, ServerGUI serverGUI) {
		setTitle("CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		txtMessage = new JTextField();
		contentPane.add(txtMessage, BorderLayout.CENTER);
		txtMessage.setColumns(10);
		
		btnTransform = new JButton("Transformar");
		btnTransform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = txtMessage.getText();
				String transformedMsg = server.echo(input);
				serverGUI.setLabel(transformedMsg);
			}
		});
		contentPane.add(btnTransform, BorderLayout.SOUTH);
		
		lblTitle = new JLabel("Digite uma mensagem:");
		contentPane.add(lblTitle, BorderLayout.NORTH);
	}

}
