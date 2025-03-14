package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dao.AnnouncementDAO;
import dao.UserCategoryDAO;
import entities.Announcement;
import entities.User;
import operations.announcements.CreateAnnouncementOp;
import operations.announcements.DeleteAnnouncementOp;
import operations.announcements.ReadAnnouncementsOp;
import operations.announcements.UpdateAnnouncementOp;
import responses.AnnouncementResponse;
import responses.Response;

public class AnnouncementService {

    private AnnouncementService() {
    }

    public static Response create(String operationJson, User userData) throws JsonSyntaxException {
        CreateAnnouncementOp createAnnouncementOp = new Gson().fromJson(operationJson, CreateAnnouncementOp.class);

        String token = createAnnouncementOp.token();
        String title = createAnnouncementOp.title();
        String text = createAnnouncementOp.text();
        String categoryId = createAnnouncementOp.categoryId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null || title == null || text == null || categoryId == null)
            return new Response("301", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("302", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1)
            return new Response("303", Response.INSUFFICIENT_PERMISSIONS);

        if (title.isBlank() || text.isBlank() || categoryId.isBlank())
            return new Response("304", Response.INVALID_INFORMATION);

        try {
            AnnouncementDAO.create(title, text, categoryId);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Announcement creation error: %s%n", e.getMessage());
            return new Response("305", Response.UNKNOWN_ERROR);
        }

        return new Response("300", Response.SUCCESS);
    }

    public static Response read(String operationJson, User userData) throws JsonSyntaxException {
        ReadAnnouncementsOp readAnnouncementsOp = new Gson().fromJson(operationJson, ReadAnnouncementsOp.class);

        String token = readAnnouncementsOp.token();
        String loggedInUserId = userData.userId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null)
            return new Response("311", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("312", Response.INVALID_TOKEN);

        List<Announcement> announcementsList = new ArrayList<>();

        try {
            if (loggedInUserRole == 1)
                announcementsList = AnnouncementDAO.readAll();
            else
                for (String categoryId : UserCategoryDAO.read(loggedInUserId))
                    announcementsList.addAll(AnnouncementDAO.read(categoryId));
        } catch (SQLException e) {
            System.err.printf("[ERROR] Announcement read error: %s%n", e.getMessage());
            return new Response("315", Response.UNKNOWN_ERROR);
        }

        return new AnnouncementResponse("310", Response.SUCCESS, announcementsList);
    }

    public static Response update(String operationJson, User userData) throws JsonSyntaxException {
        UpdateAnnouncementOp updateAnnouncementOp = new Gson().fromJson(operationJson, UpdateAnnouncementOp.class);

        String token = updateAnnouncementOp.token();
        String id = updateAnnouncementOp.id();
        String title = updateAnnouncementOp.title();
        String text = updateAnnouncementOp.text();
        String categoryId = updateAnnouncementOp.categoryId();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null || id == null)
            return new Response("321", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("322", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1)
            return new Response("323", Response.INSUFFICIENT_PERMISSIONS);

        try {
            if (id.isBlank() || AnnouncementDAO.update(id, title, text, categoryId) == 0)
                return new Response("324", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Announcement update error: %s%n", e.getMessage());
            return new Response("325", Response.UNKNOWN_ERROR);
        }

        return new Response("320", Response.SUCCESS);
    }

    public static Response delete(String operationJson, User userData) throws JsonSyntaxException {
        DeleteAnnouncementOp deleteAnnouncementOp = new Gson().fromJson(operationJson, DeleteAnnouncementOp.class);

        String token = deleteAnnouncementOp.token();
        String id = deleteAnnouncementOp.id();
        String loggedInUserToken = userData.token();
        int loggedInUserRole = userData.role();

        if (token == null || id == null)
            return new Response("331", Response.MISSING_FIELDS);

        if (!token.equals(loggedInUserToken))
            return new Response("332", Response.INVALID_TOKEN);

        if (loggedInUserRole != 1)
            return new Response("333", Response.INSUFFICIENT_PERMISSIONS);

        try {
            if (id.isBlank() || AnnouncementDAO.delete(id) == 0)
                return new Response("334", Response.INVALID_INFORMATION);
        } catch (SQLException e) {
            System.err.printf("[ERROR] Announcement deletion error: %s%n", e.getMessage());
            return new Response("335", Response.UNKNOWN_ERROR);
        }

        return new Response("330", Response.SUCCESS);
    }
}
