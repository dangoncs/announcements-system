package services;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import entities.Announcement;
import exceptions.UnsuccessfulOperationException;
import main.Client;
import operations.announcement.CreateAnnouncementOp;
import operations.announcement.DeleteAnnouncementOp;
import operations.announcement.ReadAnnouncementOp;
import operations.announcement.UpdateAnnouncementOp;
import responses.Response;
import responses.announcement.ReadAnnouncementsResponse;

public class AnnouncementService {
    private final Client client;

    public AnnouncementService(Client client) {
        this.client = client;
    }

    public void create(String title, String text, String categoryId)
            throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        CreateAnnouncementOp createAnnouncementOp = new CreateAnnouncementOp("11", token, title, text, categoryId);
        String opJson = new Gson().toJson(createAnnouncementOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("300"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public List<Announcement> read() throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        ReadAnnouncementOp readAnnouncementOp = new ReadAnnouncementOp("12", token);
        String opJson = new Gson().toJson(readAnnouncementOp);
        String responseJson = client.sendToServer(opJson);
        ReadAnnouncementsResponse responseObj = new Gson().fromJson(responseJson, ReadAnnouncementsResponse.class);

        String responseCode = responseObj.response();
        List<Announcement> announcements = responseObj.announcements();

        if (responseCode == null || announcements == null || !responseCode.equals("310"))
            throw new UnsuccessfulOperationException(responseObj.message());

        return announcements;
    }

    public void update(String id, String title, String text, String categoryId)
            throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        UpdateAnnouncementOp updateAnnouncementOp = new UpdateAnnouncementOp("13", token, id, title, text, categoryId);
        String opJson = new Gson().toJson(updateAnnouncementOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("320"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }

    public void delete(String id) throws IOException, JsonSyntaxException, UnsuccessfulOperationException {
        String token = client.getUserData().token();

        DeleteAnnouncementOp deleteAnnouncementOp = new DeleteAnnouncementOp("14", token, id);
        String opJson = new Gson().toJson(deleteAnnouncementOp);
        String responseJson = client.sendToServer(opJson);
        Response responseObj = new Gson().fromJson(responseJson, Response.class);

        String responseCode = responseObj.response();

        if (responseCode == null || !responseCode.equals("330"))
            throw new UnsuccessfulOperationException(responseObj.message());
    }
}
