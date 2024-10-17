package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		int port = 23456;
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} 
	    catch(IOException e) {
	    	System.out.printf("Falha ao escutar na porta %d.\n", port);
	    	System.exit(1);
	    }
		
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
		}
		catch(IOException e) {
			System.out.println("Falha ao aceitar conexão com o cliente.");
			System.exit(1);
		}
		
		System.out.println("Conexão estabelecida. Aguardando cliente...");
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String inputLine;
		try {
			while((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				out.println(inputLine.toUpperCase());
				
				if(inputLine.equals("Bye")) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String echo(String msg) {
		return msg.toUpperCase();
	}
}
