package gui.announcementcategory;

import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateCategoryGUI {
    private final Client client;
    private final ClientWindow clientWindow;

    public UpdateCategoryGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        setupGUI();
    }

    private void setupGUI() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        JLabel lblWindowTitle = new JLabel("Atualizar dados da categoria: ");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client, clientWindow));
        buttons.add(btnBack);

        JButton btnUpdate = new JButton("Atualizar");
        btnUpdate.addActionListener(_ -> updateCategoryActionHandler());
        buttons.add(btnUpdate);

        clientWindow.showContentPane(contentPane);
    }

    private void updateCategoryActionHandler() {

    }
}
