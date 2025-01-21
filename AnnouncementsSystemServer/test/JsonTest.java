import main.ServerThread;
import responses.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonTest {

	public static void main(String[] args) throws IOException {
		String input;

		do {
			System.out.print("Digite o JSON: ");

			try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
				input = stdIn.readLine();
			}

			Response response = new ServerThread().processJson(input);
			System.out.printf("Resposta: %s%n", response);
		} while (input != null && !input.equalsIgnoreCase("exit"));
	}

	public static List<String> scanJsonFromFile(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();

		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			String line;
			while((line = in.readLine()) != null)
				lines.add(line);
		}

		return lines;
	}
}
