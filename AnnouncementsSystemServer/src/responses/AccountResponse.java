package responses;

public class AccountResponse extends Response {
    private final String token;
    private final String user;
    private final String name;
    private final String password;

    public AccountResponse(String response, String message, String token, String user, String name, String password) {
        super(response, message);
        this.token = token;
        this.user = user;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "token='" + token + '\'' +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
