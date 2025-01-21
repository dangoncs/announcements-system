package gui.account;

import gui.connection.ConnectionGUI;
import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;

import javax.swing.JOptionPane;
import java.io.IOException;

public class DeleteAccountGUI {
    private final Client client;
    private final ClientWindow clientWindow;

    public DeleteAccountGUI(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;

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
        int input = clientWindow.showConfirmationPrompt("Excluir a conta " + accountId + "?", "Essa ação é IRREVERSÍVEL.");

        if(input != 0) {
            new HomeGUI(client, clientWindow);
            return;
        }

        //TODO: implement implementation of deletion lol
        try {
            client.sendToServer("");
        } catch (IOException e) {
            clientWindow.showErrorMessage("", e.getLocalizedMessage());
        }

        //if response successful (130)
        try {
            client.disconnect();
            new ConnectionGUI(clientWindow);
        } catch (IOException e) {
            clientWindow.showErrorMessage("Erro ao desconectar", e.getLocalizedMessage());
            System.exit(1);
        }
    }
}
