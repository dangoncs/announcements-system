package gui.category;

import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;
import operations.category.UpdateCategoryOperation;
import responses.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entities.Category;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateCategoryGUI {
    private final Client client;
    private final ClientWindow clientWindow;
    private JTextField categoryIdField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JLabel lblCategories;
    private List<Category> categoriesList;

    public UpdateCategoryGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        setupGUI();
    }

    private void setupGUI() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        JLabel lblWindowTitle = new JLabel("Atualizar dados de categorias");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        contentPane.add(center, BorderLayout.CENTER);

        categoryIdField = new JTextField(15);
        nameField = new JTextField(15);
        descriptionField = new JTextField(15);
        categoriesList = new ArrayList<>();

        JPanel categoryIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryIdPanel.add(new JLabel("Id:"));
        categoryIdPanel.add(categoryIdField);
        categoryIdPanel.setAlignmentX(Panel.LEFT_ALIGNMENT);
        center.add(categoryIdPanel);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Nome:"));
        namePanel.add(nameField);
        namePanel.setAlignmentX(Panel.LEFT_ALIGNMENT);
        center.add(namePanel);

        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionPanel.add(new JLabel("Descrição:"));
        descriptionPanel.add(descriptionField);
        descriptionPanel.setAlignmentX(Panel.LEFT_ALIGNMENT);
        center.add(descriptionPanel);

        JButton btnAdd = new JButton("Adicionar Categoria");
        btnAdd.addActionListener(_ -> addCategory());
        btnAdd.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(btnAdd);

        lblCategories = new JLabel("Categorias a atualizar: ");
        lblCategories.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(Box.createVerticalStrut(25));
        center.add(lblCategories);

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

    private void addCategory() {
        String categoryId = categoryIdField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        Category newCategory = new Category(categoryId, name, description);
        categoriesList.add(newCategory);

        categoryIdField.setText("");
        nameField.setText("");
        descriptionField.setText("");

        lblCategories.setText(lblCategories.getText() + categoryId + ", ");
    }

    private void updateCategoryActionHandler() {
        UpdateCategoryOperation createCategoryOp = new UpdateCategoryOperation(client.getLoggedInUserToken(), categoriesList);
        String responseJson;

        try {
            responseJson = client.sendToServer(createCategoryOp);
        } catch (IOException e) {
            clientWindow.showConnectionErrorHandler(client, e.getLocalizedMessage());
            return;
        }

        Response updateCategoryResponse = new Response(responseJson);
        String responseCode = updateCategoryResponse.getResponseCode();
        String message = updateCategoryResponse.getMessage();

        if(!responseCode.equals("220")) {
            clientWindow.showErrorMessage("Não foi possível atualizar categorias", message);
            categoriesList.clear();
            lblCategories.setText("Categorias a atualizar: ");
            return;
        }

        new HomeGUI(client, clientWindow);
        clientWindow.showSuccessMessage(message);
    }
}
