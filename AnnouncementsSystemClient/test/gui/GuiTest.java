package gui;

import gui.account.UpdateAccountGUI;
import main.Client;

import javax.swing.SwingUtilities;

public class GuiTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateAccountGUI(new Client(), new ClientWindow()));
    }
}
