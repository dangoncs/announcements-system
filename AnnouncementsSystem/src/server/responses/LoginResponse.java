package server.responses;

public class LoginResponse extends Response {

    private String token;

    public LoginResponse(String response, String message, String token) {
        super(response, message);
        this.token = token;
    }
}
