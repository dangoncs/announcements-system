import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.ServerThread;

public class JsonTest {

	public static void main(String[] args) throws IOException {
		ServerThread server = new ServerThread(null);

		try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.print("Digite o JSON ou \"0\" para sair: ");
				String input = stdIn.readLine();

				if (input.equals("0"))
					break;

				System.out.println(server.processJson(input));
			}
		}
	}

	public static List<String> scanJsonFromFile(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();

		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = in.readLine()) != null)
				lines.add(line);
		}

		return lines;
	}
}
