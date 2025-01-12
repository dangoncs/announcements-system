package services.accountservice;

import com.google.gson.JsonObject;
import responses.Response;
import services.AccountService;

public class AccountCreationTest {

    public static void main(String[] ignoredArgs) {
        testSuccessfulAccountCreation();
        testMissingFields();
        testInvalidUserIdOrPassword();
        testAccountAlreadyExists();
    }

    private static void testSuccessfulAccountCreation() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "2459582");
        input.addProperty("password", "1122");
        input.addProperty("name", "Danilo");

        Response response = AccountService.create(input);
        System.out.printf("Ao tentar criar nova conta, recebi: %s%n", response);
    }

    private static void testMissingFields() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");

        Response response = AccountService.create(input);
        System.out.printf("Ao tentar criar conta com campos faltando, recebi: %s%n", response);
    }

    private static void testInvalidUserIdOrPassword() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "invalidUser");
        input.addProperty("password", "123");
        input.addProperty("name", "John Doe");

        Response response = AccountService.create(input);
        System.out.printf("Ao tentar criar conta com dados inválidos, recebi: %s%n", response);
    }

    private static void testAccountAlreadyExists() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");
        input.addProperty("password", "6789");
        input.addProperty("name", "Joãozinho");

        Response response = AccountService.create(input);
        System.out.printf("Ao tentar criar conta com usuário já existente, recebi: %s%n", response);
    }
}
