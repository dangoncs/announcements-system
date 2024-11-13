package client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import client.operations.Operation;

public class Client {
	
	public void connectToServer(String serverHostname, int serverPort) {
		System.out.printf("Conectando: %s/%d\n", serverHostname, serverPort);

		try (Socket echoSocket = new Socket(serverHostname, serverPort);
			 PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
		) {
			System.out.println("INFO: Conexão estabelecida com sucesso!");

			String userInput;
			System.out.print("Login: ");

			while((userInput = stdIn.readLine()) != null) {
				if(userInput.equals("0")) break;
				String userLogin = userInput;

				System.out.print("Senha: ");
				userInput = stdIn.readLine();
				if(userInput == null || userInput.equals("0")) break;
				String userPasswd = userInput;
				
				//Operação de login
				Operation obj = new Operation("2", userLogin, userPasswd);
				Gson gson = new Gson();
				
				// converte objetos Java para JSON e retorna JSON como String
				String json = gson.toJson(obj);
				System.out.println(json);
				out.println(json);
				
				System.out.println("Resposta do servidor: " + in.readLine());
				
				System.out.print("Login: ");
			}
			
			stdIn.close();
			in.close();
			out.close();
			echoSocket.close();
			
			System.out.println("INFO: Conexão com o servidor encerrada.");
		} catch (UnknownHostException e) {
			System.out.println("ERRO: Host não encontrado:");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("ERRO de entrada ou saída:");
			System.out.println(e.getMessage());
		}
	}
	
	private static String readString() {
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		scanner.close();
		return str;
	}
	
	private static int readInt() {
		Scanner scanner = new Scanner(System.in);
		int integer = scanner.nextInt();
		scanner.close();
		return integer;
	}
}
