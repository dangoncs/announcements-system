package gui;

import main.Client;

import javax.swing.SwingUtilities;

public class GuiTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientWindow().showConnectionErrorHandler(new Client(), "Test"));
    }
}
