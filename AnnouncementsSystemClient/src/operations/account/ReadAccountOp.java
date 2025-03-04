package operations.account;

public record ReadAccountOp(String op, String token, String user) {

    public ReadAccountOp(String token, String user) {
        this("2", token, user);
    }
}
