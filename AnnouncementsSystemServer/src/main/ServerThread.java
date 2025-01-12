package main;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import responses.Response;
import services.AccountService;
import services.LoginService;

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
		System.out.println("[INFO] Conexão estabelecida com novo cliente.");
	    try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
				System.out.printf("[INFO] Recebido: %s%n", inputLine);
				String responseJson = processJson(inputLine);
                System.out.printf("[INFO] Enviando: %s%n", responseJson);
				out.println(responseJson);

				if(shouldCloseConnection(responseJson))
					break;
	    	}
			
	    	closeConnection();
	    }
	    catch (IOException e) {
            System.err.printf("[AVISO] Problema na comunicação com um cliente: %s%n", e.getLocalizedMessage());
	    	closeConnection();
	    }
	}

	public String processJson(String inputLine) {
		JsonObject receivedJson;

		try {
			receivedJson = JsonParser.parseString(inputLine).getAsJsonObject();
		} catch (Exception e) {
			Response response = new Response(
					"001",
					"Received JSON could not be processed"
			);
			return new Gson().toJson(response);
		}

		JsonElement opElement = receivedJson.get("op");

		if(opElement == null || opElement.isJsonNull()) {
			Response response = new Response("002","Operation not included in request");
			return new Gson().toJson(response);
		}

		String operationCode = opElement.getAsString();
		Response response = switch (operationCode) {
            case "1" -> AccountService.create(receivedJson);
            case "2" -> AccountService.read(receivedJson, loginService);
            case "3" -> AccountService.update(receivedJson, loginService);
            case "4" -> AccountService.delete(receivedJson, loginService);
            case "5" -> loginService.login(receivedJson);
            case "6" -> loginService.logout(receivedJson);
            default -> new Response("003", "Operation code is not recognized");
        };

        return new Gson().toJson(response);
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
			System.out.println("[INFO] Conexão com um cliente fechada.");
		} catch (Exception e) {
            System.err.printf("[AVISO] Não foi possível fechar conexão corretamente com um cliente: %s%n", e.getLocalizedMessage());
			System.err.println("[AVISO] Forçando encerramento da conexão.");
		}
	}
}
