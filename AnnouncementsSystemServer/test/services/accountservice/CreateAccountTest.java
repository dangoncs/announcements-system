package services.accountservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import responses.Response;
import services.AccountService;

public class CreateAccountTest {

    public static void main(String[] ignoredArgs) {
        testSuccessfulAccountCreation();
        testMissingFields();
        testInvalidUserIdOrPassword();
        testAccountAlreadyExists();
    }

    private static void testSuccessfulAccountCreation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "2459582");
        jsonObject.addProperty("password", "1122");
        jsonObject.addProperty("name", "Danilo");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = AccountService.create(operationJson);
        System.out.printf("Ao tentar criar nova conta, recebi: %s%n", response);
    }

    private static void testMissingFields() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "1234567");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = AccountService.create(operationJson);
        System.out.printf("Ao tentar criar conta com campos faltando, recebi: %s%n", response);
    }

    private static void testInvalidUserIdOrPassword() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "invalidUser");
        jsonObject.addProperty("password", "123");
        jsonObject.addProperty("name", "John Doe");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = AccountService.create(operationJson);
        System.out.printf("Ao tentar criar conta com dados inválidos, recebi: %s%n", response);
    }

    private static void testAccountAlreadyExists() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "1234567");
        jsonObject.addProperty("password", "6789");
        jsonObject.addProperty("name", "Joãozinho");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = AccountService.create(operationJson);
        System.out.printf("Ao tentar criar conta com usuário já existente, recebi: %s%n", response);
    }
}
