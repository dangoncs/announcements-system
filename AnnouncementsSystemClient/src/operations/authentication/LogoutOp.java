package operations.authentication;

public record LogoutOp(String op, String token) {

    public LogoutOp(String token) {
        this("6", token);
    }
}
