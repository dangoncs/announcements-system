package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private Socket clientSocket;
	
	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
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

				//Converter String JSON para objeto Java
				JsonObject jsonObject = JsonParser.parseString(inputLine).getAsJsonObject();
				System.out.println("Recebido: " + jsonObject);

				//Realizar a operação conforme código recebido
				String operationCode = jsonObject.get("op").getAsString();
				switch(operationCode) {
					case "5":
						System.out.println("OPERAÇÃO LOGIN");
						break;
					case "6":
						System.out.println("OPERAÇÃO LOGOUT");
						break;
					default:
						System.err.println("OPERAÇÃO NÃO RECONHECIDA");
				}
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
