package client.gui;

import client.Client;
import client.operations.LoginOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientLoginGUI {

    private final Client client;
    private final ClientGUI clientGUI;

    public ClientLoginGUI(Client client, ClientGUI clientGUI) {
        this.client = client;
        this.clientGUI = clientGUI;
    }

    public JPanel setup() {
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

        JTextField txtUserId = new JTextField();
        txtUserId.setBounds(150, 116, 100, 23);
        contentPane.add(txtUserId);
        txtUserId.setColumns(10);

        JLabel lblPasswd = new JLabel("Senha:");
        lblPasswd.setBounds(5, 159, 135, 23);
        contentPane.add(lblPasswd);

        JTextField txtPasswd = new JTextField();
        txtPasswd.setBounds(150, 159, 100, 23);
        contentPane.add(txtPasswd);
        txtPasswd.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(5, 233, 424, 23);
        btnLogin.addActionListener(_ -> {
            String userId = txtUserId.getText();
            String passwd = txtPasswd.getText();
            txtUserId.setText("");
            txtPasswd.setText("");

            String json = createJson(userId, passwd);

            String response = client.sendToServer(json);
            handleResponse(response, userId);
        });
        contentPane.add(btnLogin);

        return contentPane;
    }

    private String createJson(String userId, String password) {
        return new LoginOperation("5", userId, password).toJson();
    }

    private void handleResponse(String response, String userId) {
        if(response == null) {
            clientGUI.showErrorMessage("Erro", "A resposta recebida foi inválida.");
            return;
        }

        JsonObject receivedJson = JsonParser.parseString(response).getAsJsonObject();
        System.out.println("Recebido: " + receivedJson);

        JsonElement responseElement = receivedJson.get("response");
        JsonElement messageElement = receivedJson.get("message");
        JsonElement tokenElement = receivedJson.get("token");

        String responseCode = (responseElement != null) ? responseElement.getAsString() : "";
        String message = (messageElement != null) ? messageElement.getAsString() : "";
        String token = (tokenElement != null) ? tokenElement.getAsString() : "";

        if(responseCode.equals("000") || responseCode.equals("001")) {
            clientGUI.showSuccessMessage(message);
            JPanel logoutContentPane = new ClientLogoutGUI(client, clientGUI, userId, token).setup();
            clientGUI.setContentPane(logoutContentPane);
            clientGUI.revalidate();
            clientGUI.repaint();
        }
        else {
            clientGUI.showErrorMessage("Erro ao realizar login", message);
            clientGUI.showMainContentPane();
        }
    }

}
