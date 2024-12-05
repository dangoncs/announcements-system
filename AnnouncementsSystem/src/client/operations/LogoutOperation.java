package client.operations;

public class LogoutOperation extends Operation {

    private final String token;

    public LogoutOperation(String op, String token) {
        super(op);
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
