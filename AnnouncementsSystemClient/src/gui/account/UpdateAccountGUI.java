package gui.account;

import gui.home.HomeGUI;
import main.Client;
import operations.account.UpdateAccountOperation;
import responses.Response;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.BorderLayout;
import java.io.IOException;

public class UpdateAccountGUI {
    private final Client client;
    private JTextField txtName;
    private JTextField txtPasswd;

    public UpdateAccountGUI(Client client) {
        this.client = client;
        determineUserId();
    }

    private void determineUserId() {
        String userId = "";

        if (client.isAdmin()) {
            String message = "Digite o usuário da conta que deseja manipular.\nDeixe em branco para manipular a própria conta.";
            userId = JOptionPane.showInputDialog(message);

            if (userId == null)
                return;

            if (userId.isBlank())
                userId = "";
        }

        setupGUI(userId);
    }

    private void setupGUI(String userId) {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));

        JLabel lblWindowTitle = new JLabel("Atualizando dados da conta: " + userId);
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.add(lblWindowTitle, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(null);
        contentPane.add(center, BorderLayout.CENTER);

        JLabel lblSubtitle = new JLabel("Campos em branco não serão atualizados.");
        lblSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSubtitle.setBounds(5, 30, 424, 23);
        center.add(lblSubtitle);

        JLabel lblName = new JLabel("Novo nome:");
        lblName.setBounds(5, 73, 100, 23);
        center.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(110, 73, 100, 23);
        center.add(txtName);

        JLabel lblPasswd = new JLabel("Nova senha:");
        lblPasswd.setBounds(5, 116, 100, 23);
        center.add(lblPasswd);

        txtPasswd = new JTextField();
        txtPasswd.setBounds(110, 116, 100, 23);
        center.add(txtPasswd);

        JPanel buttons = new JPanel();
        contentPane.add(buttons, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(_ -> new HomeGUI(client));
        buttons.add(btnBack);

        JButton btnUpdate = new JButton("Atualizar");
        btnUpdate.addActionListener(_ -> updateAccountActionHandler(userId));
        buttons.add(btnUpdate);

        client.showContentPane(contentPane);
    }

    private void updateAccountActionHandler(String userId) {
        String name = txtName.getText();
        String passwd = txtPasswd.getText();
        String token = client.getLoggedInUserToken();

        UpdateAccountOperation updateAccountOp = new UpdateAccountOperation(userId, passwd, name, token);
        String json = updateAccountOp.toJson();
        String responseJson;

        try {
            responseJson = client.getServerConnection().sendToServer(json);
        } catch (IOException e) {
            client.showErrorMessage("Erro ao comunicar com o servidor", e.getLocalizedMessage());
            return;
        }

        Response updateAccountResponse = new Response(responseJson);
        String responseCode = updateAccountResponse.getResponseCode();
        String message = updateAccountResponse.getMessage();

        if(responseCode.equals("120"))
            client.showSuccessMessage(message);
        else
            client.showErrorMessage("Erro ao atualizar dados da conta", message);

        new HomeGUI(client).setup();
    }
}
