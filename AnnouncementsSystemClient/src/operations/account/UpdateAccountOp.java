package operations.account;

public record UpdateAccountOp(String op, String token, String user, String name, String password) {
}
