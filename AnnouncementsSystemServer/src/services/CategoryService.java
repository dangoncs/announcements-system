package services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dao.CategoryDAO;
import entities.Category;
import responses.CategoryResponse;
import responses.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public static Response create(JsonObject jsonObject, LoginService loginService) {
        JsonElement categoriesElement = jsonObject.get("categories");
        JsonElement tokenElement = jsonObject.get("token");

        if (categoriesElement == null || categoriesElement.isJsonNull() || tokenElement == null || tokenElement.isJsonNull())
            return new Response("201", "Missing fields");

        List<JsonElement> categoriesList = categoriesElement.getAsJsonArray().asList();
        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!loggedInUserToken.equals(token) || loggedInUserRole != 1)
            return new Response("202", "Invalid token");

        List<Category> categoriesToCreate = new ArrayList<>();

        for (JsonElement categoryElement : categoriesList) {
            JsonObject categoryObject;

            try {
                categoryObject = categoryElement.getAsJsonObject();
            } catch (Exception e) {
                return new Response("201", "Missing fields");
            }

            JsonElement nameElement = categoryObject.get("name");
            JsonElement descriptionElement = categoryObject.get("description");

            if (nameElement == null || nameElement.isJsonNull())
                return new Response("201", "Missing fields");

            String name = nameElement.getAsString();
            String description = (descriptionElement != null && !descriptionElement.isJsonNull()) ? descriptionElement.getAsString() : "";

            categoriesToCreate.add(new Category("", name, description));
        }

        try {
            CategoryDAO.create(categoriesToCreate);
        } catch (SQLException e) {
            return new Response("203", "Unknown error");
        }

        return new Response("200", "Successful category creation");
    }

    public static Response read(JsonObject jsonObject, LoginService loginService) {
        JsonElement tokenElement = jsonObject.get("token");

        if (tokenElement == null || tokenElement.isJsonNull())
            return new Response("211", "Missing fields");

        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!token.equals(loggedInUserToken) || loggedInUserRole != 1)
            return new Response("212", "Invalid token");

        List<Category> categoryList;
        try {
            categoryList = CategoryDAO.readAll();
        } catch (SQLException e) {
            return new Response("213", "Unknown error");
        }

        return new CategoryResponse("210", "Successful category read", categoryList);
    }

    public static Response update(JsonObject jsonObject, LoginService loginService) {
        JsonElement categoriesElement = jsonObject.get("categories");
        JsonElement tokenElement = jsonObject.get("token");

        if (categoriesElement == null || categoriesElement.isJsonNull() || !categoriesElement.isJsonArray() || tokenElement == null || tokenElement.isJsonNull())
            return new Response("221", "Missing fields");

        List<JsonElement> categoriesList = categoriesElement.getAsJsonArray().asList();
        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!loggedInUserToken.equals(token) || loggedInUserRole != 1)
            return new Response("222", "Invalid token");

        List<Category> categoriesToUpdate = new ArrayList<>();

        for (JsonElement categoryElement : categoriesList) {
            JsonObject categoryObject;

            try {
                categoryObject = categoryElement.getAsJsonObject();
            } catch (Exception e) {
                return new Response("221", "Missing fields");
            }

            JsonElement categoryIdElement = categoryObject.get("id");
            JsonElement nameElement = categoryObject.get("name");
            JsonElement descriptionElement = categoryObject.get("description");

            if (categoryIdElement == null || categoryIdElement.isJsonNull())
                return new Response("221", "Missing fields");

            String categoryId = categoryIdElement.getAsString();
            String name = (nameElement != null && !nameElement.isJsonNull()) ? nameElement.getAsString() : "";
            String description = (descriptionElement != null && !descriptionElement.isJsonNull()) ? descriptionElement.getAsString() : "";

            try {
                if(CategoryDAO.read(categoryId) == null)
                    return new Response("223", "Invalid information inserted");
            } catch (SQLException e) {
                return new Response("224", "Unknown error");
            }

            categoriesToUpdate.add(new Category(categoryId, name, description));
        }

        try {
            CategoryDAO.update(categoriesToUpdate);
        } catch (SQLException e) {
            return new Response("224", "Unknown error");
        }

        return new Response("220", "Successful category update");
    }

    public static Response delete(JsonObject jsonObject, LoginService loginService) {
        JsonElement categoryIdsElement = jsonObject.get("categoryIds");
        JsonElement tokenElement = jsonObject.get("token");

        if (categoryIdsElement == null || categoryIdsElement.isJsonNull() || tokenElement == null || tokenElement.isJsonNull())
            return new Response("231", "Missing fields");

        List<JsonElement> categoryIdsList = categoryIdsElement.getAsJsonArray().asList();
        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!loggedInUserToken.equals(token) || loggedInUserRole != 1)
            return new Response("232", "Invalid token");

        List<String> categoriesToDelete = new ArrayList<>();

        for (JsonElement categoryIdElement : categoryIdsList) {
            if (categoryIdElement == null || categoryIdElement.isJsonNull())
                return new Response("231", "Missing fields");

            categoriesToDelete.add(categoryIdElement.getAsString());

            try {
                if (CategoryDAO.read(categoryIdElement.getAsString()) == null)
                    return new Response("233", "Invalid information inserted");
            } catch (SQLException e) {
                return new Response("235", "Unknown error");
            }
        }

        try {
            CategoryDAO.delete(categoriesToDelete);
        } catch (SQLException e) {
            return new Response("234", "Category in use");
        }

        return new Response("230", "Successful category deletion");
    }
}
