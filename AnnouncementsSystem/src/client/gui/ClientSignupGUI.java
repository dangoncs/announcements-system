package client.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.operations.SignupOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClientSignupGUI {

	private final Client client;
	private final ClientGUI clientGUI;

	public ClientSignupGUI(Client client, ClientGUI clientGUI) {
		this.client = client;
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

		JTextField txtUserId = new JTextField();
		txtUserId.setBounds(150, 73, 100, 23);
		contentPane.add(txtUserId);
		txtUserId.setColumns(10);

		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(5, 116, 135, 23);
		contentPane.add(lblName);

		JTextField txtName = new JTextField();
		txtName.setBounds(150, 116, 100, 23);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblPasswd = new JLabel("Senha:");
		lblPasswd.setBounds(5, 159, 135, 23);
		contentPane.add(lblPasswd);

		JTextField txtPasswd = new JTextField();
		txtPasswd.setBounds(150, 159, 100, 23);
		contentPane.add(txtPasswd);
		txtPasswd.setColumns(10);

		JButton btnSignup = new JButton("Cadastrar");
		btnSignup.setBounds(5, 233, 424, 23);
		btnSignup.addActionListener(_ -> {
            String userId = txtUserId.getText();
            String name = txtName.getText();
			String passwd = txtPasswd.getText();
            txtUserId.setText("");
            txtName.setText("");
			txtPasswd.setText("");

			String json = createJson(userId, passwd, name);

			String response = client.sendToServer(json);
			handleResponse(response);
			clientGUI.showMainContentPane();
        });
		contentPane.add(btnSignup, BorderLayout.SOUTH);

        return contentPane;
	}

	private static String createJson(String userId, String passwd, String name) {
		return new SignupOperation("1", userId, passwd, name).toJson();
	}

	private void handleResponse(String response) {
		if(response == null)
			showErrorMessage("Erro", "A resposta recebida foi inválida.");

		JsonObject receivedJson = JsonParser.parseString(response).getAsJsonObject();
		System.out.println("Recebido: " + receivedJson);

		JsonElement responseElement = receivedJson.get("response");
		JsonElement messageElement = receivedJson.get("message");

		String responseCode = (responseElement != null) ? responseElement.getAsString() : "";
		String message = (messageElement != null) ? messageElement.getAsString() : "";

		if(responseCode.equals("100"))
			showSuccessMessage(message);
		else
			showErrorMessage("Erro ao realizar cadastro", message);

		clientGUI.showMainContentPane();
	}

	private void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	private void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
}
