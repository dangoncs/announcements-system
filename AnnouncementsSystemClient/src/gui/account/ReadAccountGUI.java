package gui.account;

import gui.home.HomeGUI;
import main.Client;
import operations.account.ReadAccountOperation;
import responses.account.ReadAccountResponse;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.io.IOException;

public class ReadAccountGUI {
    private final Client client;

    public ReadAccountGUI(Client client) {
        this.client = client;
    }

    public void readAccount(String accountId, String clientToken) {
        ReadAccountOperation readAccountOp = new ReadAccountOperation(accountId, clientToken);
        String json = readAccountOp.toJson();
        String responseJson;

        try {
            responseJson = client.getServerConnection().sendToServer(json);
        } catch (IOException e) {
            client.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
            new HomeGUI(client).setup();
            return;
        }

        ReadAccountResponse readAccountResponse = new ReadAccountResponse(responseJson);
        String responseCode = readAccountResponse.getResponseCode();

        if(responseCode.equals("110") || responseCode.equals("111")) {
            String userId = readAccountResponse.getUserId();
            String password = readAccountResponse.getPassword();
            String name = readAccountResponse.getName();
            String token = readAccountResponse.getToken();

            setup(userId, password, name, token);
        }
        else {
            String message = readAccountResponse.getMessage();
            client.showErrorMessage("Erro ao ler dados da conta", message);
            new HomeGUI(client).setup();
        }
    }

    private void setup(String userId, String password, String name, String token) {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Dados de cadastro");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 440, 23);
        contentPane.add(lblWindowTitle);

        JLabel lblUserId = new JLabel("Usuário: " + userId);
        lblUserId.setBounds(5, 70, 135, 23);
        contentPane.add(lblUserId);

        JLabel lblName = new JLabel("Nome: " + name);
        lblName.setBounds(5, 100, 135, 23);
        contentPane.add(lblName);

        JLabel lblPassword = new JLabel("Senha: " + password);
        lblPassword.setBounds(5, 130, 135, 23);
        contentPane.add(lblPassword);

        JLabel lblToken = new JLabel("Token: " + token);
        lblToken.setBounds(5, 160, 135, 23);
        contentPane.add(lblToken);

        JButton btnBack = new JButton("Voltar");
        btnBack.setBounds(5, 233, 424, 23);
        btnBack.addActionListener(_ -> new HomeGUI(client).setup());
        contentPane.add(btnBack);

        client.showContentPane(contentPane);
    }
}
