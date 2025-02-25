package operations.authentication;

import operations.Operation;

public class LogoutOp extends Operation {

    private final String token;

    public LogoutOp(String token) {
        super("6");
        this.token = token;
    }

    @Override
    public String toString() {
        return "LogoutOperation{" +
                "token='" + token + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
