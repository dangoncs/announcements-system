package services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dao.AccountDAO;
import dao.Database;
import entities.Account;
import responses.LoginResponse;
import responses.Response;

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

    public Response login(JsonObject jsonObject) {
        if (loggedInUserId != null || loggedInUserToken != null) {
            return new Response(
                    "004",
                    "Already logged in"
            );
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement passwordElement = jsonObject.get("password");

        if(userElement == null || passwordElement == null || userElement.isJsonNull()
                || passwordElement.isJsonNull()) {
            return new Response(
                    "002",
                    "Fields missing"
            );
        }

        String userId = userElement.getAsString();
        String password = passwordElement.getAsString();

        try (Connection conn = Database.connect()) {
            Account account = new AccountDAO(conn).searchByUser(userId);

            if(account == null || !password.equals(account.password())) {
                return new Response(
                        "003",
                        "Login failed"
                );
            }

            this.loggedInUserId = userId;
            this.loggedInUserToken = generateToken(userId);
            this.loggedInUserRole = AccountService.getAccountRole(userId);

            return new LoginResponse(
                    "00" + loggedInUserRole,
                    "Successful login",
                    loggedInUserToken
            );
        } catch (SQLException e) {
            return new Response(
                    "005",
                    "Unknown error."
            );
        } finally {
            Database.disconnect();
        }
    }

    public Response logout(JsonObject jsonObject) {
        if (loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "012",
                    "User not logged in."
            );
        }

        JsonElement tokenElement = jsonObject.get("token");

        if(tokenElement == null || tokenElement.isJsonNull()) {
            return new Response(
                    "011",
                    "Fields missing"
            );
        }

        String token = tokenElement.getAsString();

        if(!token.equals(loggedInUserToken)) {
            return new Response(
                    "013",
                    "Logout failed"
            );
        }

        destroyToken();
        return new Response(
                "010",
                "Successful logout"
        );
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
