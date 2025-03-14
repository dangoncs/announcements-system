package services;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.CategoryDAO;
import dao.UserCategoryDAO;
import entities.User;
import operations.category.SubscribeToCategoryOp;
import operations.category.UnsubscribeFromCategoryOp;
import responses.Response;

public class UserCategoryService {

    private UserCategoryService() {
    }

    public static Response subscribe(String operationJson, User userData) throws JsonSyntaxException {
        SubscribeToCategoryOp subscribeToCategoryOp = new Gson().fromJson(operationJson, SubscribeToCategoryOp.class);

        String token = subscribeToCategoryOp.token();
        String categoryId = subscribeToCategoryOp.categoryId();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();

        if (token == null || categoryId == null)
            return new Response("241", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("242", Response.INVALID_TOKEN);

        try {
            if (categoryId.isBlank() || CategoryDAO.read(categoryId) == null)
                return new Response("244", Response.INVALID_INFORMATION);

            if (UserCategoryDAO.read(loggedInUserId).contains(categoryId))
                return new Response("244", Response.INVALID_INFORMATION);

            UserCategoryDAO.create(loggedInUserId, categoryId);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Subscription to category error: %s%n", e.getMessage());
            return new Response("245", Response.UNKNOWN_ERROR);
        }

        return new Response("240", Response.SUCCESS);
    }

    public static Response unsubscribe(String operationJson, User userData) throws JsonSyntaxException {
        UnsubscribeFromCategoryOp unsubscribeFromCategoryOp = new Gson().fromJson(operationJson,
                UnsubscribeFromCategoryOp.class);

        String token = unsubscribeFromCategoryOp.token();
        String categoryId = unsubscribeFromCategoryOp.categoryId();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();

        if (token == null || categoryId == null)
            return new Response("251", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("252", Response.INVALID_TOKEN);

        try {
            if (categoryId.isBlank() || UserCategoryDAO.delete(loggedInUserId, categoryId) == 0)
                return new Response("254", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Unsubscription from category error: %s%n", e.getMessage());
            return new Response("255", Response.UNKNOWN_ERROR);
        }

        return new Response("250", Response.SUCCESS);
    }
}
