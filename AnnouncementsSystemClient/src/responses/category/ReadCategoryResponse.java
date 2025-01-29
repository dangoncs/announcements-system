package responses.category;

import com.google.gson.JsonElement;
import responses.Response;

import java.util.List;

public class ReadCategoryResponse extends Response {

    public ReadCategoryResponse(String responseJson) {
        super(responseJson);
    }

    public List<JsonElement> getCategories() {
        if(jsonObject != null) {
            JsonElement categoriesElement = jsonObject.get("token");
            if(categoriesElement != null && !categoriesElement.isJsonNull() && categoriesElement.isJsonArray())
                return categoriesElement.getAsJsonArray().asList();
        }

        return null;
    }
}
