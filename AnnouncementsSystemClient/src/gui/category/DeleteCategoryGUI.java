package gui.category;

import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;
import operations.category.DeleteCategoryOperation;
import responses.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCategoryGUI {
    private final Client client;
    private final ClientWindow clientWindow;
    private JTextField txtCategoryId;
    private JLabel lblCategories;
    private List<String> categoryIdsList;

    public DeleteCategoryGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        setupGUI();
    }

    private void setupGUI() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 25));

        JLabel lblWindowTitle = new JLabel("Excluir categorias");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        contentPane.add(center, BorderLayout.CENTER);

        txtCategoryId = new JTextField(15);
        categoryIdsList = new ArrayList<>();

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Id da categoria:"));
        namePanel.add(txtCategoryId);
        namePanel.setAlignmentX(Panel.LEFT_ALIGNMENT);
        center.add(namePanel);

        JButton btnAdd = new JButton("Adicionar Categoria");
        btnAdd.addActionListener(_ -> addCategory());
        btnAdd.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(btnAdd);

        lblCategories = new JLabel("Categorias a excluir: ");
        lblCategories.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(Box.createVerticalStrut(25));
        center.add(lblCategories);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client, clientWindow));
        buttons.add(btnBack);

        JButton btnFinish = new JButton("Concluir");
        btnFinish.addActionListener(_ -> deleteCategoryActionHandler());
        buttons.add(btnFinish);

        clientWindow.showContentPane(contentPane);
    }

    private void addCategory() {
        String categoryId = txtCategoryId.getText().trim();
        categoryIdsList.add(categoryId);

        txtCategoryId.setText("");

        lblCategories.setText(lblCategories.getText() + categoryId + ", ");
    }

    private void deleteCategoryActionHandler() {
        String categoriesToDelete = lblCategories.getText().substring(0, lblCategories.getText().length() - 2);

        int input = clientWindow.showConfirmationPrompt("Excluir as categorias?", "Essa ação é IRREVERSÍVEL. " + categoriesToDelete + ".");
        if(input != 0) {
            new HomeGUI(client, clientWindow);
            return;
        }

        DeleteCategoryOperation deleteCategoryOp = new DeleteCategoryOperation(client.getLoggedInUserToken(), categoryIdsList);
        String responseJson;

        try {
            responseJson = client.sendToServer(deleteCategoryOp);
        } catch (IOException e) {
            clientWindow.showConnectionErrorHandler(client, e.getLocalizedMessage());
            return;
        }

        Response deleteCategoryResponse = new Response(responseJson);
        String responseCode = deleteCategoryResponse.getResponseCode();
        String message = deleteCategoryResponse.getMessage();

        if(!responseCode.equals("230")) {
            clientWindow.showErrorMessage("Não foi possível excluir categorias", message);
            categoryIdsList.clear();
            lblCategories.setText("Categorias a excluir: ");
            return;
        }

        new HomeGUI(client, clientWindow);
        clientWindow.showSuccessMessage(message);
    }
}
