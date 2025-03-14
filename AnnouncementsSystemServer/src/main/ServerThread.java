package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import entities.User;
import operations.Operation;
import responses.Response;
import services.AccountService;
import services.AnnouncementService;
import services.CategoryService;
import services.LoginService;
import services.UserCategoryService;

public class ServerThread extends Thread {
    private final LoginService loginService;
    private final Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.loginService = new LoginService();
    }

    @Override
    public void run() {
        System.out.println("[INFO] Established a new connection.");

        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null && !loginService.isNoLongerLoggedIn()) {
                System.out.printf("[INFO] Received: %s%n", inputLine);
                String responseJson = processJson(inputLine);

                System.out.printf("[INFO] Sending: %s%n", responseJson);
                out.println(responseJson);
            }
        } catch (IOException e) {
            System.err.printf("[WARNING] A problem happened while communicating with a client: %s%n",
                    e.getLocalizedMessage());
        } finally {
            if (loginService.getLoggedInUser() != null)
                Server.removeFromLoggedInUsers(loginService.getLoggedInUser());
        }
    }

    public String processJson(String operationJson) {
        User loggedInUser = loginService.getLoggedInUser();
        Response response;

        try {
            Operation operation = new Gson().fromJson(operationJson, Operation.class);

            response = switch (operation.op()) {
                case "10" -> AccountService.create(operationJson);
                case "11" -> AccountService.read(operationJson, loggedInUser);
                case "12" -> AccountService.update(operationJson, loggedInUser);
                case "13" -> AccountService.delete(operationJson, loginService);
                case "14" -> loginService.login(operationJson);
                case "15" -> loginService.logout(operationJson);
                case "20" -> CategoryService.create(operationJson, loggedInUser);
                case "21" -> CategoryService.read(operationJson, loggedInUser);
                case "22" -> CategoryService.update(operationJson, loggedInUser);
                case "23" -> CategoryService.delete(operationJson, loggedInUser);
                case "24" -> UserCategoryService.subscribe(operationJson, loggedInUser);
                case "25" -> UserCategoryService.unsubscribe(operationJson, loggedInUser);
                case "30" -> AnnouncementService.create(operationJson, loggedInUser);
                case "31" -> AnnouncementService.read(operationJson, loggedInUser);
                case "32" -> AnnouncementService.update(operationJson, loggedInUser);
                case "33" -> AnnouncementService.delete(operationJson, loggedInUser);
                case null -> new Response("001", Response.MISSING_FIELDS);
                default -> new Response("002", "Not Implemented");
            };
        } catch (JsonSyntaxException e) {
            response = new Response("000", "Internal Server Error");
        }

        return new Gson().toJson(response);
    }
}
