package client.responses;

import com.google.gson.JsonElement;

public class LoginResponse extends Response {

    public LoginResponse(String responseJson) {
        super(responseJson);
    }

    public String getToken() {
        if(jsonObject != null) {
            JsonElement tokenElement = jsonObject.get("token");
            if(tokenElement != null && !tokenElement.isJsonNull())
                return tokenElement.getAsString();
        }

        return null;
    }
}
