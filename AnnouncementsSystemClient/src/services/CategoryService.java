package services;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import entities.Category;
import exceptions.UnsuccessfulOperationException;
import main.Client;
import operations.category.CreateCategoryOp;
import operations.category.DeleteCategoryOp;
import operations.category.ReadCategoriesOp;
import operations.category.UpdateCategoryOp;
import responses.ReadCategoriesResponse;
import responses.Response;

public class CategoryService {
    private final Client client;

    public CategoryService(Client client) {
        this.client = client;
    }

    public void create(Category category) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        CreateCategoryOp createCategoryOp = new CreateCategoryOp("7", token, category);
        String opJson = new Gson().toJson(createCategoryOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("200"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public List<Category> read() throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        ReadCategoriesOp readCategoriesOp = new ReadCategoriesOp("8", token);
        String opJson = new Gson().toJson(readCategoriesOp);
        String responseJson = client.sendToServer(opJson);
        ReadCategoriesResponse responseObj = new Gson().fromJson(responseJson, ReadCategoriesResponse.class);

        String responseCode = responseObj.response();
        List<Category> categories = responseObj.categories();

        if (responseCode == null || categories == null || !responseCode.equals("210"))
            throw new UnsuccessfulOperationException(responseObj.message());

        return categories;
    }

    public void update(Category category) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        UpdateCategoryOp updateCategoryOp = new UpdateCategoryOp("9", token, category);
        String opJson = new Gson().toJson(updateCategoryOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("220"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public void delete(String categoryId) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        DeleteCategoryOp deleteCategoryOp = new DeleteCategoryOp("10", token, categoryId);
        String opJson = new Gson().toJson(deleteCategoryOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("230"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }
}
