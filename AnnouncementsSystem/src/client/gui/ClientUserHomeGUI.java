package client.gui;

import client.Client;
import client.gui.account.ClientReadAccountGUI;
import client.operations.LogoutOperation;
import client.responses.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class ClientUserHomeGUI {
    private final Client client;

    public ClientUserHomeGUI(Client client) {
        this.client = client;
    }

    public void setup() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Conectado");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 440, 23);
        contentPane.add(lblWindowTitle);

        JLabel lblLoggedIn = new JLabel("Você está logado como: " + client.getLoggedInUserId());
        lblLoggedIn.setBounds(5, 50, 440, 23);
        contentPane.add(lblLoggedIn);

        JButton btnReadAccount = new JButton("Ver dados da conta");
        btnReadAccount.setBounds(5, 120, 424, 23);
        btnReadAccount.addActionListener(_ ->
                new ClientReadAccountGUI(client).readAccount(client.getLoggedInUserId(), client.getLoggedInUserToken())
        );
        contentPane.add(btnReadAccount);


        JButton btnLogout = new JButton("Fazer logout e sair");
        btnLogout.setBounds(5, 150, 424, 23);
        btnLogout.addActionListener(_ -> logoutActionHandler());
        contentPane.add(btnLogout);

        client.showContentPane(contentPane);
    }

    private void logoutActionHandler() {
        LogoutOperation logoutOp = new LogoutOperation("6", client.getLoggedInUserToken());
        String json = logoutOp.toJson();
        String responseJson;

        try {
            responseJson = client.getServerConnection().sendToServer(json);
        } catch (IOException e) {
            client.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
            return;
        }

        Response logoutResponse = new Response(responseJson);
        String responseCode = logoutResponse.getResponseCode();
        String message = logoutResponse.getMessage();

        if(responseCode.equals("010")) {
            client.showSuccessMessage(message);
            client.getServerConnection().disconnectAndExit();
        }
        else {
            client.showErrorMessage("Erro ao realizar logout", message);
        }
    }
}
