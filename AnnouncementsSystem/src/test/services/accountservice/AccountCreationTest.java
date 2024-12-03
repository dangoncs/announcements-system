package test.services.accountservice;
import com.google.gson.JsonObject;
import server.services.AccountService;

public class AccountCreationTest {

    public static void main(String[] args) {
        testSuccessfulAccountCreation();
        testMissingFields();
        testInvalidUserIdOrPassword();
        testAccountAlreadyExists();
    }

    private static void testSuccessfulAccountCreation() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");
        input.addProperty("password", "2345");
        input.addProperty("name", "John Doe");

        String responseJson = AccountService.create(input);
        System.out.println("Ao tentar criar nova conta, recebi: " + responseJson);
    }

    private static void testMissingFields() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");

        String responseJson = AccountService.create(input);
        System.out.println("Ao tentar criar conta com campos faltando, recebi: " + responseJson);
    }

    private static void testInvalidUserIdOrPassword() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "invalidUser");
        input.addProperty("password", "123");
        input.addProperty("name", "John Doe");

        String responseJson = AccountService.create(input);
        System.out.println("Ao tentar criar conta com dados inválidos, recebi: " + responseJson);
    }

    private static void testAccountAlreadyExists() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");
        input.addProperty("password", "6789");
        input.addProperty("name", "Joãozinho");

        String responseJson = AccountService.create(input);
        System.out.println("Ao tentar criar conta com usuário já existente, recebi: " + responseJson);
    }
}
