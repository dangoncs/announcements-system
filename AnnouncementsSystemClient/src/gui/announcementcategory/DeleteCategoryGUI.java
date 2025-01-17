package gui.announcementcategory;

import gui.home.HomeGUI;
import main.Client;

import javax.swing.*;
import java.io.IOException;

public class DeleteCategoryGUI {
    private final Client client;

    public DeleteCategoryGUI(Client client) {
        this.client = client;
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
        int input = client.showConfirmationPrompt("Excluir a categoria " + categoryId + "?", "Essa ação é IRREVERSÍVEL.");

        if(input != 0) {
            new HomeGUI(client);
            return;
        }

        //TODO: implement implementation of deletion lol
        try {
            client.getServerConnection().sendToServer("");
        } catch (IOException e) {
            client.showErrorMessage("", e.getLocalizedMessage());
        }
    }
}
