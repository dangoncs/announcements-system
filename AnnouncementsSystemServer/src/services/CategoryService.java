package services;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.AnnouncementDAO;
import dao.CategoryDAO;
import entities.Category;
import entities.User;
import operations.category.CreateCategoryOp;
import operations.category.DeleteCategoryOp;
import operations.category.ReadCategoryOp;
import operations.category.UpdateCategoryOp;
import responses.CategoryResponse;
import responses.Response;

public class CategoryService {

    private CategoryService() {
    }

    public static Response create(String operationJson, User userData) throws JsonSyntaxException {
        CreateCategoryOp createCategoryOp = new Gson().fromJson(operationJson, CreateCategoryOp.class);

        String token = createCategoryOp.token();
        String name = createCategoryOp.name();
        String description = createCategoryOp.description();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null || name == null)
            return new Response("201", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("202", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1)
            return new Response("203", Response.INSUFFICIENT_PERMISSIONS);

        if (name.isBlank())
            return new Response("204", Response.INVALID_INFORMATION);

        try {
            CategoryDAO.create(name, description);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category creation error: %s%n", e.getMessage());
            return new Response("205", Response.UNKNOWN_ERROR);
        }

        return new Response("200", Response.SUCCESS);
    }

    public static Response read(String operationJson, User userData) throws JsonSyntaxException {
        ReadCategoryOp readCategoryOp = new Gson().fromJson(operationJson, ReadCategoryOp.class);

        String token = readCategoryOp.token();
        String loggedInUserToken = userData.token();
        String loggedInUserId = userData.userId();

        if (token == null)
            return new Response("211", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("212", Response.INVALID_TOKEN);

        List<Category> categoryList;
        try {
            categoryList = CategoryDAO.readAll(loggedInUserId);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category read error: %s%n", e.getMessage());
            return new Response("215", Response.UNKNOWN_ERROR);
        }

        return new CategoryResponse("210", Response.SUCCESS, categoryList);
    }

    public static Response update(String operationJson, User userData) throws JsonSyntaxException {
        UpdateCategoryOp updateCategoryOp = new Gson().fromJson(operationJson, UpdateCategoryOp.class);

        String token = updateCategoryOp.token();
        String id = updateCategoryOp.id();
        String name = updateCategoryOp.name();
        String description = updateCategoryOp.description();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null || id == null)
            return new Response("221", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("222", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1)
            return new Response("223", Response.INSUFFICIENT_PERMISSIONS);

        if (id.isBlank())
            return new Response("224", Response.INVALID_INFORMATION);

        try {
            if (name != null && !name.isBlank() && CategoryDAO.updateName(id, name) == 0)
                return new Response("224", Response.INVALID_INFORMATION);

            if (description != null && !description.isBlank() && CategoryDAO.updateDescription(id, description) == 0)
                return new Response("224", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category update error: %s%n", e.getMessage());
            return new Response("225", Response.UNKNOWN_ERROR);
        }

        return new Response("220", Response.SUCCESS);
    }

    public static Response delete(String operationJson, User userData) throws JsonSyntaxException {
        DeleteCategoryOp deleteCategoryOp = new Gson().fromJson(operationJson, DeleteCategoryOp.class);

        String token = deleteCategoryOp.token();
        String id = deleteCategoryOp.id();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null || id == null)
            return new Response("231", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("232", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1)
            return new Response("233", Response.INSUFFICIENT_PERMISSIONS);

        if (id.isBlank())
            return new Response("234", Response.INVALID_INFORMATION);

        try {
            if (!AnnouncementDAO.readByCategory(id).isEmpty())
                return new Response("236", CategoryResponse.CATEGORY_IN_USE);

            if (CategoryDAO.delete(id) == 0)
                return new Response("234", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Category deletion error: %s%n", e.getMessage());
            return new Response("235", Response.UNKNOWN_ERROR);
        }

        return new Response("230", Response.SUCCESS);
    }
}
