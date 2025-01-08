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
		System.out.println("INFO: Conexão estabelecida com novo cliente.");
	    try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
				System.out.printf("Recebido: %s%n", inputLine);
				String responseJson = processJson(inputLine);
                System.out.printf("Resposta: %s%n", responseJson);
				out.println(responseJson);

				if(shouldCloseConnection(responseJson))
					break;
	    	}

			System.out.println("INFO: Conexão com um cliente fechada.");
	    	closeConnection();
	    }
	    catch (IOException e) {
            System.err.printf("ERRO: Problema na comunicação com um cliente: %s%n", e.getLocalizedMessage());
	    }
	}

	public String processJson(String inputLine) {
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

	private boolean shouldCloseConnection(String responseJson) {
		JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
		JsonElement responseElement = jsonObject.get("response");
		return (responseElement != null) && (responseElement.getAsString().equals("010"));
    }

	public void closeConnection() {
		try {
			if(out != null) out.close();
			if(in != null) in.close();
			if(clientSocket != null) clientSocket.close();
		} catch (Exception e) {
            System.err.printf("Erro ao fechar conexão com o cliente: %s%n", e.getLocalizedMessage());
		}
	}
}
