package services;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import dao.AccountDAO;
import dao.Database;
import entities.Account;
import responses.AccountResponse;
import responses.Response;

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

    public static Response create(JsonObject jsonObject) {
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
            );
        }

        String userId = userElement.getAsString();
        String password = passwordElement.getAsString();
        String name = nameElement.getAsString();

        if(isNotValidUserId(userId) || isNotValidPassword(password) || isNotValidName(name)) {
            return new Response(
                    "102",
                    "Invalid information inserted: user or password"
            );
        }

        try(Connection conn = Database.connect()) {
            if(new AccountDAO(conn).searchByUser(userId) != null) {
                return new Response(
                        "103",
                        "Already exists an account with the username"
                );
            }

            name = truncateString(name);
            new AccountDAO(conn).create(userId, name, password);

            return new Response(
                    "100",
                    "Successful account creation"
            );
        } catch(SQLException e) {
            System.err.printf("[AVISO] Account creation error: %s%n", e.getLocalizedMessage());
            return new Response(
                    "104",
                    "Unknown error"
            );
        } finally {
            Database.disconnect();
        }
    }

    public static Response read(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "116",
                    "Cannot perform operation while logged out."
            );
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement tokenElement = jsonObject.get("token");

        String userId = (userElement != null && !userElement.isJsonNull()) ? userElement.getAsString() : loggedInUserId;
        String token = (tokenElement != null && !tokenElement.isJsonNull()) ? tokenElement.getAsString() : null;

        if (userId.isBlank())
            userId = loggedInUserId;

        if(loggedInUserRole != 1) {
            if(!loggedInUserToken.equals(token)) {
                return new Response(
                        "112",
                        "Invalid or empty token"
                );
            }

            if(!loggedInUserId.equals(userId)) {
                return new Response(
                        "113",
                        "Invalid Permission, user does not have permission to visualize other users data"
                );
            }
        }

        try(Connection conn = Database.connect()) {
            Account account = new AccountDAO(conn).searchByUser(userId);

            if(account == null) {
                return new Response(
                        "114",
                        "User not found ( Admin Only )"
                );
            }

            return new AccountResponse(
                    "11" + getAccountRole(account.userId()),
                    "Returns all information of the account",
                    loginService.generateToken(account.userId()),
                    account.userId(),
                    account.name(),
                    account.password()
            );
        } catch(SQLException e) {
            System.err.printf("[AVISO] Account read error: %s%n", e.getLocalizedMessage());
            return new Response(
                    "115",
                    "Unknown error"
            );
        } finally {
            Database.disconnect();
        }
    }

    public static Response update(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "125",
                    "Cannot perform operation while logged out."
            );
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement passwordElement = jsonObject.get("password");
        JsonElement nameElement = jsonObject.get("name");
        JsonElement tokenElement = jsonObject.get("token");

        String userId = (userElement != null && !userElement.isJsonNull()) ? userElement.getAsString() : loggedInUserId;
        String password = (passwordElement != null && !passwordElement.isJsonNull()) ? passwordElement.getAsString() : "";
        String name = (nameElement != null && !nameElement.isJsonNull()) ? nameElement.getAsString() : "";
        String token = (tokenElement != null && !tokenElement.isJsonNull()) ? tokenElement.getAsString() : null;

        if (userId.isBlank())
            userId = loggedInUserId;

        if(!loggedInUserToken.equals(token)) {
            return new Response(
                    "121",
                    "Invalid or empty token"
            );
        }

        if(loggedInUserRole != 1 && !loggedInUserId.equals(userId)) {
            return new Response(
                    "122",
                    "Invalid Permission, user does not have permission to update other users data"
            );
        }

        try(Connection conn = Database.connect()) {
            if(new AccountDAO(conn).searchByUser(userId) == null) {
                return new Response(
                        "123",
                        "No user or token found ( Admin Only )}"
                );
            }

            if (!name.isBlank()) {
                if (isNotValidName(name)) {
                    return new Response(
                            "126",
                            "Invalid information inserted"
                    );
                }
                new AccountDAO(conn).updateName(userId, truncateString(name));
            }

            if(!password.isBlank()) {
                if (isNotValidPassword(password)) {
                    return new Response(
                            "126",
                            "Invalid information inserted"
                    );
                }
                new AccountDAO(conn).updatePassword(userId, password);
            }

            return new Response(
                    "120",
                    "Account successfully updated"
            );
        } catch(SQLException e) {
            System.err.printf("[AVISO] Account update error: %s%n", e.getLocalizedMessage());
            return new Response(
                    "124",
                    "Unknown error"
            );
        } finally {
            Database.disconnect();
        }
    }

    public static Response delete(JsonObject jsonObject, LoginService loginService) {
        String loggedInUserId = loginService.getLoggedInUserId();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(loggedInUserId == null || loggedInUserToken == null) {
            return new Response(
                    "136",
                    "Cannot perform operation while logged out."
            );
        }

        JsonElement userElement = jsonObject.get("user");
        JsonElement tokenElement = jsonObject.get("token");

        if(tokenElement == null || tokenElement.isJsonNull()) {
            return new Response(
                    "131",
                    "Fields missing"
            );
        }

        String userId = (userElement != null && !tokenElement.isJsonNull()) ? userElement.getAsString() : loggedInUserId;
        String token = tokenElement.getAsString();

        if (userId.isBlank())
            userId = loggedInUserId;

        if(!loggedInUserToken.equals(token)) {
            return new Response(
                    "132",
                    "Invalid Token"
            );
        }

        if(loggedInUserRole != 1 && !loggedInUserId.equals(userId)) {
            return new Response(
                    "133",
                    "Invalid Permission, user does not have permission to delete other users data"
            );
        }

        try(Connection conn = Database.connect()) {
            int manipulatedLines = new AccountDAO(conn).delete(userId);

            if(manipulatedLines < 1) {
                return new Response(
                        "134",
                        "User not found ( Admin Only )"
                );
            }

            if(loggedInUserId.equals(userId))
                loginService.destroyToken();

            return new Response(
                    "130",
                    "Account successfully deleted"
            );
        } catch(SQLException e) {
            System.err.printf("[AVISO] Account deletion error: %s%n", e.getLocalizedMessage());
            return new Response(
                    "135",
                    "Unknown error"
            );
        } finally {
            Database.disconnect();
        }
    }
}
