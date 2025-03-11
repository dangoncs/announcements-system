package services;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import entities.Account;
import exceptions.UnsuccessfulOperationException;
import main.Client;
import operations.account.CreateAccountOp;
import operations.account.DeleteAccountOp;
import operations.account.ReadAccountOp;
import operations.account.UpdateAccountOp;
import responses.Response;
import responses.account.ReadAccountResponse;

public class AccountService {
    private final Client client;

    public AccountService(Client client) {
        this.client = client;
    }

    public void create(String userId, String name, String password)
            throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        CreateAccountOp createAccountOp = new CreateAccountOp("1", userId, name, password);
        String opJson = new Gson().toJson(createAccountOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("100"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public Account read(String userId) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        ReadAccountOp readAccountOp = new ReadAccountOp("2", token, userId);
        String opJson = new Gson().toJson(readAccountOp);
        String responseJson = client.sendToServer(opJson);
        ReadAccountResponse responseObj = new Gson().fromJson(responseJson, ReadAccountResponse.class);

        String responseCode = responseObj.response();
        Account account = responseObj.account();

        if (responseCode == null || account == null || (!responseCode.equals("110") && !responseCode.equals("111")))
            throw new UnsuccessfulOperationException(responseObj.message());

        return account;
    }

    public void update(String userId, String name, String password)
            throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        UpdateAccountOp updateAccountOp = new UpdateAccountOp("3", token, userId, name, password);
        String opJson = new Gson().toJson(updateAccountOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("100"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public void delete(String userId) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        DeleteAccountOp deleteAccountOp = new DeleteAccountOp("4", token, userId);
        String opJson = new Gson().toJson(deleteAccountOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("100"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }
}
