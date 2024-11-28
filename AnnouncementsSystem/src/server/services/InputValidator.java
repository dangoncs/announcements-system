package server.services;

import server.dao.AccountDAO;
import server.dao.Database;
import server.entities.Account;
import server.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class InputValidator {

    public static boolean isValidString(String str) {
        return str != null;
    }

    public static boolean isValidUserId(String userId) {
        return (userId.length() == 7) && (userId.matches("\\d{7}"));
    }

    public static boolean isValidPassword(String password) {
        return (password.length() == 4) && (password.matches("\\d{4}"));
    }

    public static String truncateString(String str, int length) {
        if(str == null)
            return null;

        if(str.length() > length)
            return str.substring(0, length);

        return str;
    }

    public static boolean isAuthorized(String userId, String token) {
        return userId.equals(token);
    }
}
