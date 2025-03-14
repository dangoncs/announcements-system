package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Category;

public class CategoryDAO {

    private CategoryDAO() {}

    public static void create(String name, String description) throws SQLException {
        String sql = "INSERT INTO category (name, description) VALUES (?, ?)";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, description);

                ps.executeUpdate();
            }
        }
    }

    public static Category read(String categoryId) throws SQLException {
        String sql = "SELECT name, description FROM category WHERE category_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");

                    return new Category(categoryId, name, description);
                }

                return null;
            }
        }
    }

    public static List<Category> readAll(String userId) throws SQLException {
        List<String> subscribedCategories = UserCategoryDAO.read(userId);
        String sql = "SELECT category_id, name, description FROM category";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                List<Category> categoryList = new ArrayList<>();

                while (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    boolean subscribed = subscribedCategories.contains(categoryId);

                    categoryList.add(new Category(categoryId, name, description, subscribed));
                }

                return categoryList;
            }
        }
    }

    public static int updateName(String categoryId, String name) throws SQLException {
        String sql = "UPDATE category SET name = ? WHERE category_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, categoryId);

                return ps.executeUpdate();
            }
        }
    }

    public static int updateDescription(String categoryId, String description) throws SQLException {
        String sql = "UPDATE category SET description = ? WHERE category_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, description);
                ps.setString(2, categoryId);

                return ps.executeUpdate();
            }
        }
    }

    public static int delete(String categoryId) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, categoryId);

                return ps.executeUpdate();
            }
        }
    }
}
