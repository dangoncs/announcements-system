package operations.account;

public record UpdateAccountOp(String op, String token, String user, String name, String password) {

    public UpdateAccountOp(String token, String user, String name, String password) {
        this("3", token, user, name, password);
    }
}
