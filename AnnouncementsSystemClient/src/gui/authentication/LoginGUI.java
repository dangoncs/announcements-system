package gui.authentication;

import gui.UserHomeGUI;
import gui.account.CreateAccountGUI;
import main.Client;
import operations.authentication.LoginOperation;
import responses.authentication.LoginResponse;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import java.awt.Font;
import java.io.IOException;

public class LoginGUI {
    private final Client client;
    private JTextField txtUserId;
    private JTextField txtPasswd;

    public LoginGUI(Client client) {
        this.client = client;
    }

    public void setup() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Bem-vindo");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 440, 23);
        contentPane.add(lblWindowTitle);

        JLabel lblUserId = new JLabel("Usuário:");
        lblUserId.setBounds(140, 63, 70, 23);
        contentPane.add(lblUserId);

        txtUserId = new JTextField();
        txtUserId.setBounds(210, 63, 100, 23);
        contentPane.add(txtUserId);

        JLabel lblPasswd = new JLabel("Senha:");
        lblPasswd.setBounds(140, 106, 70, 23);
        contentPane.add(lblPasswd);

        txtPasswd = new JTextField();
        txtPasswd.setBounds(210, 106, 100, 23);
        contentPane.add(txtPasswd);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 149, 170, 23);
        btnLogin.addActionListener(_ -> loginActionHandler());
        contentPane.add(btnLogin);

        JSeparator separator = new JSeparator();
        separator.setBounds(140, 183, 170, 2);
        contentPane.add(separator);

        JButton btnSignup = new JButton("Cadastro");
        btnSignup.setBounds(140, 194, 170, 23);
        btnSignup.addActionListener(_ ->
                new CreateAccountGUI(client).setup()
        );
        contentPane.add(btnSignup);

        client.showContentPane(contentPane);
    }

    private void loginActionHandler() {
        String userId = txtUserId.getText();
        String passwd = txtPasswd.getText();

        LoginOperation loginOp = new LoginOperation("5", userId, passwd);
        String json = loginOp.toJson();
        String responseJson;

        try {
            responseJson = client.getServerConnection().sendToServer(json);
        } catch (IOException e) {
            client.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
            return;
        }

        LoginResponse loginResponse = new LoginResponse(responseJson);
        String responseCode = loginResponse.getResponseCode();
        String message = loginResponse.getMessage();

        if(responseCode.equals("000") || responseCode.equals("001")) {
            client.setLoggedInUserToken(loginResponse.getToken());
            client.setLoggedInUserId(userId);

            client.showSuccessMessage(message);
            new UserHomeGUI(client).setup();
        }
        else {
            client.showErrorMessage("Erro ao realizar login", message);
        }
    }
}
