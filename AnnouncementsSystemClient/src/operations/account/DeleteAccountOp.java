package operations.account;

import operations.Operation;

public class DeleteAccountOp extends Operation {
    private final String token;
    private final String user;

    public DeleteAccountOp(String user, String token) {
        super("4");
        this.user = user;
        this.token = token;
    }

    @Override
    public String toString() {
        return "DeleteAccountOperation{" +
                "token='" + token + '\'' +
                "user='" + user + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
