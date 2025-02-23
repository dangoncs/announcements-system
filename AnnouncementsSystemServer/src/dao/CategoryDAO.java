package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Category;

public class CategoryDAO {

    private static int updateName(Connection conn, Category category) throws SQLException {
        String sql = "UPDATE category SET name = ? WHERE category_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.name());
            ps.setString(2, category.id());

            return ps.executeUpdate();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    private static int updateDescription(Connection conn, Category category) throws SQLException {
        String sql = "UPDATE category SET description = ? WHERE category_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.description());
            ps.setString(2, category.id());

            return ps.executeUpdate();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    public static void create(List<Category> categoriesList) throws SQLException {
        String sql = "INSERT INTO category (name, description) VALUES (?, ?)";

        try (Connection conn = Database.connect()) {
            conn.setAutoCommit(false);

            for (Category category : categoriesList) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, category.name());
                    ps.setString(2, category.description());

                    ps.executeUpdate();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            }

            conn.commit();
        }
    }

    public static Category read(String categoryId) throws SQLException {
        String sql = "SELECT * FROM category";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");

                    return new Category(categoryId, name, description, null);
                }

                return null;
            }
        }
    }

    public static List<Category> readAll(String userId) throws SQLException {
        List<String> subscribedCategories = UserCategoryDAO.read(userId);
        String sql = "SELECT * FROM category";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                List<Category> categoryList = new ArrayList<>();

                while (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String subscribed = (subscribedCategories.contains(categoryId)) ? "true" : "false";

                    categoryList.add(new Category(categoryId, name, description, subscribed));
                }

                return categoryList;
            }
        }
    }

    public static int update(List<Category> categoriesList) throws SQLException {
        try (Connection conn = Database.connect()) {
            conn.setAutoCommit(false);

            for (Category category : categoriesList) {
                if (category.name() != null && !category.name().isBlank()) {
                    if (updateName(conn, category) == 0) {
                        conn.rollback();
                        return -1;
                    }
                }

                if (category.description() != null && !category.description().isBlank()) {
                    if (updateDescription(conn, category) == 0) {
                        conn.rollback();
                        return -1;
                    }
                }
            }

            conn.commit();
            return categoriesList.size();
        }
    }

    public static int delete(List<String> categoryIdsList) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id = ?";

        try (Connection conn = Database.connect()) {
            conn.setAutoCommit(false);

            for (String categoryId : categoryIdsList) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, categoryId);

                    if (ps.executeUpdate() == 0) {
                        conn.rollback();
                        return -1;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            }

            conn.commit();
            return categoryIdsList.size();
        }
    }
}
