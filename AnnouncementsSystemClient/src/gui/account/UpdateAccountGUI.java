package gui.account;

import gui.UserHomeGUI;
import main.Client;
import operations.account.CreateAccountOperation;
import responses.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class UpdateAccountGUI {
    private final Client client;
    private JTextField txtUserId;
    private JTextField txtName;
    private JTextField txtPasswd;

    public UpdateAccountGUI(Client client) {
        this.client = client;
    }

    public JPanel setup() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel lblWindowTitle = new JLabel("Insira os dados da conta:");
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWindowTitle.setBounds(5, 5, 424, 23);
        contentPane.add(lblWindowTitle);

        JLabel lblSubtitle = new JLabel("Campos em branco não serão atualizados.");
        lblSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSubtitle.setBounds(5, 30, 424, 23);
        contentPane.add(lblSubtitle);

        JLabel lblUserId = new JLabel("Usuário:");
        lblUserId.setBounds(5, 73, 135, 23);
        contentPane.add(lblUserId);

        txtUserId = new JTextField();
        txtUserId.setBounds(150, 73, 100, 23);
        contentPane.add(txtUserId);
        txtUserId.setColumns(10);

        JLabel lblName = new JLabel("Nome:");
        lblName.setBounds(5, 116, 135, 23);
        contentPane.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 116, 100, 23);
        contentPane.add(txtName);
        txtName.setColumns(10);

        JLabel lblPasswd = new JLabel("Senha:");
        lblPasswd.setBounds(5, 159, 135, 23);
        contentPane.add(lblPasswd);

        txtPasswd = new JTextField();
        txtPasswd.setBounds(150, 159, 100, 23);
        contentPane.add(txtPasswd);
        txtPasswd.setColumns(10);

        JButton btnSignup = new JButton("Cadastrar");
        btnSignup.setBounds(5, 233, 424, 23);
        btnSignup.addActionListener(_ -> updateAccountActionHandler());
        contentPane.add(btnSignup, BorderLayout.SOUTH);

        return contentPane;
    }

    private void updateAccountActionHandler() {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String passwd = txtPasswd.getText();

        CreateAccountOperation createAccountOp = new CreateAccountOperation("1", userId, passwd, name);
        String json = createAccountOp.toJson();
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

        new UserHomeGUI(client).setup();
    }
}
