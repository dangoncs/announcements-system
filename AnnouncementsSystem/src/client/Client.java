package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader stdIn;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		this.socket = null;
		this.out = null;
		this.in = null;
		this.stdIn = null;
	}

	public void connectToServer(String serverHostname, int serverPort) {
        System.out.printf("Conectando: %s/%d%n", serverHostname, serverPort);

        try {
            socket = new Socket(serverHostname, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("INFO: Conexão estabelecida com sucesso!");
        } catch (UnknownHostException e) {
            System.err.println("ERRO: Host não encontrado:");
            System.err.println(e.getLocalizedMessage());
			disconnectAndExit();
        } catch (IOException ioe) {
            System.err.println("ERRO de entrada ou saída:");
            System.err.println(ioe.getLocalizedMessage());
			disconnectAndExit();
        }
    }

	public String sendToServer(String input) {
		if (input.isEmpty() || input.equals("0")) {
			System.out.println("INFO: Encerrando conexão com o servidor.");
			disconnectAndExit();
			return input;
		}

		out.println(input);
		System.out.println("Enviando: " + input);

		String serverResponse = null;
		try {
			serverResponse = in.readLine();
		} catch (IOException ioe) {
			System.err.println("ERRO ao receber resposta do servidor.\n");
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
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}
}
