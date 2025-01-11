package client.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.ServerConnection;
import client.operations.SignupOperation;
import client.responses.Response;

public class ClientSignupGUI {

	private final ServerConnection serverConnection;
	private final ClientGUI clientGUI;
	private JTextField txtUserId;
	private JTextField txtName;
	private JTextField txtPasswd;

	public ClientSignupGUI(ServerConnection serverConnection, ClientGUI clientGUI) {
		this.serverConnection = serverConnection;
		this.clientGUI = clientGUI;
	}

    public JPanel setup() {
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Insira os dados da conta:");
		lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWindowTitle.setBounds(5, 5, 424, 23);
		contentPane.add(lblWindowTitle);

		JLabel lblUserId = new JLabel("RA:");
		lblUserId.setBounds(5, 73, 135, 23);
		contentPane.add(lblUserId);

		txtUserId = new JTextField();
		txtUserId.setBounds(150, 73, 100, 23);
		contentPane.add(txtUserId);
		txtUserId.setColumns(10);

		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(5, 116, 135, 23);
		contentPane.add(lblName);

		txtName = new JTextField();
		txtName.setBounds(150, 116, 100, 23);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblPasswd = new JLabel("Senha:");
		lblPasswd.setBounds(5, 159, 135, 23);
		contentPane.add(lblPasswd);

		txtPasswd = new JTextField();
		txtPasswd.setBounds(150, 159, 100, 23);
		contentPane.add(txtPasswd);
		txtPasswd.setColumns(10);

		JButton btnSignup = new JButton("Cadastrar");
		btnSignup.setBounds(5, 233, 424, 23);
		btnSignup.addActionListener(_ -> signupActionHandler());
		contentPane.add(btnSignup, BorderLayout.SOUTH);

        return contentPane;
	}

	private void signupActionHandler() {
		String userId = txtUserId.getText();
		String name = txtName.getText();
		String passwd = txtPasswd.getText();
		txtUserId.setText("");
		txtName.setText("");
		txtPasswd.setText("");

		SignupOperation signupOperation = new SignupOperation("1", userId, passwd, name);
		String json = signupOperation.toJson();
		String responseJson;

		try {
			responseJson = serverConnection.sendToServer(json);
		} catch (IOException e) {
			clientGUI.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
			return;
		}

		Response signupResponse = new Response(responseJson);
		String responseCode = signupResponse.getResponseCode();
		String message = signupResponse.getMessage();

		if(responseCode.equals("100"))
			clientGUI.showSuccessMessage(message);
		else
			clientGUI.showErrorMessage("Erro ao realizar cadastro", message);

		clientGUI.showStartContentPane();
	}
}
