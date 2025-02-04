package services.loginservice;

import com.google.gson.JsonObject;
import responses.Response;
import services.LoginService;

public class LoginTest {

    public static void main(String[] ignoredArgs) {
        testSuccessfulCommonUserLogin();
        testSuccessfulAdminLogin();
        testUserAlreadyLoggedIn();
        testMissingFields();
        testInvalidCredentials();
    }

    public static LoginService testSuccessfulCommonUserLogin() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "2459582");
        jsonObject.addProperty("password", "1122");

        LoginService loginService = new LoginService();
        Response response = loginService.login(jsonObject);

        if (!response.getResponseCode().equals("000"))
            System.err.printf("Failed testSuccessfulCommonUserLogin: %s%n", response);

        return loginService;
    }

    public static LoginService testSuccessfulAdminLogin() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "admin01");
        jsonObject.addProperty("password", "pass");

        LoginService loginService = new LoginService();
        Response response = loginService.login(jsonObject);

        if (!response.getResponseCode().equals("001"))
            System.err.printf("Failed testSuccessfulAdminLogin: %s%n", response);

        return loginService;
    }

    public static void testUserAlreadyLoggedIn() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "2459582");
        jsonObject.addProperty("password", "1122");

        LoginService loginService = new LoginService();
        loginService.login(jsonObject);
        Response response = loginService.login(jsonObject);

        if (!response.getResponseCode().equals("004"))
            System.err.printf("Failed testUserAlreadyLoggedIn: %s%n", response);
    }

    public static void testMissingFields() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", "1234");

        LoginService loginService = new LoginService();
        Response response = loginService.login(jsonObject);

        if (!response.getResponseCode().equals("002"))
            System.err.printf("Failed testMissingFields: %s%n", response);
    }

    public static void testInvalidCredentials() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "2459582");
        jsonObject.addProperty("password", "password123");

        Response response = new LoginService().login(jsonObject);

        if (!response.getResponseCode().equals("003"))
            System.err.printf("Failed testInvalidCredentials: %s%n", response);
    }
}
