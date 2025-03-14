package responses;

public class Response {
    public static final String MISSING_FIELDS = "Missing required fields";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String INSUFFICIENT_PERMISSIONS = "Your account does not have sufficient permissions to perform this action";
    public static final String INVALID_INFORMATION = "Invalid information inserted";
    public static final String UNKNOWN_ERROR = "Unknown error";
    public static final String SUCCESS = "Successfully performed operation";
    protected final String responseCode;
    protected final String message;

    public Response(String responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
