package gui.announcementcategory;

import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;
import operations.Operation;

import javax.swing.*;
import java.io.IOException;

public class DeleteCategoryGUI {
    private final Client client;
    private final ClientWindow clientWindow;

    public DeleteCategoryGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

        determineCategoryId();
    }

    private void determineCategoryId() {
        String message = "Digite o ID da categoria que deseja manipular.";
        String categoryId  = JOptionPane.showInputDialog(message);

        if (categoryId == null || categoryId.isBlank())
            return;

        deleteCategoryActionHandler(categoryId);
    }

    private void deleteCategoryActionHandler(String categoryId) {
        int input = clientWindow.showConfirmationPrompt("Excluir a categoria " + categoryId + "?", "Essa ação é IRREVERSÍVEL.");

        if(input != 0) {
            new HomeGUI(client, clientWindow);
            return;
        }

        Operation deleteCategoryOp = new Operation("CHANGE ME to DeleteCategoryOperation");

        //TODO: implement implementation of deletion lol
        try {
            client.sendToServer(deleteCategoryOp);
        } catch (IOException e) {
            clientWindow.showErrorMessage("", e.getLocalizedMessage());
        }
    }
}
