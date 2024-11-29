package server.responses;

import com.google.gson.Gson;

public class Response {

    protected String response;
    protected String message;

    public Response(String response, String message) {
        this.response = response;
        this.message = message;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
