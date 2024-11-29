package server.services;

import com.google.gson.JsonObject;
import server.dao.AccountDAO;
import server.dao.Database;
import server.entities.Account;
import server.responses.AccountResponse;
import server.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {

    private static boolean isAdmin(String userId) {
        return userId.startsWith("a");
    }

    private static boolean isValidUserId(String userId) {
        return (userId.length() == 7) && (userId.matches("\\d{7}"));
    }

    private static boolean isValidPassword(String password) {
        return (password.length() == 4) && (password.matches("\\d{4}"));
    }

    private static String truncateString(String str) {
        if (str == null)
            return null;

        if (str.length() > 40)
            return str.substring(0, 40);

        return str;
    }

    public static String create(JsonObject jsonObject) {
        String userId = jsonObject.get("user").getAsString();
        String password = jsonObject.get("password").getAsString();
        String name = jsonObject.get("name").getAsString();

        if (userId == null || name == null || password == null) {
            return new Response(
                    "101",
                    "Fields missing"
            ).toJson();
        }

        if (!isValidUserId(userId) || !isValidPassword(password)) {
            return new Response(
                    "102",
                    "Invalid information inserted: user or password"
            ).toJson();
        }

        try {
            name = truncateString(name);

            Connection conn = Database.connect();
            new AccountDAO(conn).create(userId, name, password);

            return new Response(
                    "100",
                    "Successful account creation"
            ).toJson();
        } catch (SQLException e) {
            return new Response(
                    "103",
                    "Already exists an account with the username"
            ).toJson();
        } finally {
            Database.disconnect();
        }
    }

    public static String read(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        String userId = jsonObject.get("user").getAsString();
        String token = jsonObject.get("token").getAsString();

        if (userId == null)
            userId = loggedInUserId;

        if (loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "115",
                    "Cannot perform operation while logged out."
            ).toJson();
        }

        if (!isAdmin(loggedInUserId)) {
            if (!loggedInUserToken.equals(token)) {
                return new Response(
                        "112",
                        "Invalid or empty token"
                ).toJson();
            }

            if (!loggedInUserId.equals(userId)) {
                return new Response(
                        "113",
                        "Invalid Permission, user does not have permission to visualize other users data"
                ).toJson();
            }
        }

        try {
            Connection conn = Database.connect();
            Account account = new AccountDAO(conn).searchByUser(userId);

            if (isAdmin(account.getUserId())) {
                return new AccountResponse(
                        "111",
                        "Returns all information of the account",
                        loginService.generateToken(account.getUserId()),
                        account.getUserId(),
                        account.getName(),
                        account.getPassword()
                ).toJson();
            }

            return new AccountResponse(
                    "110",
                    "Returns all information of the account",
                    loginService.generateToken(account.getUserId()),
                    account.getUserId(),
                    account.getName(),
                    account.getPassword()
            ).toJson();
        } catch (SQLException e) {
            return new Response(
                    "114",
                    "User not found ( Admin Only )"
            ).toJson();
        } finally {
            Database.disconnect();
        }
    }

    public static String update(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        String userId = jsonObject.get("user").getAsString();
        String password = jsonObject.get("password").getAsString();
        String name = jsonObject.get("name").getAsString();
        String token = jsonObject.get("token").getAsString();

        if (userId == null)
            userId = loggedInUserId;

        if (loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "124",
                    "Cannot perform operation while logged out."
            ).toJson();
        }

        if (!loggedInUserToken.equals(token)) {
            return new Response(
                    "121",
                    "Invalid or empty token"
            ).toJson();
        }

        if (!isAdmin(loggedInUserId) && !loggedInUserId.equals(userId)) {
            return new Response(
                    "122",
                    "Invalid Permission, user does not have permission to update other users data"
            ).toJson();
        }

        try {
            Connection conn = Database.connect();

            if (name != null) {
                name = truncateString(name);
                new AccountDAO(conn).updateName(userId, name);
            }

            if (password != null)
                new AccountDAO(conn).updatePassword(userId, password);

            return new Response(
                    "120",
                    "Account successfully updated"
            ).toJson();
        } catch (SQLException e) {
            return new Response(
                    "123",
                    "No user or token found ( Admin Only)"
            ).toJson();
        } finally {
            Database.disconnect();
        }
    }

    public static String delete(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        String userId = jsonObject.get("user").getAsString();
        String token = jsonObject.get("token").getAsString();

        if (userId == null)
            userId = loggedInUserId;

        if (loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "135",
                    "Cannot perform operation while logged out."
            ).toJson();
        }

        if (!loggedInUserToken.equals(token)) {
            return new Response(
                    "132",
                    "Invalid Token"
            ).toJson();
        }

        if (!isAdmin(loggedInUserId) && !loggedInUserId.equals(userId)) {
            return new Response(
                    "133",
                    "Invalid Permission, user does not have permission to delete other users data"
            ).toJson();
        }

        try {
            Connection conn = Database.connect();
            int manipulatedLines = new AccountDAO(conn).delete(userId);

            if (manipulatedLines < 1) {
                return new Response(
                        "134",
                        "User not found ( Admin Only )"
                ).toJson();
            }

            if (loggedInUserId.equals(userId))
                loginService.destroyToken();

            return new Response(
                    "130",
                    "Account successfully deleted"
            ).toJson();
        } catch (SQLException e) {
            return new Response(
                    "131",
                    "Fields missing"
            ).toJson();
        } finally {
            Database.disconnect();
        }

    }
}
