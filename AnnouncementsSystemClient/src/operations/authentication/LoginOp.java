package operations.authentication;

public record LoginOp(String op, String user, String password) {

    public LoginOp(String user, String password) {
        this("5", user, password);
    }
}
