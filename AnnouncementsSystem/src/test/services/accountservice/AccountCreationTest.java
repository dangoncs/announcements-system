package test.services.accountservice;
import com.google.gson.JsonObject;
import server.services.AccountService;

public class AccountCreationTest {

    public static void main(String[] args) {
        testCreate_SuccessfulAccountCreation();
        testCreate_MissingFields();
        testCreate_InvalidUserIdOrPassword();
        testCreate_AccountAlreadyExists();
    }

    private static void testCreate_SuccessfulAccountCreation() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");
        input.addProperty("password", "2345");
        input.addProperty("name", "John Doe");

        String responseJson = AccountService.create(input);
        System.out.println("Recebido: " + responseJson);
    }

    private static void testCreate_MissingFields() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");

        String responseJson = AccountService.create(input);
        System.out.println("Recebido: " + responseJson);
    }

    private static void testCreate_InvalidUserIdOrPassword() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "invalidUser");
        input.addProperty("password", "123");
        input.addProperty("name", "John Doe");

        String responseJson = AccountService.create(input);
        System.out.println("Recebido: " + responseJson);
    }

    private static void testCreate_AccountAlreadyExists() {
        JsonObject input = new JsonObject();
        input.addProperty("user", "1234567");
        input.addProperty("password", "6789");
        input.addProperty("name", "Joãozinho");

        String responseJson = AccountService.create(input);
        System.out.println("Recebido: " + responseJson);
    }
}
