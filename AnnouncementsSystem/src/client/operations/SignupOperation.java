package client.operations;

public class SignupOperation extends Operation {
    private String user;
    private String password;
    private String name;
        
    public SignupOperation(String op, String user, String password, String name) {
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
