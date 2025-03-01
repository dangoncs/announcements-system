package utilities;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class JsonUtilities {

    public static JsonObject parseJson(String responseJson) {
        JsonElement jsonElement;

        try {
            jsonElement = JsonParser.parseString(responseJson);
        } catch (JsonParseException e) {
            return null;
        }

        if (jsonElement == null || !jsonElement.isJsonObject())
            return null;
        
        return jsonElement.getAsJsonObject();
    }

    public static String getElementValue(String element, JsonObject jsonObject) {
        JsonElement jsonElement = jsonObject.get(element);
        if (jsonElement != null && jsonElement.isJsonPrimitive() && !jsonElement.isJsonNull())
            return jsonElement.getAsString();

        return "Invalid response received. Response does not contain " + element;
    }

    public static JsonArray getArray(String element, JsonObject jsonObject) {
        return jsonObject.getAsJsonArray(element);
    }

    public static List<JsonObject> getAsObjectList(String element, JsonObject jsonObject) {
        JsonElement jsonElement = jsonObject.get(element);
        if (jsonElement == null || !jsonElement.isJsonArray())
            return null;

        JsonArray array = jsonElement.getAsJsonArray();
        List<JsonObject> objectList = new ArrayList<>();

        for (JsonElement elem : array) {
            if (elem != null && elem.isJsonObject())
                objectList.add(elem.getAsJsonObject());
        }

        return objectList;
    }
}
