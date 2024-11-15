package test;

import client.operations.LoginOperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		//Converter String JSON para objeto Java
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		System.out.println("Recebido: " + jsonObject);

		//Realizar a operação conforme código recebido
		String operationCode = jsonObject.get("op").getAsString();
		switch(operationCode) {
			case "5":
				System.out.println("OPERAÇÃO LOGIN");
				break;
			case "6":
				System.out.println("OPERAÇÃO LOGOUT");
				break;
			default:
				System.err.println("OPERAÇÃO NÃO RECONHECIDA");
		}

		//Operation obj2 = gson.fromJson(br, JsonObject.class);
	}
}
