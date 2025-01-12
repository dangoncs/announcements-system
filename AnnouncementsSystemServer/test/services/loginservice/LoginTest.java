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
        System.out.printf("Ao tentar logar como usuário comum, recebi: %s%n", response);
        return loginService;
    }

    public static void testSuccessfulAdminLogin() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "admin01");
        jsonObject.addProperty("password", "pass");

        Response response = new LoginService().login(jsonObject);
        System.out.printf("Ao tentar logar como admin, recebi: %s%n", response);
    }

    public static void testUserAlreadyLoggedIn() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "2459582");
        jsonObject.addProperty("password", "1122");

        LoginService loginService = new LoginService();
        loginService.login(jsonObject);
        Response response = loginService.login(jsonObject);
        System.out.printf("Ao tentar logar com alguém já logado, recebi: %s%n", response);
    }

    public static void testMissingFields() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", "1234");

        LoginService loginService = new LoginService();
        Response response = loginService.login(jsonObject);
        System.out.printf("Ao tentar logar com campos faltando, recebi: %s%n", response);
    }

    public static void testInvalidCredentials() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "2459582");
        jsonObject.addProperty("password", "password123");

        Response response = new LoginService().login(jsonObject);
        System.out.printf("Ao tentar logar com credenciais inválidas, recebi: %s%n", response);
    }
}
