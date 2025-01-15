package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public ServerConnection() {
		this.socket = null;
		this.out = null;
		this.in = null;
	}

	public void start(String serverHostname, int serverPort) throws IOException {
		socket = new Socket(serverHostname, serverPort);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

	public String sendToServer(String input) throws IOException {
		out.println(input);
        System.out.printf("[INFO] Enviando: %s%n", input);

		String serverResponse = in.readLine();
		System.out.printf("[INFO] Recebido: %s%n", serverResponse);
		return serverResponse;
	}

	public void disconnect() {
		try {
			if(in != null) in.close();
			if(out != null) out.close();
			if(socket != null) socket.close();
		} catch (IOException e) {
			System.err.printf("[ERRO] Não foi possível fechar corretamente: %s%n", e.getLocalizedMessage());
			System.err.println("[AVISO] Saída forçada");
			System.exit(1);
		}
	}
}
