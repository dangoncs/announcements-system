package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	protected Socket clientSocket;
	
	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.start();
	}
	
	private String echo(String msg) {
		return msg.toUpperCase();
	}
	
	public void run() {
		System.out.println("INFO: Conexão estabelecida com novo cliente.");
	    try {
	    	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
	    		if (inputLine.equals("0")) break;
	    		out.println(this.echo(inputLine));
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
