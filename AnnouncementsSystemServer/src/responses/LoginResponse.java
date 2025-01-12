package responses;

public class LoginResponse extends Response {
    private final String token;

    public LoginResponse(String response, String message, String token) {
        super(response, message);
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
