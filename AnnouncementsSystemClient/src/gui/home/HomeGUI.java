package gui.home;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import gui.Window;
import gui.account.DeleteAccountGUI;
import gui.account.ReadAccountGUI;
import gui.account.UpdateAccountGUI;
import gui.announcement.CreateAnnouncementGUI;
import gui.announcement.DeleteAnnouncementGUI;
import gui.announcement.ReadAnnouncementsGUI;
import gui.announcement.UpdateAnnouncementGUI;
import gui.authentication.LogoutGUI;
import gui.category.CreateCategoryGUI;
import gui.category.DeleteCategoryGUI;
import gui.category.ReadCategoryGUI;
import gui.category.SubscribeToCategoryGUI;
import gui.category.UnsubscribeFromCategoryGUI;
import gui.category.UpdateCategoryGUI;
import main.Client;

public class HomeGUI extends JPanel {
    private final Client client;
    private final Window window;

    public HomeGUI(Client client, Window window) {
        this.client = client;
        this.window = window;

        setupGUI();
    }

    private void setupGUI() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(5, 5));

        String windowTitle = (client.getUserData().isAdmin()) ? "Painel do Admin" : "Opções";
        JLabel lblWindowTitle = new JLabel(windowTitle);
        lblWindowTitle.setFont(new Font("Tahoma", Font.BOLD, 20));

        JPanel accountPanel = setupAccountPanel();
        JPanel categoryPanel = setupCategoryPanel();
        JPanel announcementPanel = setupAnnouncementPanel();
        JPanel subscriptionPanel = setupSubscriptionPanel();

        JPanel center = new JPanel();
        center.setBorder(new EmptyBorder(5, 5, 5, 5));
        center.add(accountPanel);
        center.add(announcementPanel);
        center.add(categoryPanel);
        center.add(subscriptionPanel);

        JButton btnLogout = new JButton("Fazer logout e desconectar");
        btnLogout.addActionListener(_ -> window.showContentPane(new LogoutGUI(client, window)));

        add(lblWindowTitle, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(btnLogout, BorderLayout.SOUTH);
    }

    private JPanel setupAccountPanel() {
        JButton btnReadAccount = new JButton("Ver dados");
        btnReadAccount.addActionListener(_ -> window.showContentPane(new ReadAccountGUI(client, window)));

        JButton btnUpdateAccount = new JButton("Atualizar dados");
        btnUpdateAccount.addActionListener(_ -> window.showContentPane(new UpdateAccountGUI(client, window)));

        JButton btnDeleteAccount = new JButton("Excluir");
        btnDeleteAccount.addActionListener(_ -> window.showContentPane(new DeleteAccountGUI(client, window)));

        JPanel accountPanel = new JPanel();
        accountPanel.setBorder(
                new TitledBorder(null, "Conta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        accountPanel.add(btnReadAccount);
        accountPanel.add(btnUpdateAccount);
        accountPanel.add(btnDeleteAccount);

        return accountPanel;
    }

    private JPanel setupAnnouncementPanel() {
        JButton btnReadAnnouncements = new JButton("Ver");
        btnReadAnnouncements.addActionListener(_ -> window.showContentPane(new ReadAnnouncementsGUI(client, window)));

        JButton btnCreateAnnouncement = new JButton("Criar");
        btnCreateAnnouncement.addActionListener(_ -> window.showContentPane(new CreateAnnouncementGUI(client, window)));

        JButton btnUpdateAnnouncement = new JButton("Atualizar");
        btnUpdateAnnouncement.addActionListener(_ -> window.showContentPane(new UpdateAnnouncementGUI(client, window)));

        JButton btnDeleteAnnouncement = new JButton("Excluir");
        btnDeleteAnnouncement.addActionListener(_ -> window.showContentPane(new DeleteAnnouncementGUI(client, window)));

        JPanel announcementPanel = new JPanel();
        announcementPanel.setBorder(
                new TitledBorder(null, "Avisos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        announcementPanel.add(btnReadAnnouncements);

        if (client.getUserData().isAdmin()) {
            announcementPanel.add(btnCreateAnnouncement);
            announcementPanel.add(btnUpdateAnnouncement);
            announcementPanel.add(btnDeleteAnnouncement);
        }

        return announcementPanel;
    }

    private JPanel setupCategoryPanel() {
        JButton btnCreateCategory = new JButton("Criar");
        btnCreateCategory.addActionListener(_ -> window.showContentPane(new CreateCategoryGUI(client, window)));

        JButton btnReadCategory = new JButton("Ver");
        btnReadCategory.addActionListener(_ -> window.showContentPane(new ReadCategoryGUI(client, window)));

        JButton btnUpdateCategory = new JButton("Atualizar");
        btnUpdateCategory.addActionListener(_ -> window.showContentPane(new UpdateCategoryGUI(client, window)));

        JButton btnDeleteCategory = new JButton("Excluir");
        btnDeleteCategory.addActionListener(_ -> window.showContentPane(new DeleteCategoryGUI(client, window)));

        JPanel categoryPanel = new JPanel();
        categoryPanel.setBorder(
                new TitledBorder(null, "Categoria de Avisos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        categoryPanel.add(btnReadCategory);

        if (client.getUserData().isAdmin()) {
            categoryPanel.add(btnCreateCategory);
            categoryPanel.add(btnUpdateCategory);
            categoryPanel.add(btnDeleteCategory);
        }

        return categoryPanel;
    }

    private JPanel setupSubscriptionPanel() {
        JButton btnSubscribe = new JButton("Inscrever-se em uma categoria");
        btnSubscribe.addActionListener(_ -> window.showContentPane(new SubscribeToCategoryGUI(client, window)));

        JButton btnUnsubscribe = new JButton("Desinscrever-se de uma categoria");
        btnUnsubscribe.addActionListener(_ -> window.showContentPane(new UnsubscribeFromCategoryGUI(client, window)));

        JPanel subscriptionPanel = new JPanel();
        subscriptionPanel.setBorder(
                new TitledBorder(null, "Inscrições", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        subscriptionPanel.add(btnSubscribe);
        subscriptionPanel.add(btnUnsubscribe);

        return subscriptionPanel;
    }
}
