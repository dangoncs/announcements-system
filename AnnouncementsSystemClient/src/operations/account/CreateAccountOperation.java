package operations.account;

import operations.Operation;

public class CreateAccountOperation extends Operation {
    private final String user;
    private final String password;
    private final String name;

    public CreateAccountOperation(String op, String user, String password, String name) {
        super(op);
        this.user = user;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Operation [op=" + op + ", user=" + user + ", name=" + name + ", password=" + password + "]";
    }
}
