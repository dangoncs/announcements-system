package operations.account;

import operations.Operation;

public class DeleteAccountOperation extends Operation {
    private final String token;

    public DeleteAccountOperation(String token) {
        super("4");
        this.token = token;
    }

    @Override
    public String toString() {
        return "DeleteAccountOperation{" +
                "token='" + token + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
