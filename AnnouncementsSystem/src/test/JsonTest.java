package test;

import server.ServerThread;

import java.util.Scanner;

public class JsonTest {

	public static void main(String[] args) {
		// Exemplo de JSON:
		// {"op":"5","user":"a1234567","password":"abc123"}

		//TODO: implement protections against null values and invalid JSON

		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o JSON: ");
		String inputLine = scanner.nextLine();
		String responseJson = new ServerThread().processJson(inputLine);
		System.out.println("Resposta: " + responseJson);
	}
}
