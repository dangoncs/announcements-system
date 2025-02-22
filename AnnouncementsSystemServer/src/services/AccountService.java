package services;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.AccountDAO;
import entities.Account;
import entities.User;
import operations.account.CreateAccountOp;
import operations.account.DeleteAccountOp;
import operations.account.ReadAccountOp;
import operations.account.UpdateAccountOp;
import responses.AccountResponse;
import responses.Response;

public class AccountService {

    private static String truncateString(String str) {
        return (str.length() > 40) ? str.substring(0, 40) : str;
    }

    private static boolean isNotValidUser(String userId) {
        return !userId.matches("\\d{7}");
    }

    private static boolean isNotValidPassword(String password) {
        return !password.matches("\\d{4}");
    }

    public static int getAccountRole(String userId) {
        return userId.matches("\\d{7}") && (!userId.equals("0000000")) ? 0 : 1;
    }

    public static Response create(String operationJson) throws JsonSyntaxException {
        CreateAccountOp createAccountOp = new Gson().fromJson(operationJson, CreateAccountOp.class);

        String userId = createAccountOp.user();
        String password = createAccountOp.password();
        String name = createAccountOp.name();

        if (userId == null || password == null || name == null)
            return new Response("101", "Fields missing");

        if (isNotValidUser(userId) || isNotValidPassword(password) || name.isBlank())
            return new Response("102", "Invalid information inserted: user or password");

        try {
            if (new AccountDAO().read(userId) != null)
                return new Response("103", "Already exists an account with the username");

            new AccountDAO().create(userId, truncateString(name), password);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account creation error: %s%n", e.getMessage());
            return new Response("104", "Unknown error");
        }

        return new Response("100", "Successful account creation");
    }

    public static Response read(String operationJson, User userData) throws JsonSyntaxException {
        ReadAccountOp readAccountOp = new Gson().fromJson(operationJson, ReadAccountOp.class);

        String userId = readAccountOp.user();
        String token = readAccountOp.token();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (userId == null || userId.isBlank())
            userId = loggedInUserId;

        if (token == null || !token.equals(loggedInUserToken))
            return new Response("112", "Invalid or empty token");

        if (loggedInUserRole != 1 && !userId.equals(loggedInUserId)) {
            return new Response(
                    "113",
                    "Invalid Permission, user does not have permission to visualize other users data");
        }

        Account account;
        try {
            account = new AccountDAO().read(userId);
            if (account == null)
                return new Response("114", "User not found ( Admin Only )");
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account read error: %s%n", e.getMessage());
            return new Response("115", "Unknown error");
        }

        return new AccountResponse(
                "11" + getAccountRole(userId),
                "Returns all information of the account",
                account.userId(),
                account.name(),
                account.password());
    }

    public static Response update(String operationJson, User userData) throws JsonSyntaxException {
        UpdateAccountOp updateAccountOp = new Gson().fromJson(operationJson, UpdateAccountOp.class);

        String userId = updateAccountOp.user();
        String name = updateAccountOp.name();
        String password = updateAccountOp.password();
        String token = updateAccountOp.token();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (userId == null || userId.isBlank())
            userId = loggedInUserId;

        if (token == null || !token.equals(loggedInUserToken))
            return new Response("121", "Invalid or empty token");

        if (loggedInUserRole != 1 && !userId.equals(loggedInUserId)) {
            return new Response(
                    "122",
                    "Invalid Permission, user does not have permission to update other users data");
        }

        try {
            if (name != null && !name.isBlank())
                if (new AccountDAO().updateName(userId, truncateString(name)) == 0)
                    return new Response("123", "No user or token found ( Admin Only )");

            if (password != null && !password.isBlank()) {
                if (getAccountRole(userId) != 1 && isNotValidPassword(password))
                    return new Response("125", "Invalid information inserted");

                if (new AccountDAO().updatePassword(userId, password) == 0)
                    return new Response("123", "No user or token found ( Admin Only )");
            }
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account update error: %s%n", e.getMessage());
            return new Response("124", "Unknown error");
        }

        return new Response("120", "Account successfully updated");
    }

    public static Response delete(String operationJson, LoginService loginService) throws JsonSyntaxException {
        DeleteAccountOp deleteAccountOp = new Gson().fromJson(operationJson, DeleteAccountOp.class);

        String userId = deleteAccountOp.user();
        String token = deleteAccountOp.token();
        String loggedInUserId = loginService.getLoggedInUser().userId();
        String loggedInUserToken = loginService.getLoggedInUser().token();
        int loggedInUserRole = loginService.getLoggedInUser().role();

        if (userId == null || userId.isBlank())
            userId = loggedInUserId;

        if (token == null)
            return new Response("131", "Fields missing");

        if (!token.equals(loggedInUserToken))
            return new Response("132", "Invalid Token");

        if (loggedInUserRole != 1 && !userId.equals(loggedInUserId)) {
            return new Response(
                    "133",
                    "Invalid Permission, user does not have permission to delete other users data");
        }

        try {
            if (new AccountDAO().delete(userId) == 0)
                return new Response("134", "User not found ( Admin Only )");
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account deletion error: %s%n", e.getMessage());
            return new Response("135", "Unknown error");
        }

        if (userId.equals(loggedInUserId))
            loginService.handleAccountDeletion();

        return new Response("130", "Account successfully deleted");
    }
}
