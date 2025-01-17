package gui.announcementcategory;

import gui.home.HomeGUI;
import main.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReadCategoryGUI {
    private final Client client;

    public ReadCategoryGUI(Client client) {
        this.client = client;
        readCategoriesActionHandler();
    }

    private void readCategoriesActionHandler() {
        setupGUI();
    }

    private void setupGUI() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        JLabel lblWindowTitle = new JLabel("Categorias");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client));
        buttons.add(btnBack);

        client.showContentPane(contentPane);
    }


}
