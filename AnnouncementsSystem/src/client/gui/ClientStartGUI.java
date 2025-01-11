package client.gui;

import client.Client;
import client.gui.account.ClientCreateAccountGUI;
import client.gui.authentication.ClientLoginGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientStartGUI {
    private final Client client;

    public ClientStartGUI(Client client) {
        this.client = client;
    }

    public void setup() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Bem-vindo(a)");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 440, 23);
        contentPane.add(lblWindowTitle);

        JButton btnSignup = new JButton("Cadastro");
        btnSignup.setBounds(5, 120, 424, 23);
        btnSignup.addActionListener(_ ->
                new ClientCreateAccountGUI(client).setup()
        );
        contentPane.add(btnSignup);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(5, 150, 424, 23);
        btnLogin.addActionListener(_ ->
                new ClientLoginGUI(client).setup()
        );
        contentPane.add(btnLogin);

        client.showContentPane(contentPane);
    }
}
