package server.services;

import server.dao.AccountDAO;
import server.dao.Database;
import server.entities.Account;
import server.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

import static server.services.InputValidator.*;

public class AccountService {

    public static String create(JsonObject jsonObject) {
        String userId = jsonObject.get("user").getAsString();
        String password = jsonObject.get("password").getAsString();
        String name = jsonObject.get("name").getAsString();

        if(userId == null || name == null || password == null)
            return new Response("101", "Fields missing").toJson();

        if(!isValidUserId(userId) || !isValidPassword(password))
            return new Response("102", "Invalid information inserted: user or password").toJson();

        try {
            name = truncateString(name, 40);

            Connection conn = Database.connect();
            new AccountDAO(conn).create(userId, name, password);

            return new Response("100", "Successful account creation").toJson();
        } catch (SQLException e) {
            return new Response("103", "Already exists an account with the username").toJson();
        } finally {
            Database.disconnect();
        }
    }

    public static String read(JsonObject jsonObject, String loggedInUser) {
        String userId = jsonObject.get("user").getAsString();
        String token = jsonObject.get("token").getAsString();

        if(InputValidator.isAuthorized(userId, token)) {
            try {
                Connection conn = Database.connect();
                new AccountDAO(conn).searchByUser(userId);
            } catch (SQLException e) {
                System.err.println("ERRO ao ler conta: " + e.getLocalizedMessage());
                return null;
            } finally {
                Database.disconnect();
            }
        }
        else {
            System.out.println("ERRO ao ler conta: input inválido");
            return null;
        }
    }

    public static String update(JsonObject jsonObject, String loggedInUser) {
        String userId = jsonObject.get("user").getAsString();
        String password = jsonObject.get("password").getAsString();
        String name = jsonObject.get("name").getAsString();
        String token = jsonObject.get("token").getAsString();

        if(isAuthorized(loggedInUser, token))
            return new Response("121", "Invalid or Empty Token").toJson();

        try {
            name = truncateString(name, 40);

            Connection conn = Database.connect();
            if(userId != null) new AccountDAO(conn).updateUser(account);
            if(name != null) new AccountDAO(conn).updateName(account);
            if(password != null) new AccountDAO(conn).updatePassword(account);

            return new Response("120", "Account successfully updated").toJson();
        } catch (SQLException e) {
            System.err.println("ERRO ao atualizar conta: " + e.getLocalizedMessage());
            return new Response("200", "NOT OK").toJson();
        } finally {
            Database.disconnect();
        }
    }

    public static String delete(JsonObject jsonObject, String loggedInUser) {
        String userId = jsonObject.get("user").getAsString();
        if(isValidUserId(userId)) {
            try {
                Connection conn = Database.connect();
                int manipulatedLines = new AccountDAO(conn).delete(userId);

                if(manipulatedLines < 1)
                    System.out.println("INFO: Conta não encontrada.");
                else
                    System.out.println("INFO: Conta excluída.");
            } catch (SQLException e) {
                System.err.println("ERRO ao excluir conta: " + e.getLocalizedMessage());
            } finally {
                Database.disconnect();
            }
        }
        else {
            System.out.println("ERRO ao excluir conta: input inválido");
            return null;
        }
    }
}
