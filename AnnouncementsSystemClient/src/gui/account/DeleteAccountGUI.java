package gui.account;

import gui.home.HomeGUI;
import main.Client;

import javax.swing.JOptionPane;
import java.io.IOException;

public class DeleteAccountGUI {
    private final Client client;

    public DeleteAccountGUI(Client client) {
        this.client = client;
        determineUserId();
    }

    private void determineUserId() {
        String userId = client.getLoggedInUserId();

        if (client.isAdmin()) {
            String message = "Digite o usuário da conta que deseja manipular.\nDeixe em branco para manipular a própria conta.";
            userId = JOptionPane.showInputDialog(message);

            if (userId == null)
                return;

            if (userId.isBlank())
                userId = client.getLoggedInUserId();
        }

        deleteAccountActionHandler(userId);
    }

    private void deleteAccountActionHandler(String accountId) {
        int input = client.showConfirmationPrompt("Excluir a conta " + accountId + "?", "Essa ação é IRREVERSÍVEL.");

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

        //if response successful
        client.getServerConnection().disconnect();
    }
}
