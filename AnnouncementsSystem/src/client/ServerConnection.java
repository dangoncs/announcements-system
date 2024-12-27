package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader stdIn;

	public ServerConnection() {
		this.socket = null;
		this.out = null;
		this.in = null;
		this.stdIn = null;
	}

	public void start(String serverHostname, int serverPort) throws IOException {
        System.out.printf("Conectando: %s/%d%n", serverHostname, serverPort);

		socket = new Socket(serverHostname, serverPort);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		stdIn = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("INFO: Conexão estabelecida com sucesso!");
    }

	public String sendToServer(String input) throws IOException {
		out.println(input);
        System.out.printf("Enviando: %s%n", input);

		String serverResponse;
		serverResponse = in.readLine();
		return serverResponse;
	}

	public void disconnectAndExit() {
		try {
			if(stdIn != null) stdIn.close();
			if(in != null) in.close();
			if(out != null) out.close();
			if(socket != null) socket.close();
			System.exit(0);
		} catch (IOException e) {
			System.exit(1);
		}
	}
}
