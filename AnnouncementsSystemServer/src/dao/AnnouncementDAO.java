package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entities.Announcement;

public class AnnouncementDAO {

    private AnnouncementDAO() {
    }

    private static int updateTitle(String announcementId, String title) throws SQLException {
        String sql = "UPDATE announcement SET title = ? WHERE announcement_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, announcementId);

                return ps.executeUpdate();
            }
        }
    }

    private static int updateText(String announcementId, String text) throws SQLException {
        String sql = "UPDATE announcement SET text = ? WHERE announcement_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, text);
                ps.setString(2, announcementId);

                return ps.executeUpdate();
            }
        }
    }

    private static int updateCategoryId(String announcementId, String categoryId) throws SQLException {
        String sql = "UPDATE announcement SET category_id = ? WHERE announcement_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, categoryId);
                ps.setString(2, announcementId);

                return ps.executeUpdate();
            }
        }
    }

    public static void create(String title, String text, String categoryId) throws SQLException {
        String sql = "INSERT INTO announcement (title, text, date, category_id) VALUES (?, ?, ?, ?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = LocalDate.now().format(formatter);

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, text);
                ps.setString(3, date);
                ps.setString(4, categoryId);

                ps.executeUpdate();
            }
        }
    }

    public static List<Announcement> readByCategory(String categoryId) throws SQLException {
        String sql = "SELECT announcement_id, title, text, date, category_id FROM announcement WHERE category_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, categoryId);

                ResultSet rs = ps.executeQuery();
                List<Announcement> announcementList = new ArrayList<>();

                while (rs.next()) {
                    String id = rs.getString("announcement_id");
                    String title = rs.getString("title");
                    String text = rs.getString("text");
                    String date = rs.getString("date");
                    categoryId = rs.getString("category_id");

                    announcementList.add(new Announcement(id, title, text, date, categoryId));
                }

                return announcementList;
            }
        }
    }

    public static List<Announcement> readAll() throws SQLException {
        String sql = "SELECT announcement_id, title, text, date, category_id FROM announcement";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                List<Announcement> announcementList = new ArrayList<>();

                while (rs.next()) {
                    String id = rs.getString("announcement_id");
                    String title = rs.getString("title");
                    String text = rs.getString("text");
                    String date = rs.getString("date");
                    String categoryId = rs.getString("category_id");

                    announcementList.add(new Announcement(id, title, text, date, categoryId));
                }

                return announcementList;
            }
        }
    }

    public static int update(String announcementId, String title, String text, String categoryId) throws SQLException {
        if (title != null && !title.isBlank() && updateTitle(announcementId, title) == 0)
            return 0;

        if (text != null && !text.isBlank() && updateText(announcementId, text) == 0)
            return 0;

        if (categoryId != null && !categoryId.isBlank() && updateCategoryId(announcementId, categoryId) == 0)
            return 0;

        return 1;
    }

    public static int delete(String announcementId) throws SQLException {
        String sql = "DELETE FROM announcement WHERE announcement_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, announcementId);
                return ps.executeUpdate();
            }
        }
    }
}
