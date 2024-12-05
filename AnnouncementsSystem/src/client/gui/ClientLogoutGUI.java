package client.gui;

import client.Client;
import client.operations.LogoutOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientLogoutGUI {
    private final Client client;
    private final ClientGUI clientGUI;
    private final String clientUser;
    private final String clientToken;

    public ClientLogoutGUI(Client client, ClientGUI clientGUI, String clientUser, String clientToken) {
        this.client = client;
        this.clientGUI = clientGUI;
        this.clientUser = clientUser;
        this.clientToken = clientToken;
    }

    public JPanel setup() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Conectado");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 440, 23);
        contentPane.add(lblWindowTitle);

        JLabel lblLoggedIn = new JLabel("Você está logado como: " + clientUser);
        lblLoggedIn.setBounds(5, 50, 440, 23);
        contentPane.add(lblLoggedIn);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(5, 233, 424, 23);
        btnLogout.addActionListener(_ -> {
            String json = createJson(clientToken);

            String response = client.sendToServer(json);
            handleResponse(response);
        });
        contentPane.add(btnLogout);

        return contentPane;
    }

    private static String createJson(String token) {
        return new LogoutOperation("6", token).toJson();
    }

    private void handleResponse(String response) {
        if(response == null) {
            clientGUI.showErrorMessage("Erro", "A resposta recebida foi inválida.");
            return;
        }

        JsonObject receivedJson = JsonParser.parseString(response).getAsJsonObject();
        System.out.println("Recebido: " + receivedJson);

        JsonElement responseElement = receivedJson.get("response");
        JsonElement messageElement = receivedJson.get("message");

        String responseCode = (responseElement != null) ? responseElement.getAsString() : "";
        String message = (messageElement != null) ? messageElement.getAsString() : "";

        if(responseCode.equals("010")) {
            clientGUI.showSuccessMessage(message);
            clientGUI.showMainContentPane();
        }
        else {
            clientGUI.showErrorMessage("Erro ao realizar logout", message);
        }
    }
}
