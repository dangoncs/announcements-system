package services;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.AccountDAO;
import entities.Account;
import entities.User;
import main.Server;
import operations.authentication.LoginOp;
import operations.authentication.LogoutOp;
import responses.LoginResponse;
import responses.Response;

public class LoginService {
    private User loggedInUser;
    private boolean noLongerLoggedIn;

    public String generateToken(String userId) {
        return userId;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isNoLongerLoggedIn() {
        return noLongerLoggedIn;
    }

    public void setNoLongerLoggedIn(boolean noLongerLoggedIn) {
        this.noLongerLoggedIn = noLongerLoggedIn;
    }

    public Response login(String operationJson) throws JsonSyntaxException {
        LoginOp loginOp = new Gson().fromJson(operationJson, LoginOp.class);

        String userId = loginOp.user();
        String password = loginOp.password();

        if (loggedInUser != null)
            return new Response("146", LoginResponse.ALREADY_LOGGED_IN);

        if (userId == null || password == null)
            return new Response("141", Response.MISSING_FIELDS);

        Account account;
        try {
            account = AccountDAO.read(userId);
            if (account == null || !password.equals(account.password()))
                return new Response("144", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Authentication error: %s%n", e.getMessage());
            return new Response("145", Response.UNKNOWN_ERROR);
        }

        String loggedInUserToken = generateToken(userId);
        int loggedInUserRole = account.role();

        loggedInUser = new User(userId, loggedInUserToken, loggedInUserRole);
        Server.addToLoggedInUsers(loggedInUser);

        return new LoginResponse("140", Response.SUCCESS, loggedInUser);
    }

    public Response logout(String operationJson) throws JsonSyntaxException {
        LogoutOp logoutOp = new Gson().fromJson(operationJson, LogoutOp.class);

        String token = logoutOp.token();

        if (loggedInUser == null || noLongerLoggedIn)
            return new Response("156", LoginResponse.NOT_LOGGED_IN);

        if (token == null)
            return new Response("151", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUser.token()))
            return new Response("152", Response.INVALID_TOKEN);

        noLongerLoggedIn = true;
        return new Response("150", Response.SUCCESS);
    }
}
