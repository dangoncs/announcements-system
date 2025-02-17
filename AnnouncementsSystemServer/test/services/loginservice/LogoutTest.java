package services.loginservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import responses.Response;
import services.LoginService;

public class LogoutTest {

    public static void main(String[] ignoredArgs) {
        testSuccessfulLogout();
        testUserNotLoggedIn();
        testMissingTokenField();
        testIncorrectToken();
    }

    public static LoginService testSuccessfulLogout() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("010"))
            System.err.printf("Failed testSuccessfulLogout: %s%n", response);

        return loginService;
    }

    public static void testUserNotLoggedIn() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        Response response = new LoginService().logout(operationJson);

        if (!response.getResponseCode().equals("012"))
            System.err.printf("Failed testUserNotLoggedIn: %s%n", response);
    }

    public static void testMissingTokenField() {
        JsonObject jsonObject = new JsonObject();

        String operationJson = new Gson().toJson(jsonObject);

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("011"))
            System.err.printf("Failed testMissingTokenField: %s%n", response);
    }

    public static void testIncorrectToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "");

        String operationJson = new Gson().toJson(jsonObject);

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(operationJson);

        if (!response.getResponseCode().equals("013"))
            System.err.printf("Failed testIncorrectToken: %s%n", response);
    }
}
