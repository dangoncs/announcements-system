package gui.announcementcategory;

import gui.home.HomeGUI;
import main.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateCategoryGUI {
    private final Client client;

    public CreateCategoryGUI(Client client) {
        this.client = client;
        setupGUI();
    }

    private void setupGUI() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        JLabel lblWindowTitle = new JLabel("Criar categoria");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client));
        buttons.add(btnBack);

        JButton btnCreate = new JButton("Criar");
        btnCreate.addActionListener(_ -> createCategoryActionHandler());
        buttons.add(btnCreate);

        client.showContentPane(contentPane);
    }

    private void createCategoryActionHandler() {
    }
}
