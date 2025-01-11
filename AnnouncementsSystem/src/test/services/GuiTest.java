package test.services;

import client.Client;
import client.gui.authentication.ClientLoginGUI;
import server.Server;

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
                Client client = new Client();
                client.getServerConnection().start(hostname, port);
                new ClientLoginGUI(client).setup();
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
        });
    }
}
