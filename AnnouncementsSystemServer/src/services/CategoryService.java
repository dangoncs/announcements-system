package services;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.CategoryDAO;
import entities.Category;
import operations.category.CreateCategoryOp;
import operations.category.DeleteCategoryOp;
import operations.category.ReadCategoryOp;
import operations.category.UpdateCategoryOp;
import responses.CategoryResponse;
import responses.Response;

public class CategoryService {

    public static Response create(String operationJson, LoginService loginService) throws JsonSyntaxException {
        CreateCategoryOp createCategoryOp = new Gson().fromJson(operationJson, CreateCategoryOp.class);

        String token = createCategoryOp.token();
        List<Category> categoriesList = createCategoryOp.categories();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if (token == null || categoriesList == null)
            return new Response("201", "Missing fields");

        if (!token.equals(loggedInUserToken) || loggedInUserRole != 1)
            return new Response("202", "Invalid token");

        for (Category category : categoriesList)
            if (category.name() == null || category.name().isBlank())
                return new Response("201", "Missing fields");

        try {
            CategoryDAO.create(categoriesList);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category creation error: %s%n", e.getMessage());
            return new Response("203", "Unknown error");
        }

        return new Response("200", "Successful category creation");
    }

    public static Response read(String operationJson, LoginService loginService) throws JsonSyntaxException {
        ReadCategoryOp readCategoryOp = new Gson().fromJson(operationJson, ReadCategoryOp.class);

        String token = readCategoryOp.token();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if (token == null)
            return new Response("211", "Missing fields");

        if (!token.equals(loggedInUserToken) || loggedInUserRole != 1)
            return new Response("212", "Invalid token");

        List<Category> categoryList;
        try {
            categoryList = CategoryDAO.read();
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category read error: %s%n", e.getMessage());
            return new Response("213", "Unknown error");
        }

        return new CategoryResponse("210", "Successful category read", categoryList);
    }

    public static Response update(String operationJson, LoginService loginService) throws JsonSyntaxException {
        UpdateCategoryOp updateCategoryOp = new Gson().fromJson(operationJson, UpdateCategoryOp.class);

        String token = updateCategoryOp.token();
        List<Category> categoriesList = updateCategoryOp.categories();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if (token == null || categoriesList == null)
            return new Response("221", "Missing fields");

        if (!token.equals(loggedInUserToken) || loggedInUserRole != 1)
            return new Response("222", "Invalid token");

        for (Category category : categoriesList)
            if (category.id() == null || category.id().isBlank())
                return new Response("221", "Missing fields");

        try {
            if (CategoryDAO.update(categoriesList) == -1)
                return new Response("223", "Invalid information inserted");
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category update error: %s%n", e.getMessage());
            return new Response("224", "Unknown error");
        }

        return new Response("220", "Successful category update");
    }

    public static Response delete(String operationJson, LoginService loginService) throws JsonSyntaxException {
        DeleteCategoryOp deleteCategoryOp = new Gson().fromJson(operationJson, DeleteCategoryOp.class);

        String token = deleteCategoryOp.token();
        List<String> categoryIdsList = deleteCategoryOp.categoryIds();
        String loggedInUserToken = loginService.getLoggedInUserToken();
        int loggedInUserRole = loginService.getLoggedInUserRole();

        if (token == null || categoryIdsList == null)
            return new Response("231", "Missing fields");

        if (!token.equals(loggedInUserToken) || loggedInUserRole != 1)
            return new Response("232", "Invalid token");

        for (String categoryId : categoryIdsList)
            if (categoryId == null || categoryId.isBlank())
                return new Response("233", "Invalid information inserted");

        try {
            if (CategoryDAO.delete(categoryIdsList) == -1)
                return new Response("233", "Invalid information inserted");
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category deletion error: %s%n", e.getMessage());
            return new Response("234", "Category in use");
        }

        return new Response("230", "Successful category deletion");
    }
}
