import main.ServerThread;

import java.util.Scanner;

public class JsonTest {

	public static void main(String[] args) {
		// Operation example: {"op":"5","user":"a1234567","password":"abc123"}
		// Response example: {"response":"000", "message":"Successful login", "token":"1234567"}

		Scanner scanner = new Scanner(System.in);

		System.out.print("Digite o JSON: ");
		String inputLine = scanner.nextLine();
		scanner.close();

		String responseJson = new ServerThread().processJson(inputLine);
		System.out.printf("Resposta: %s%n", responseJson);
	}
}
