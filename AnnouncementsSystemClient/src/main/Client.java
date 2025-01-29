package main;

import com.google.gson.Gson;
import gui.ClientWindow;
import operations.Operation;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String loggedInUserToken;
	private String loggedInUserId;
	private boolean isAdmin;

	public static void main(String[] ignoredArgs) {
		SwingUtilities.invokeLater(ClientWindow::new);
	}

	public void startConnection(String serverHostname, int serverPort) throws IOException {
		socket = new Socket(serverHostname, serverPort);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

	public String sendToServer(Operation operation) throws IOException {
		String json = new Gson().toJson(operation);
		System.out.printf("[INFO] Enviando: %s%n", json);
		out.println(json);

		String serverResponse = in.readLine();
		System.out.printf("[INFO] Recebido: %s%n", serverResponse);
		return serverResponse;
	}

	public void disconnect() throws IOException {
		if(in != null) in.close();
		if(out != null) out.close();
		if(socket != null) socket.close();
	}

	public String getLoggedInUserToken() {
		return loggedInUserToken;
	}

	public String getLoggedInUserId() {
		return "";
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setLoggedInUserToken(String loggedInUserToken) {
		this.loggedInUserToken = loggedInUserToken;
	}

	public void setLoggedInUserId(String loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	public void setAdmin(boolean admin) {
		this.isAdmin = admin;
	}
}
