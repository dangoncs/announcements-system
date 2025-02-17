package services;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.AccountDAO;
import entities.Account;
import operations.authentication.LoginOp;
import operations.authentication.LogoutOp;
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

    public Response login(String operationJson) throws JsonSyntaxException {
        LoginOp loginOp = new Gson().fromJson(operationJson, LoginOp.class);

        String userId = loginOp.user();
        String password = loginOp.password();

        if (userId == null || password == null)
            return new Response("002", "Fields missing");

        if (loggedInUserId != null || loggedInUserToken != null)
            return new Response("004", "Already logged in");

        Account account;
        try {
            account = new AccountDAO().read(userId);
            if (account == null || !password.equals(account.password()))
                return new Response("003", "Login failed");
        } catch (SQLException e) {
            System.err.printf("[ERROR] Authentication error: %s%n", e.getMessage());
            return new Response("005", "Unknown error");
        }

        this.loggedInUserId = userId;
        this.loggedInUserToken = generateToken(userId);
        this.loggedInUserRole = AccountService.getAccountRole(userId);

        return new LoginResponse("00" + loggedInUserRole, "Successful login", loggedInUserToken);
    }

    public Response logout(String operationJson) throws JsonSyntaxException {
        LogoutOp logoutOp = new Gson().fromJson(operationJson, LogoutOp.class);

        String token = logoutOp.token();

        if (token == null)
            return new Response("011", "Fields missing");

        if (loggedInUserId == null || loggedInUserToken == null)
            return new Response("012", "User not logged in");

        if (!token.equals(loggedInUserToken))
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
