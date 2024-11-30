package test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.ServerThread;

import java.util.Scanner;

public class JsonTest {

	public static void main(String[] args) {
		// Exemplo de JSON:
		// {"op":"5","user":"a1234567","password":"abc123"}

		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o JSON: ");
		String inputLine = scanner.nextLine();
		String response = new ServerThread().processJson(inputLine);
		System.out.println("Recebido: " + response);
	}
}
