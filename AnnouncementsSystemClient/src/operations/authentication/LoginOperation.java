package operations.authentication;

import operations.Operation;

public class LoginOperation extends Operation {
    private final String user;
    private final String password;

    public LoginOperation(String op, String user, String password) {
        super(op);
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Operation [op=" + op + ", user=" + user + ", password=" + password + "]";
    }
}
