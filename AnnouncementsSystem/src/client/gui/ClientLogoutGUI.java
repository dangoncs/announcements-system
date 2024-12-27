package client.gui;

import client.ServerConnection;
import client.operations.LogoutOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class ClientLogoutGUI {
    private final ServerConnection serverConnection;
    private final ClientGUI clientGUI;
    private final String clientUser;
    private final String clientToken;

    public ClientLogoutGUI(ServerConnection serverConnection, ClientGUI clientGUI, String clientUser, String clientToken) {
        this.serverConnection = serverConnection;
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

        JButton btnLogout = new JButton("Fazer logout e sair");
        btnLogout.setBounds(5, 233, 424, 23);
        btnLogout.addActionListener(_ -> {
            String json = createJson(clientToken);

            String response = null;
            try {
                response = serverConnection.sendToServer(json);
            } catch (IOException e) {
                clientGUI.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
            }

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
            return;
        }

        JsonObject receivedJson = JsonParser.parseString(response).getAsJsonObject();
        System.out.println("Recebido: " + receivedJson);

        JsonElement responseElement = receivedJson.get("responseCode");
        JsonElement messageElement = receivedJson.get("message");

        String responseCode = (responseElement != null) ? responseElement.getAsString() : "";
        String message = (messageElement != null) ? messageElement.getAsString() : "";

        if(responseCode.equals("010")) {
            clientGUI.showSuccessMessage(message);
            serverConnection.disconnectAndExit();
        }
        else {
            clientGUI.showErrorMessage("Erro ao realizar logout", message);
        }
    }
}
