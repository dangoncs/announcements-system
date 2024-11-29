package server.services;

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

    public String generateToken(String userId) {
       return userId;
    }

    public void destroyToken() {
        loggedInUserId = null;
        loggedInUserToken = null;
    }

    public String login(JsonObject jsonObject) {
        if (loggedInUserId != null || loggedInUserToken != null)
            return new Response("004", "Need to logout before logging in again").toJson();

        String userId = jsonObject.get("user").getAsString();
        String password = jsonObject.get("password").getAsString();

        if(userId == null || password == null)
            return new Response("002", "Fields missing").toJson();

        try {
            Connection conn = Database.connect();
            Account account = new AccountDAO(conn).searchByUser(userId);

            if(account == null || !password.equals(account.getPassword()))
                return new Response("003", "Login failed").toJson();

            this.loggedInUserId = userId;
            this.loggedInUserToken = generateToken(userId);
            return new LoginResponse("000", "Successful login", loggedInUserToken).toJson();
            //TODO: implement admin login
        } catch (SQLException e) {
            return new Response("003", "Login failed").toJson();
        } finally {
            Database.disconnect();
        }
    }

    public String logout(JsonObject jsonObject) {
        if (loggedInUserId == null || loggedInUserToken == null)
            return new Response("012", "Already logged out").toJson();

        String token = jsonObject.get("token").getAsString();

        if(token == null)
            return new Response("011", "Fields missing").toJson();

        if(!token.equals(loggedInUserToken))
            return new Response("013", "Incorrect token").toJson();

        destroyToken();
        return new Response("010", "Successful logout").toJson();
    }

    public String getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public String getLoggedInUserToken() {
        return this.loggedInUserToken;
    }
}
