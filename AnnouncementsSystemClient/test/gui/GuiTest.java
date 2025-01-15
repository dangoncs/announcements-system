package gui;

import gui.account.UpdateAccountGUI;
import main.Client;

import javax.swing.SwingUtilities;

public class GuiTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            new UpdateAccountGUI(client).setup();
        });
    }
}
