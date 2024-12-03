package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.responses.Response;
import server.services.AccountService;
import server.services.LoginService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private final Socket clientSocket;
	private final LoginService loginService;

	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.loginService = new LoginService();
	}

	public ServerThread() {
		this.clientSocket = null;
		this.loginService = new LoginService();
	}
	
	public void run() {
		System.out.println("INFO: Conexão estabelecida com novo cliente.");
	    try {
	    	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("0")) break;

				String responseJson = processJson(inputLine);
				System.out.println("Resposta: " + responseJson);
				out.println(responseJson);
	    	}
	    	
	    	out.close();
	    	in.close();
	    	clientSocket.close();
	    	System.out.println("INFO: Conexão fechada por um cliente.");
	    }
	    catch (IOException e) {
	    	System.err.println("ERRO: Problema na comunicação com um cliente: " + e.getMessage());
	    }
	}

	public String processJson(String inputLine) {
		System.out.println("Recebido: " + inputLine);
		JsonObject receivedJson;

		try {
			receivedJson = JsonParser.parseString(inputLine).getAsJsonObject();
		} catch (Exception e) {
			return new Response(
					"002",
					"Received JSON is invalid"
			).toJson();
		}

		JsonElement opElement = receivedJson.get("op");

		if(opElement == null || opElement.isJsonNull()) {
			return new Response(
					"003",
					"Operation not included in request"
			).toJson();
		}

		String operationCode = opElement.getAsString();
		String responseJson;
        responseJson = switch (operationCode) {
            case "1" -> AccountService.create(receivedJson);
            case "2" -> AccountService.read(receivedJson, loginService);
            case "3" -> AccountService.update(receivedJson, loginService);
            case "4" -> AccountService.delete(receivedJson, loginService);
            case "5" -> loginService.login(receivedJson);
            case "6" -> loginService.logout(receivedJson);
            default -> "{}";
        };

		return responseJson;
	}
}
