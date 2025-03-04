package operations.account;

public record DeleteAccountOp(String op, String token, String user) {

    public DeleteAccountOp(String token, String user) {
        this("4", token, user);
    }
}
