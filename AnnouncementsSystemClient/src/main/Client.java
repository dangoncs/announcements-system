package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import entities.User;
import gui.Window;
import operations.Operation;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private User userData;

	public static void main(String[] ignoredArgs) {
		SwingUtilities.invokeLater(Window::new);
	}

	public void startConnection(String serverHostname, int serverPort) throws IOException {
		socket = new Socket(serverHostname, serverPort);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public String sendToServer(Operation operation) throws IOException {
		String json = new Gson().toJson(operation);

		System.out.printf("[INFO] Sending: %s%n", json);
		out.println(json);

		String serverResponse = in.readLine();
		System.out.printf("[INFO] Received: %s%n", serverResponse);
		return serverResponse;
	}

	public void disconnect() throws IOException {
		if (in != null)
			in.close();
		if (out != null)
			out.close();
		if (socket != null)
			socket.close();
	}

	public User getUserData() {
		return userData;
	}

	public void setUserData(User userData) {
		this.userData = userData;
	}
}
