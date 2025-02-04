package services.accountservice;

import com.google.gson.JsonObject;
import responses.Response;
import services.AccountService;
import services.LoginService;
import services.loginservice.LoginTest;
import services.loginservice.LogoutTest;

public class ReadAccountTest {

    public static void main(String[] args) {
        testSuccessfulResponse();
        testLoggedOutUser();
        testInvalidOrEmptyToken();
        testInvalidPermission();
        testUserNotFound();
    }

    private static void testSuccessfulResponse() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "");
        jsonObject.addProperty("token", "2459582");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = AccountService.read(jsonObject, loginService);
        if (!response.getResponseCode().equals("110"))
            System.err.printf("Failed testSuccessfulResponse: %s%n", response);

        jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "admin01");
        jsonObject.addProperty("token", "admin01");

        loginService = LoginTest.testSuccessfulAdminLogin();
        response = AccountService.read(jsonObject, loginService);
        if (!response.getResponseCode().equals("111"))
            System.err.printf("Failed testSuccessfulResponse: %s%n", response);
    }

    public static void testLoggedOutUser() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "");
        jsonObject.addProperty("token", "2459582");

        Response response = AccountService.read(jsonObject, new LoginService());
        if (!response.getResponseCode().equals("116"))
            System.err.printf("Failed testLoggedOutUser: %s%n", response);

        LoginService loginService = LogoutTest.testSuccessfulLogout();
        response = AccountService.read(jsonObject, loginService);
        if (!response.getResponseCode().equals("116"))
            System.err.printf("Failed testLoggedOutUser: %s%n", response);
    }

    private static void testInvalidOrEmptyToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "");
        jsonObject.addProperty("token", "");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = AccountService.read(jsonObject, loginService);
        if (!response.getResponseCode().equals("112"))
            System.err.printf("Failed testInvalidOrEmptyToken: %s%n", response);
    }

    private static void testInvalidPermission() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "admin01");
        jsonObject.addProperty("token", "2459582");

        LoginService loginService = LoginTest.testSuccessfulCommonUserLogin();
        Response response = AccountService.read(jsonObject, loginService);
        if (!response.getResponseCode().equals("113"))
            System.err.printf("Failed testInvalidPermission: %s%n", response);
    }

    private static void testUserNotFound() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "00000000");
        jsonObject.addProperty("token", "admin01");

        LoginService loginService = LoginTest.testSuccessfulAdminLogin();
        Response response = AccountService.read(jsonObject, loginService);
        if (!response.getResponseCode().equals("114"))
            System.err.printf("Failed testUserNotFound: %s%n", response);
    }
}
