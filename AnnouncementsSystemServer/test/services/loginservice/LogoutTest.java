package services.loginservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entities.User;
import responses.Response;
import services.LoginService;

public class LogoutTest {

    public static void main(String[] ignoredArgs) {
        LoginService loginService = new LoginService();
        loginService.setLoggedInUser(new User("2459582", "2459582", 0));

        testMissingTokenField(loginService);
        testUserNotLoggedIn();
        testIncorrectToken(loginService);
        testSuccessfulLogout(loginService);
        testUserNotLoggedIn(loginService);
    }

    public static void testMissingTokenField(LoginService loginService) {
        JsonObject jsonObject = new JsonObject();

        String operationJson = new Gson().toJson(jsonObject);

        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("011"))
            System.err.printf("Failed testMissingTokenField: %s%n", response);
    }

    public static void testUserNotLoggedIn() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = new LoginService().logout(operationJson);

        if (!response.getResponseCode().equals("012"))
            System.err.printf("Failed testUserNotLoggedIn: %s%n", response);
    }

    public static void testIncorrectToken(LoginService loginService) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("013"))
            System.err.printf("Failed testIncorrectToken: %s%n", response);
    }

    public static void testSuccessfulLogout(LoginService loginService) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("010"))
            System.err.printf("Failed testSuccessfulLogout: %s%n", response);
    }

    public static void testUserNotLoggedIn(LoginService loginService) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("012"))
            System.err.printf("Failed testUserNotLoggedIn: %s%n", response);
    }
}
