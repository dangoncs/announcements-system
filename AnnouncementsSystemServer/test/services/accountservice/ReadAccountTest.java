package services.accountservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entities.User;
import responses.Response;
import services.AccountService;

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
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        User userData = new User("2459582", "2459582", 0);
        Response response = AccountService.read(operationJson, userData);
        if (!response.getResponseCode().equals("110"))
            System.err.printf("Failed testSuccessfulResponse: %s%n", response);

        jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("token", "admin01");

        operationJson = new Gson().toJson(jsonObject);

        userData = new User("admin01", "admin01", 1);
        response = AccountService.read(operationJson, userData);

        if (!response.getResponseCode().equals("111"))
            System.err.printf("Failed testSuccessfulResponse: %s%n", response);
    }

    public static void testLoggedOutUser() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        User userData = new User("", "", 0);
        Response response = AccountService.read(operationJson, userData);

        if (!response.getResponseCode().equals("112"))
            System.err.printf("Failed testLoggedOutUser: %s%n", response);
    }

    private static void testInvalidOrEmptyToken() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("token", "");

        String operationJson = new Gson().toJson(jsonObject);

        User userData = new User("2459582", "2459582", 0);
        Response response = AccountService.read(operationJson, userData);

        if (!response.getResponseCode().equals("112"))
            System.err.printf("Failed testInvalidOrEmptyToken: %s%n", response);
    }

    private static void testInvalidPermission() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "admin01");
        jsonObject.addProperty("token", "2459582");

        String operationJson = new Gson().toJson(jsonObject);

        User userData = new User("2459582", "2459582", 0);
        Response response = AccountService.read(operationJson, userData);

        if (!response.getResponseCode().equals("113"))
            System.err.printf("Failed testInvalidPermission: %s%n", response);
    }

    private static void testUserNotFound() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("op", "2");
        jsonObject.addProperty("user", "00000000");
        jsonObject.addProperty("token", "admin01");

        String operationJson = new Gson().toJson(jsonObject);

        User userData = new User("admin01", "admin01", 1);
        Response response = AccountService.read(operationJson, userData);

        if (!response.getResponseCode().equals("114"))
            System.err.printf("Failed testUserNotFound: %s%n", response);
    }
}
