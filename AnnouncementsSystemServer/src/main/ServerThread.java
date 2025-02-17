package main;

import com.google.gson.Gson;
import operations.Operation;
import responses.Response;
import services.AccountService;
import services.CategoryService;
import services.LoginService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private final LoginService loginService;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.loginService = new LoginService();
	}

	public ServerThread() {
		this.loginService = new LoginService();
		this.clientSocket = null;
		this.out = null;
		this.in = null;
	}
	
	public void run() {
		System.out.println("[INFO] Established a new connection.");
	    try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
				System.out.printf("[INFO] Received: %s%n", inputLine);
				Response response = processJson(inputLine);
				String responseJson = new Gson().toJson(response);

                System.out.printf("[INFO] Sending: %s%n", responseJson);
				out.println(responseJson);

				if(loginService.isNoLongerLoggedIn())
					break;
	    	}
	    }
	    catch (IOException e) {
            System.err.printf("[WARNING] A problem happened while communicating with a client: %s%n", e.getLocalizedMessage());
	    }
		finally {
			closeConnection();
		}
	}

	public Response processJson(String operationJson) {
		Operation operation = new Gson().fromJson(operationJson, Operation.class);
		
		String operationCode = operation.op();

        return switch (operationCode) {
			case "1" -> AccountService.create(operationJson);
			case "2" -> AccountService.read(operationJson, loginService);
			case "3" -> AccountService.update(operationJson, loginService);
			case "4" -> AccountService.delete(operationJson, loginService);
			case "5" -> loginService.login(operationJson);
			case "6" -> loginService.logout(operationJson);
            case "7" -> CategoryService.create(operationJson, loginService);
            case "8" -> CategoryService.read(operationJson, loginService);
            case "9" -> CategoryService.update(operationJson, loginService);
            case "10" -> CategoryService.delete(operationJson, loginService);
            case null, default -> new Response("500", "Internal Server Error");
        };
	}

	public void closeConnection() {
		try {
			if(out != null) out.close();
			if(in != null) in.close();
			if(clientSocket != null) clientSocket.close();
			System.out.println("[INFO] Connection with a client has been closed.");
		} catch (Exception e) {
            System.err.printf("[WARNING] Could not close connection with a client correctly: %s%n", e.getLocalizedMessage());
			out = null;
			in = null;
			clientSocket = null;
			System.err.println("[WARNING] Forced close.");
		}
	}
}
