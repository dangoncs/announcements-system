package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCategoryDAO {

    public static void create(String userId, String categoryId) throws SQLException {
        String sql = "INSERT INTO user_category (user_id, category_id) VALUES (?, ?)";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);
                ps.setString(2, categoryId);

                ps.executeUpdate();
            }
        }
    }

    public static List<String> read(String userId) throws SQLException {
        String sql = "SELECT c.category_id FROM category c JOIN user_category uc ON c.category_id = uc.category_id WHERE uc.user_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);

                ResultSet rs = ps.executeQuery();
                List<String> categories = new ArrayList<>();

                while (rs.next()) {
                    String categoryId = rs.getString("c.category_id");
                    categories.add(categoryId);
                }

                return categories;
            }
        }
    }

    public static int delete(String userId, String categoryId) throws SQLException {
        String sql = "DELETE FROM user_category WHERE user_id = ? AND category_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);
                ps.setString(2, categoryId);
                return ps.executeUpdate();
            }
        }
    }
}
