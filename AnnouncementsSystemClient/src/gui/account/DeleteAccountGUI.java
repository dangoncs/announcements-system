package gui.account;

import gui.connection.ConnectionGUI;
import gui.home.HomeGUI;
import gui.ClientWindow;
import main.Client;
import operations.account.DeleteAccountOperation;
import responses.Response;

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

        DeleteAccountOperation deleteAccountOp = new DeleteAccountOperation(accountId, client.getLoggedInUserToken());
        String responseJson;

        try {
            responseJson = client.sendToServer(deleteAccountOp);
        } catch (IOException e) {
            clientWindow.showConnectionErrorHandler(client, e.getLocalizedMessage());
            return;
        }

        Response deleteAccountResponse = new Response(responseJson);
        String responseCode = deleteAccountResponse.getResponseCode();
        String message = deleteAccountResponse.getMessage();

        if (!responseCode.equals("130")) {
            clientWindow.showErrorMessage("Não foi possível excluir conta", message);
            return;
        }

        clientWindow.showSuccessMessage(message);

        if (accountId.equals(client.getLoggedInUserId())) {
            try {
                client.disconnect();
            } catch (IOException e) {
                clientWindow.showErrorMessage("Erro ao desconectar", e.getLocalizedMessage());
                System.exit(1);
            }
            
            new ConnectionGUI(clientWindow);
        }
    }
}
