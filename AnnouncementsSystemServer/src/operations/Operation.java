package operations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Operation {
    private JsonObject jsonObject;

    public void parseString(String json) {
        try {
            jsonObject = JsonParser.parseString(json).getAsJsonObject();
        } catch (Exception e) {
            jsonObject = null;
            System.err.printf("[ERROR] Could not parse JSON: %s%n", e.getLocalizedMessage());
        }

    }

    public String getElementValue(String element) {
        if(jsonObject != null) {
            JsonElement jsonElement = jsonObject.get(element);
            if (jsonElement != null && jsonElement.isJsonPrimitive() && !jsonElement.isJsonNull())
                return jsonElement.getAsString();
        }

        return null;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
