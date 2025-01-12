package gui.authentication;

import gui.StartGUI;
import gui.UserHomeGUI;
import main.Client;
import operations.authentication.LoginOperation;
import responses.authentication.LoginResponse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

        JLabel lblWindowTitle = new JLabel("Fazer login");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 440, 23);
        contentPane.add(lblWindowTitle);

        JLabel lblUserId = new JLabel("Usuário:");
        lblUserId.setBounds(5, 116, 135, 23);
        contentPane.add(lblUserId);

        txtUserId = new JTextField();
        txtUserId.setBounds(150, 116, 100, 23);
        contentPane.add(txtUserId);
        txtUserId.setColumns(10);

        JLabel lblPasswd = new JLabel("Senha:");
        lblPasswd.setBounds(5, 159, 135, 23);
        contentPane.add(lblPasswd);

        txtPasswd = new JTextField();
        txtPasswd.setBounds(150, 159, 100, 23);
        contentPane.add(txtPasswd);
        txtPasswd.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(5, 233, 424, 23);
        btnLogin.addActionListener(_ -> loginActionHandler());
        contentPane.add(btnLogin);

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
            new StartGUI(client).setup();
        }
    }
}
