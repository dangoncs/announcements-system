package services;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dao.AccountDAO;
import entities.Account;
import responses.LoginResponse;
import responses.Response;

public class LoginService {
    private String loggedInUserId;
    private String loggedInUserToken;
    private int loggedInUserRole;
    private boolean noLongerLoggedIn;

    String generateToken(String userId) {
       return userId;
    }

    public Response login(JsonObject jsonObject) {
        if (loggedInUserId != null || loggedInUserToken != null)
            return new Response("004", "Already logged in");

        JsonElement userElement = jsonObject.get("user");
        JsonElement passwordElement = jsonObject.get("password");

        if(userElement == null || passwordElement == null || userElement.isJsonNull()
                || passwordElement.isJsonNull())
            return new Response("002", "Fields missing");

        String userId = userElement.getAsString();
        String password = passwordElement.getAsString();

        Account account;
        try {
            account = new AccountDAO().searchByUser(userId);
            if(account == null || !password.equals(account.password()))
                return new Response("003", "Login failed");
        } catch (SQLException ex) {
            return new Response("005", "Unknown error.");
        }

        this.loggedInUserId = userId;
        this.loggedInUserToken = generateToken(userId);
        this.loggedInUserRole = AccountService.getAccountRole(userId);

        return new LoginResponse(
                "00" + loggedInUserRole,
                "Successful login",
                loggedInUserToken
        );
    }

    public Response logout(JsonObject jsonObject) {
        if (loggedInUserId == null || loggedInUserToken == null)
            return new Response("012", "User not logged in.");

        JsonElement tokenElement = jsonObject.get("token");

        if(tokenElement == null || tokenElement.isJsonNull())
            return new Response("011", "Fields missing");

        String token = tokenElement.getAsString();

        if(!token.equals(loggedInUserToken))
            return new Response("013", "Logout failed");

        noLongerLoggedIn = true;
        return new Response("010", "Successful logout");
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

    public boolean isNoLongerLoggedIn() {
        return noLongerLoggedIn;
    }

    public void setNoLongerLoggedIn(boolean noLongerLoggedIn) {
        this.noLongerLoggedIn = noLongerLoggedIn;
    }
}
