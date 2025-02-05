package gui.category;

import com.google.gson.JsonElement;
import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;
import operations.category.ReadCategoryOperation;
import responses.category.ReadCategoryResponse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ReadCategoryGUI {
    private final Client client;
    private final ClientWindow clientWindow;

    public ReadCategoryGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        readCategoriesActionHandler();
    }

    private void readCategoriesActionHandler() {
        ReadCategoryOperation readCategoryOp = new ReadCategoryOperation(client.getLoggedInUserToken());
        String responseJson;

        try {
            responseJson = client.sendToServer(readCategoryOp);
        } catch (IOException e) {
            clientWindow.showConnectionErrorHandler(client, e.getLocalizedMessage());
            return;
        }

        ReadCategoryResponse readCategoryResponse = new ReadCategoryResponse(responseJson);
        String responseCode = readCategoryResponse.getResponseCode();

        if(!responseCode.equals("210")) {
            clientWindow.showErrorMessage("Não foi possível ler categorias", readCategoryResponse.getMessage());
            return;
        }

        StringBuilder categoriesHtml = new StringBuilder("<html>");

        List<JsonElement> categories = readCategoryResponse.getCategories();
        for (JsonElement category : categories) {
            categoriesHtml.append(category).append("<br>");
        }

        categoriesHtml.append("</html>");

        setupGUI(categoriesHtml.toString());
    }

    private void setupGUI(String categories) {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        JLabel lblWindowTitle = new JLabel("Categorias");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JLabel lblCategories = new JLabel(categories);
        contentPane.add(lblCategories, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client, clientWindow));
        buttons.add(btnBack);

        clientWindow.showContentPane(contentPane);
    }
}
