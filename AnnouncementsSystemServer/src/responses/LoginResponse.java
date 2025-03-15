package responses;

import entities.User;

public class LoginResponse extends Response {
    public static final String ALREADY_LOGGED_IN = "User already logged in";
    public static final String NOT_LOGGED_IN = "User not logged in";
    private final User user;

    public LoginResponse(String responseCode, String message, User user) {
        super(responseCode, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
