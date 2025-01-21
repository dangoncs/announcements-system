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

        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!loggedInUserToken.equals(token) || loggedInUserRole != 1)
            return new Response("202", "Invalid token");

        List<Category> categoryList = new ArrayList<>();
        //TODO: implement missing fields response

        try {
            CategoryDAO.create(categoryList);
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

        if (categoriesElement == null || categoriesElement.isJsonNull() || tokenElement == null || tokenElement.isJsonNull())
            return new Response("221", "Missing fields");

        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!loggedInUserToken.equals(token) || loggedInUserRole != 1)
            return new Response("222", "Invalid token");

        List<Category> categoryList = new ArrayList<>();
        //TODO: implement other responses

        try {
            CategoryDAO.update(categoryList);
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

        List<Category> categoryList = new ArrayList<>();

        String token = tokenElement.getAsString();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if(!loggedInUserToken.equals(token) || loggedInUserRole != 1)
            return new Response("232", "Invalid token");
        //TODO: implement other responses

        try {
            CategoryDAO.delete(categoryList);
        } catch (SQLException e) {
            return new Response("235", "Unknown error");
        }

        return new Response("230", "Successful category deletion");
    }
}
