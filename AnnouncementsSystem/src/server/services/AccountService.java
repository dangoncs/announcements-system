package server.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import server.dao.AccountDAO;
import server.dao.Database;
import server.entities.Account;
import server.responses.AccountResponse;
import server.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {

    private static boolean isNotValidUserId(String userId) {
        return (userId == null) || (!userId.matches("\\d{7}"));
    }

    private static boolean isNotValidPassword(String password) {
        return (password == null) || (!password.matches("\\d{4}"));
    }

    private static boolean isNotValidName(String name) {
        return (name == null) || (name.length() <= 1);
    }

    private static String truncateString(String str) {
        return (str != null) && (str.length() > 40) ? str.substring(0, 40) : str;
    }

    public static int getAccountRole(String userId) {
        return userId.matches("\\d{7}") ? 0 : 1;
    }

    public static String create(JsonObject jsonObject) {
        JsonElement userElement = jsonObject.get("user");
        JsonElement passwordElement = jsonObject.get("password");
        JsonElement nameElement = jsonObject.get("name");

        if(userElement == null || passwordElement == null || nameElement == null 
            || userElement.isJsonNull() || passwordElement.isJsonNull()
            || nameElement.isJsonNull()
        ) {
            return new Response(
                    "101",
                    "Fields missing"
            ).toJson();
        }

        String userId = userElement.getAsString();
        String password = passwordElement.getAsString();
        String name = nameElement.getAsString();

        if(isNotValidUserId(userId) || isNotValidPassword(password) || isNotValidName(name)) {
            return new Response(
                    "102",
                    "Invalid information inserted: user or password"
            ).toJson();
        }

        try(Connection conn = Database.connect()) {
            if(new AccountDAO(conn).searchByUser(userId) != null) {
                return new Response(
                        "103",
                        "Already exists an account with the username"
                ).toJson();
            }

            name = truncateString(name);
            new AccountDAO(conn).create(userId, name, password);

            return new Response(
                    "100",
                    "Successful account creation"
            ).toJson();
        } catch(SQLException e) {
            return new Response(
                    "104",
                    "Unknown error"
            ).toJson();
        }
    }

    public static String read(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "116",
                    "Cannot perform operation while logged out."
            ).toJson();
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement tokenElement = jsonObject.get("token");

        String userId = (userElement != null && !userElement.isJsonNull()) ? userElement.getAsString() : loggedInUserId;

        if(loggedInUserRole != 1) {
            if(tokenElement == null || tokenElement.isJsonNull()) {
                return new Response(
                        "112",
                        "Invalid or empty token"
                ).toJson();
            }

            String token = tokenElement.getAsString();
            if(!loggedInUserToken.equals(token)) {
                return new Response(
                        "112",
                        "Invalid or empty token"
                ).toJson();
            }

            if(!loggedInUserId.equals(userId)) {
                return new Response(
                        "113",
                        "Invalid Permission, user does not have permission to visualize other users data"
                ).toJson();
            }
        }

        try(Connection conn = Database.connect()) {
            Account account = new AccountDAO(conn).searchByUser(userId);

            if(account == null) {
                return new Response(
                        "114",
                        "User not found ( Admin Only )"
                ).toJson();
            }

            return new AccountResponse(
                    "11" + getAccountRole(account.getUserId()),
                    "Returns all information of the account",
                    loginService.generateToken(account.getUserId()),
                    account.getUserId(),
                    account.getName(),
                    account.getPassword()
            ).toJson();
        } catch(SQLException e) {
            return new Response(
                    "115",
                    "Unknown error"
            ).toJson();
        }
    }

    public static String update(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "125",
                    "Cannot perform operation while logged out."
            ).toJson();
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement passwordElement = jsonObject.get("password");
        JsonElement nameElement = jsonObject.get("name");
        JsonElement tokenElement = jsonObject.get("token");

        String userId = (userElement != null && !userElement.isJsonNull()) ? userElement.getAsString() : loggedInUserId;
        String password = (passwordElement != null && !passwordElement.isJsonNull()) ? passwordElement.getAsString() : null;
        String name = (nameElement != null && !nameElement.isJsonNull()) ? nameElement.getAsString() : null;
        String token = (tokenElement != null && !tokenElement.isJsonNull()) ? tokenElement.getAsString() : null;

        if(!loggedInUserToken.equals(token)) {
            return new Response(
                    "121",
                    "Invalid or empty token"
            ).toJson();
        }

        if(loggedInUserRole != 1 && !loggedInUserId.equals(userId)) {
            return new Response(
                    "122",
                    "Invalid Permission, user does not have permission to update other users data"
            ).toJson();
        }

        try(Connection conn = Database.connect()) {
            if((name != null && isNotValidName(name)) || (password != null && isNotValidPassword(password))) {
                return new Response(
                        "126",
                        "Invalid information inserted"
                ).toJson();
            }

            if(new AccountDAO(conn).searchByUser(userId) == null) {
                return new Response(
                        "123",
                        "No user or token found ( Admin Only )}"
                ).toJson();
            }

            if(name != null)
                new AccountDAO(conn).updateName(userId, truncateString(name));

            if(password != null)
                new AccountDAO(conn).updatePassword(userId, password);

            return new Response(
                    "120",
                    "Account successfully updated"
            ).toJson();
        } catch(SQLException e) {
            return new Response(
                    "124",
                    "Unknown error"
            ).toJson();
        }
    }

    public static String delete(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "136",
                    "Cannot perform operation while logged out."
            ).toJson();
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement tokenElement = jsonObject.get("token");

        if(tokenElement == null || tokenElement.isJsonNull()) {
            return new Response(
                    "131",
                    "Fields missing"
            ).toJson();
        }

        String userId = (userElement != null && !tokenElement.isJsonNull()) ? userElement.getAsString() : loggedInUserId;
        String token = tokenElement.getAsString();

        if(!loggedInUserToken.equals(token)) {
            return new Response(
                    "132",
                    "Invalid Token"
            ).toJson();
        }

        if(loggedInUserRole != 1 && !loggedInUserId.equals(userId)) {
            return new Response(
                    "133",
                    "Invalid Permission, user does not have permission to delete other users data"
            ).toJson();
        }

        try(Connection conn = Database.connect()) {
            int manipulatedLines = new AccountDAO(conn).delete(userId);

            if(manipulatedLines < 1) {
                return new Response(
                        "134",
                        "User not found ( Admin Only )"
                ).toJson();
            }

            if(loggedInUserId.equals(userId))
                loginService.destroyToken();

            return new Response(
                    "130",
                    "Account successfully deleted"
            ).toJson();
        } catch(SQLException e) {
            return new Response(
                    "135",
                    "Unknown error"
            ).toJson();
        }
    }
}
