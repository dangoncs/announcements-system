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

    private AccountService() {
    }

    private static String truncateString(String str) {
        return (str.length() > 40) ? str.substring(0, 40) : str;
    }

    private static boolean isNotValidUser(String userId) {
        return !userId.matches("\\d{7}");
    }

    private static boolean isNotValidPassword(String password) {
        return !password.matches("\\d{4}");
    }

    public static Response create(String operationJson) throws JsonSyntaxException {
        CreateAccountOp createAccountOp = new Gson().fromJson(operationJson, CreateAccountOp.class);

        String userId = createAccountOp.user();
        String password = createAccountOp.password();
        String name = createAccountOp.name();

        if (userId == null || password == null || name == null)
            return new Response("101", Response.MISSING_FIELDS);

        if (isNotValidUser(userId) || isNotValidPassword(password) || name.isBlank())
            return new Response("104", Response.INVALID_INFORMATION);

        try {
            if (AccountDAO.read(userId) != null)
                return new Response("106", AccountResponse.DUPLICATE_USERNAME);

            AccountDAO.create(userId, truncateString(name), password);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account creation error: %s%n", e.getMessage());
            return new Response("105", Response.UNKNOWN_ERROR);
        }

        return new Response("100", Response.SUCCESS);
    }

    public static Response read(String operationJson, User userData) throws JsonSyntaxException {
        ReadAccountOp readAccountOp = new Gson().fromJson(operationJson, ReadAccountOp.class);

        String token = readAccountOp.token();
        String userId = readAccountOp.user();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (userId == null || userId.isBlank())
            userId = loggedInUserId;

        if (token == null)
            return new Response("111", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("112", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1 && !userId.equals(loggedInUserId))
            return new Response("113", Response.INSUFFICIENT_PERMISSIONS);

        Account account;
        try {
            account = AccountDAO.read(userId);
            if (account == null)
                return new Response("114", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account read error: %s%n", e.getMessage());
            return new Response("115", Response.UNKNOWN_ERROR);
        }

        return new AccountResponse("110", Response.SUCCESS, account);
    }

    public static Response update(String operationJson, User userData) throws JsonSyntaxException {
        UpdateAccountOp updateAccountOp = new Gson().fromJson(operationJson, UpdateAccountOp.class);

        String token = updateAccountOp.token();
        String userId = updateAccountOp.user();
        String name = updateAccountOp.name();
        String password = updateAccountOp.password();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (userId == null || userId.isBlank())
            userId = loggedInUserId;

        if (token == null)
            return new Response("121", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("122", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1 && !userId.equals(loggedInUserId))
            return new Response("123", Response.INSUFFICIENT_PERMISSIONS);

        try {
            Account account = AccountDAO.read(userId);
            if (account == null)
                return new Response("124", Response.INVALID_INFORMATION);

            if (password != null && !password.isBlank()) {
                if (account.role() != 1 && isNotValidPassword(password))
                    return new Response("124", Response.INVALID_INFORMATION);

                AccountDAO.updatePassword(userId, password);
            }

            if (name != null && !name.isBlank())
                AccountDAO.updateName(userId, truncateString(name));
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account update error: %s%n", e.getMessage());
            return new Response("125", Response.UNKNOWN_ERROR);
        }

        return new Response("120", Response.SUCCESS);
    }

    public static Response delete(String operationJson, LoginService loginService) throws JsonSyntaxException {
        DeleteAccountOp deleteAccountOp = new Gson().fromJson(operationJson, DeleteAccountOp.class);

        String token = deleteAccountOp.token();
        String userId = deleteAccountOp.user();
        String loggedInUserId = loginService.getLoggedInUser().userId();
        String loggedInUserToken = loginService.getLoggedInUser().token();
        int loggedInUserRole = loginService.getLoggedInUser().role();

        if (userId == null || userId.isBlank())
            userId = loggedInUserId;

        if (token == null)
            return new Response("131", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("132", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1 && !userId.equals(loggedInUserId))
            return new Response("133", Response.INSUFFICIENT_PERMISSIONS);

        try {
            if (AccountDAO.delete(userId) == 0)
                return new Response("134", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Account deletion error: %s%n", e.getMessage());
            return new Response("135", Response.UNKNOWN_ERROR);
        }

        if (userId.equals(loggedInUserId))
            loginService.setNoLongerLoggedIn(true);

        return new Response("130", Response.SUCCESS);
    }
}
