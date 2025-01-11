package test.services;

import client.ServerConnection;
import client.gui.ClientGUI;
import client.gui.ClientLoginGUI;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;

public class GuiTest {

    public static void main(String[] ignoredArgs) {
        Server server = new Server();
        String hostname = "127.0.0.1";
        int port = 20000;

        new Thread(() -> {
            try (ServerSocket _ = server.createSocket(port)) {
                server.startConnectionLoop();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
                System.exit(1);
            }
        }).start();

        EventQueue.invokeLater(() -> {
            try {
                ServerConnection serverConnection = new ServerConnection();

                ClientGUI frame = new ClientGUI(serverConnection);
                frame.setVisible(true);

                serverConnection.start(hostname, port);

                JPanel contentPane = new ClientLoginGUI(serverConnection, frame).setup();
                frame.changeContentPane(contentPane);
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
        });
    }
}
