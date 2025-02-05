package gui.category;

import entities.Category;
import gui.ClientWindow;
import gui.home.HomeGUI;
import main.Client;
import operations.category.CreateCategoryOperation;
import responses.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateCategoryGUI {
    private final Client client;
    private final ClientWindow clientWindow;
    private JTextField nameField;
    private JTextField descriptionField;
    private JLabel lblCategories;
    private List<Category> categoriesList;

    public CreateCategoryGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        setupGUI();
    }

    private void setupGUI() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 25));

        JLabel lblWindowTitle = new JLabel("Criar categorias");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        contentPane.add(center, BorderLayout.CENTER);

        nameField = new JTextField(15);
        descriptionField = new JTextField(15);
        categoriesList = new ArrayList<>();

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

        lblCategories = new JLabel("Categorias a criar: ");
        lblCategories.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(Box.createVerticalStrut(25));
        center.add(lblCategories);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client, clientWindow));
        buttons.add(btnBack);

        JButton btnFinish = new JButton("Concluir");
        btnFinish.addActionListener(_ -> createCategoryActionHandler());
        buttons.add(btnFinish);

        clientWindow.showContentPane(contentPane);
    }

    private void addCategory() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        Category newCategory = new Category(name, description);
        categoriesList.add(newCategory);

        nameField.setText("");
        descriptionField.setText("");

        lblCategories.setText(lblCategories.getText() + name + ", ");
    }

    private void createCategoryActionHandler() {
        CreateCategoryOperation createCategoryOp = new CreateCategoryOperation(client.getLoggedInUserToken(), categoriesList);
        String responseJson;

        try {
            responseJson = client.sendToServer(createCategoryOp);
        } catch (IOException e) {
            clientWindow.showConnectionErrorHandler(client, e.getLocalizedMessage());
            return;
        }

        Response createCategoryResponse = new Response(responseJson);
        String responseCode = createCategoryResponse.getResponseCode();
        String message = createCategoryResponse.getMessage();

        if(!responseCode.equals("200")) {
            clientWindow.showErrorMessage("Não foi possível cadastrar categorias", message);
            categoriesList.clear();
            lblCategories.setText("Categorias a criar: ");
            return;
        }

        new HomeGUI(client, clientWindow);
        clientWindow.showSuccessMessage(message);
    }
}
