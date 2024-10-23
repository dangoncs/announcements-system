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
	    try {
	    	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	    	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	
	    	String inputLine;
	    	System.out.println("INFO: Aguardando cliente...");
	    	while ((inputLine = in.readLine()) != null) {
	    		if (inputLine.equals("bye")) break;
	    		
	    		System.out.println("Cliente diz: " + inputLine);
	    		out.println(this.echo(inputLine));
	    		System.out.println("INFO: Aguardando cliente...");
	    	}
	    	
	    	out.close();
	    	in.close();
	    	clientSocket.close();
	    	System.out.println("INFO: Conexão fechada pelo cliente.");
	    }
	    catch (IOException e) {
	    	System.err.println("ERRO: Problema na comunicação: " + e.getMessage());
	    	System.exit(1);
	    }
	}
}
