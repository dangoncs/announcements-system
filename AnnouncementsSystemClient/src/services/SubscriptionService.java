package services;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import exceptions.UnsuccessfulOperationException;
import main.Client;
import operations.category.SubscribeToCategoryOp;
import operations.category.UnsubscribeFromCategoryOp;
import responses.Response;

public class SubscriptionService {
    private final Client client;

    public SubscriptionService(Client client) {
        this.client = client;
    }

    public void create(String categoryId) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        SubscribeToCategoryOp subscribeToCategoryOp = new SubscribeToCategoryOp("15", token, categoryId);
        String opJson = new Gson().toJson(subscribeToCategoryOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("240"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public void delete(String categoryId) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        UnsubscribeFromCategoryOp unsubscribeFromCategoryOp = new UnsubscribeFromCategoryOp("16", token, categoryId);
        String opJson = new Gson().toJson(unsubscribeFromCategoryOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("250"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }
}
