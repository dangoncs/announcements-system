package services.loginservice;

import com.google.gson.JsonObject;
import responses.Response;
import services.LoginService;

public class LogoutTest {

    public static void main(String[] ignoredArgs) {
        testSuccessfulLogout();
        testAlreadyLoggedOut();
        testMissingTokenField();
        testIncorrectToken();
        testNullToken();
    }

    public static void testSuccessfulLogout() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);

        System.out.printf("Ao tentar fazer logout, recebi: %s%n", response);
    }

    public static void testAlreadyLoggedOut() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        loginService.logout(jsonObject);
        Response response = loginService.logout(jsonObject);
        System.out.printf("Ao tentar fazer logout já deslogado, recebi: %s%n", response);
    }

    public static void testMissingTokenField() {
        JsonObject jsonObject = new JsonObject();

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);
        System.out.printf("Ao tentar fazer logout sem token, recebi: %s%n", response);
    }

    public static void testIncorrectToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);
        System.out.printf("Ao tentar fazer logout com token incorreto, recebi: %s%n", response);
    }

    public static void testNullToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", (String) null);

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);
        System.out.printf("Ao tentar fazer logout com token nulo, recebi: %s%n", response);
    }
}
