package gui.account;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.authentication.LoginGUI;
import gui.ClientWindow;
import main.Client;
import operations.account.CreateAccountOperation;
import responses.Response;

public class CreateAccountGUI {
	private final Client client;
	private final ClientWindow clientWindow;
	private JTextField txtUserId;
	private JTextField txtName;
	private JTextField txtPasswd;

	public CreateAccountGUI(Client client, ClientWindow clientWindow) {
		this.client = client;
		this.clientWindow = clientWindow;

		setupGUI();
	}

    private void setupGUI() {
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

		JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new LoginGUI(client, clientWindow));
        buttons.add(btnBack);

        JButton btnSignup = new JButton("Cadastrar");
		btnSignup.addActionListener(_ -> createAccountActionHandler());
        buttons.add(btnSignup);

		clientWindow.showContentPane(contentPane);
	}

	private void createAccountActionHandler() {
		String userId = txtUserId.getText();
		String name = txtName.getText();
		String passwd = txtPasswd.getText();

		CreateAccountOperation createAccountOp = new CreateAccountOperation(userId, passwd, name);
		String responseJson;

		try {
			responseJson = client.sendToServer(createAccountOp);
		} catch (IOException e) {
			clientWindow.showConnectionErrorHandler(client, e.getLocalizedMessage());
			return;
		}

		Response createAccountResponse = new Response(responseJson);
		String responseCode = createAccountResponse.getResponseCode();
		String message = createAccountResponse.getMessage();

		new LoginGUI(client, clientWindow);

		if(!responseCode.equals("100"))
			clientWindow.showErrorMessage("Erro ao realizar cadastro", message);
		else
			clientWindow.showSuccessMessage(message);
	}
}
