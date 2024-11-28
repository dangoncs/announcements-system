package server.services;

import server.entities.Account;

public class InputValidator {

    public static boolean isValidUserId(String userId) {
        return (userId != null) && (userId.length() == 7) && (userId.matches("\\d{7}"));
    }

    public static boolean isValidName(String name) {
        return name != null;
    }

    public static boolean isValidPassword(String password) {
        return (password != null) && (password.length() == 4) && (password.matches("\\d{4}"));
    }

    public static boolean isValidAccountInfo(Account account) {
        return isValidUserId(account.getUserId()) && isValidName(account.getName()) && isValidPassword(account.getPassword());
    }

    public static String truncateString(String str, int length) {
        if(str == null)
            return null;

        if(str.length() > length)
            return str.substring(0, length);

        return str;
    }
}
