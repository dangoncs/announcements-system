package gui.account;

import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;
import operations.account.ReadAccountOperation;
import responses.account.ReadAccountResponse;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.io.IOException;

public class ReadAccountGUI {
    private final Client client;
    private final ClientWindow clientWindow;

    public ReadAccountGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        determineUserId();
    }

    private void determineUserId() {
        String userId = client.getLoggedInUserId();

        if (client.isAdmin()) {
            String message = "Digite o usuário da conta que deseja manipular.\nDeixe em branco para manipular a própria conta.";
            userId = JOptionPane.showInputDialog(message);

            if (userId == null)
                return;

            if (userId.isBlank())
                userId = client.getLoggedInUserId();
        }

        readAccountActionHandler(userId);
    }

    private void readAccountActionHandler(String accountId) {
        String clientToken = client.getLoggedInUserToken();

        ReadAccountOperation readAccountOp = new ReadAccountOperation(accountId, clientToken);
        String responseJson;

        try {
            responseJson = client.sendToServer(readAccountOp);
        } catch (IOException e) {
            clientWindow.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
            new HomeGUI(client, clientWindow);
            return;
        }

        ReadAccountResponse readAccountResponse = new ReadAccountResponse(responseJson);
        String responseCode = readAccountResponse.getResponseCode();

        if(responseCode.equals("110") || responseCode.equals("111")) {
            String userId = readAccountResponse.getUserId();
            String password = readAccountResponse.getPassword();
            String name = readAccountResponse.getName();

            setupGUI(userId, password, name);
        }
        else {
            String message = readAccountResponse.getMessage();
            clientWindow.showErrorMessage("Erro ao ler dados da conta", message);
            new HomeGUI(client, clientWindow);
        }
    }

    private void setupGUI(String userId, String password, String name) {
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

        JButton btnBack = new JButton("Voltar");
        btnBack.setBounds(5, 233, 424, 23);
        btnBack.addActionListener(_ -> new HomeGUI(client, clientWindow));
        contentPane.add(btnBack);

        clientWindow.showContentPane(contentPane);
    }
}
