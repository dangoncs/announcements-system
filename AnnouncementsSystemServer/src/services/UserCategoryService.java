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

    public static Response subscribe(String operationJson, User userData) throws JsonSyntaxException {
        SubscribeToCategoryOp subscribeToCategoryOp = new Gson().fromJson(operationJson, SubscribeToCategoryOp.class);

        String categoryId = subscribeToCategoryOp.categoryId();
        String token = subscribeToCategoryOp.token();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();

        if (categoryId == null || categoryId.isBlank())
            return new Response("341", "Missing fields");

        if (token == null || !token.equals(loggedInUserToken))
            return new Response("342", "Invalid token");

        try {
            if (UserCategoryDAO.read(loggedInUserId).contains(categoryId))
                return new Response("343", "Invalid information inserted");

            if (CategoryDAO.read(categoryId) == null)
                return new Response("343", "Invalid information inserted");

            UserCategoryDAO.create(loggedInUserId, categoryId);
        } catch (SQLException e) {
            return new Response("344", "Unknown error");
        }

        return new Response("340", "Successful subscription");
    }

    public static Response unsubscribe(String operationJson, User userData) throws JsonSyntaxException {
        UnsubscribeFromCategoryOp unsubscribeFromCategoryOp = new Gson().fromJson(operationJson,
                UnsubscribeFromCategoryOp.class);

        String categoryId = unsubscribeFromCategoryOp.categoryId();
        String token = unsubscribeFromCategoryOp.token();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();

        if (categoryId == null || categoryId.isBlank())
            return new Response("351", "Missing fields");

        if (token == null || !token.equals(loggedInUserToken))
            return new Response("352", "Invalid token");

        try {
            if (UserCategoryDAO.delete(loggedInUserId, categoryId) == 0)
                return new Response("353", "Invalid information inserted");
        } catch (SQLException e) {
            return new Response("354", "Unknown error");
        }

        return new Response("350", "Successfully unsubscribed");
    }
}
