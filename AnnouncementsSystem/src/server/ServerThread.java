package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
		this.start();
	}
	
	public void run() {
		System.out.println("INFO: Conexão estabelecida com novo cliente.");
	    try {
	    	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("0")) break;

				JsonObject receivedJson = JsonParser.parseString(inputLine).getAsJsonObject();
				System.out.println("Recebido: " + receivedJson);

				String responseJson = "{}";

				String operationCode = receivedJson.get("op").getAsString();
				switch(operationCode) {
					case "1":
						responseJson = AccountService.create(receivedJson);
						break;
					case "2":
						responseJson = AccountService.read(receivedJson, loginService);
						break;
					case "3":
						responseJson = AccountService.update(receivedJson, loginService);
						break;
					case "4":
						responseJson = AccountService.delete(receivedJson, loginService);
						break;
					case "5":
						responseJson = loginService.login(receivedJson);
						break;
					case "6":
						responseJson = loginService.logout(receivedJson);
						break;
					default:
						System.err.println("OPERAÇÃO NÃO RECONHECIDA");
				}

				System.out.println("Enviando: " + responseJson);
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
}
