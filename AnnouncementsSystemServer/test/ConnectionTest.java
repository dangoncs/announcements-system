import main.ServerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectionTest {

    public static void main(String[] args) throws IOException {
        new Thread(ConnectionTest::preconfiguredServer).start();
        preconfiguredClient();
    }

    private static void preconfiguredServer() {
        int port = 24680;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Thread thread = new ServerThread(serverSocket.accept());
            thread.start();

            thread.join();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void preconfiguredClient() throws IOException {
        String serverHostname = "localhost";
        int serverPort = 24680;

        List<String> input = JsonTest.scanJsonFromFile("test/json.txt");

        try (Socket socket = new Socket(serverHostname, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            for(String line : input) {
                out.println(line);
                in.readLine();
            }
        }
    }
}
