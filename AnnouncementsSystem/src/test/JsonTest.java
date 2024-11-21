package test;

import client.operations.LoginOperation;

public class JsonTest {

	public static void main(String[] args) {
		//String json = "{ \"op\": \"5\", \"username\": \"a1234567\", \"password\": \"abc123\" }";
		String json = createJson();
		System.out.println("Enviando: " + json);
        readJson(json);
	}

	//Cria objeto, insere dados, converte objeto Java para JSON e retorna JSON como String
	public static String createJson() {
		return new LoginOperation("6", "a9876543", "1234").toJson();
	}

	public static void readJson(String json) {
		//Operation obj2 = gson.fromJson(br, JsonObject.class);
	}
}
