package client;

import client.gui.ClientGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private final ClientGUI clientGUI;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader stdIn;

	public Client(ClientGUI clientGUI) {
		this.socket = null;
		this.out = null;
		this.in = null;
		this.stdIn = null;
		this.clientGUI = clientGUI;
	}

	public void connectToServer(String serverHostname, int serverPort) {
        System.out.printf("Conectando: %s/%d%n", serverHostname, serverPort);

        try {
            socket = new Socket(serverHostname, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("INFO: Conexão estabelecida com sucesso!");
			clientGUI.setupMainGUI(serverHostname, serverPort);
        } catch (UnknownHostException e) {
            clientGUI.showErrorMessage("Host não encontrado", e.getLocalizedMessage());
			disconnectAndExit();
        } catch (IOException e) {
			clientGUI.showErrorMessage("Erro de entrada ou saída", e.getLocalizedMessage());
			disconnectAndExit();
        }
    }

	public String sendToServer(String input) {
		out.println(input);
		System.out.println("Enviando: " + input);

		String serverResponse = "{}";
		try {
			serverResponse = in.readLine();
		} catch (IOException e) {
			clientGUI.showErrorMessage("Erro ao receber resposta do servidor", e.getLocalizedMessage());
			disconnectAndExit();
		}

		return serverResponse;
	}

	public void disconnectAndExit() {
		try {
			if(stdIn != null) stdIn.close();
			if(in != null) in.close();
			if(out != null) out.close();
			if(socket != null) socket.close();
			System.exit(0);
		} catch (Exception e) {
			clientGUI.showErrorMessage("Erro desconhecido", e.getLocalizedMessage());
			System.exit(0);
		}
	}
}
