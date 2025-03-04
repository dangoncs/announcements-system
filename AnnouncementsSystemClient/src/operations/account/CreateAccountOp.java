package operations.account;

public record CreateAccountOp(String op, String user, String name, String password) {

    public CreateAccountOp(String user, String name, String password) {
        this("1", user, name, password);
    }
}
