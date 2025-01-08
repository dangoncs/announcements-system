package server.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.entities.Account;
import server.dao.AccountDAO;
import server.dao.Database;
import server.responses.LoginResponse;
import server.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {
    private String loggedInUserId;
    private String loggedInUserToken;
    private int loggedInUserRole;

    String generateToken(String userId) {
       return userId;
    }

    void destroyToken() {
        loggedInUserId = null;
        loggedInUserToken = null;
        loggedInUserRole = 0;
    }

    public String login(JsonObject jsonObject) {
        if (loggedInUserId != null || loggedInUserToken != null) {
            return new Response(
                    "004",
                    "Already logged in"
            ).toJson();
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement passwordElement = jsonObject.get("password");

        if(userElement == null || passwordElement == null || userElement.isJsonNull()
                || passwordElement.isJsonNull()) {
            return new Response(
                    "002",
                    "Fields missing"
            ).toJson();
        }

        String userId = userElement.getAsString();
        String password = passwordElement.getAsString();

        try {
            Connection conn = Database.connect();
            Account account = new AccountDAO(conn).searchByUser(userId);

            if(account == null || !password.equals(account.getPassword())) {
                return new Response(
                        "003",
                        "Login failed"
                ).toJson();
            }

            this.loggedInUserId = userId;
            this.loggedInUserToken = generateToken(userId);
            this.loggedInUserRole = AccountService.getAccountRole(userId);

            return new LoginResponse(
                    "00" + loggedInUserRole,
                    "Successful login",
                    loggedInUserToken
            ).toJson();
        } catch (SQLException e) {
            return new Response(
                    "005",
                    "Unknown error."
            ).toJson();
        } finally {
            Database.disconnect();
        }
    }

    public String logout(JsonObject jsonObject) {
        if (loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "012",
                    "User not logged in."
            ).toJson();
        }

        JsonElement tokenElement = jsonObject.get("token");

        if(tokenElement == null || tokenElement.isJsonNull()) {
            return new Response(
                    "011",
                    "Fields missing"
            ).toJson();
        }

        String token = tokenElement.getAsString();

        if(!token.equals(loggedInUserToken)) {
            return new Response(
                    "013",
                    "Logout failed"
            ).toJson();
        }

        destroyToken();
        return new Response(
                "010",
                "Successful logout"
        ).toJson();
    }

    public String getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public String getLoggedInUserToken() {
        return this.loggedInUserToken;
    }

    public int getLoggedInUserRole() {
        return this.loggedInUserRole;
    }
}
