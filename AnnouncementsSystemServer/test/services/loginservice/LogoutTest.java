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

    public static LoginService testSuccessfulLogout() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);

        if (!response.getResponseCode().equals("010"))
            System.err.printf("Failed testSuccessfulLogout: %s%n", response);

        return loginService;
    }

    public static void testAlreadyLoggedOut() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "2459582");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        loginService.logout(jsonObject);
        Response response = loginService.logout(jsonObject);

        if (!response.getResponseCode().equals("012"))
            System.err.printf("Failed testAlreadyLoggedOut: %s%n", response);
    }

    public static void testMissingTokenField() {
        JsonObject jsonObject = new JsonObject();

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);

        if (!response.getResponseCode().equals("011"))
            System.err.printf("Failed testMissingTokenField: %s%n", response);
    }

    public static void testIncorrectToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);

        if (!response.getResponseCode().equals("013"))
            System.err.printf("Failed testIncorrectToken: %s%n", response);
    }

    public static void testNullToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", (String) null);

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = loginService.logout(jsonObject);

        if (!response.getResponseCode().equals("011"))
            System.err.printf("Failed testNullToken: %s%n", response);
    }
}
