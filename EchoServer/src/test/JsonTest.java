package test;

import java.io.*;

import client.operations.LoginOperation;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonTest {

	public static void main(String[] args) {
		//String json = "{ \"op\": \"5\", \"username\": \"a1234567\", \"password\": \"abc123\" }";
		String json = createJson();
        readJson(json);
	}

	public static String createJson() {
		//Criar objeto e inserir dados
		LoginOperation loginOperation = new LoginOperation("6", "a9876543", "1234");
		Gson gson = new Gson();

		//Converter objeto Java para JSON e retornar JSON como String
		String json = gson.toJson(loginOperation);
		System.out.println("Enviando: " + json);
		return json;
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
