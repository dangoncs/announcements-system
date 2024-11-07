package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import entities.Operation;

public class JsonTest {

	public static void main(String[] args) {
			Operation obj = new Operation("2", "a2459582", "1234");
			Gson gson = new Gson();
			
			// converte objetos Java para JSON e retorna JSON como String
			String json = gson.toJson(obj);
			System.out.println(json);
			
			try {
				//Escreve Json convertido em arquivo chamado "file.json"
				FileWriter writer = new FileWriter("C:\\Users\\a2459582\\Downloads\\sistemas-distribuidos-main\\file.json");
				writer.write(json);
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {

				BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\a2459582\\Downloads\\sistemas-distribuidos-main\\file.json"));

				//Converte String JSON para objeto Java
				Operation obj2 = gson.fromJson(br, Operation.class);
				System.out.println(obj2);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
