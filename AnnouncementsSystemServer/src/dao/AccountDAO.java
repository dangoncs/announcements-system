package dao;

import entities.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    private AccountDAO() {}

    public static void create(String userId, String name, String password) throws SQLException {
        String sql = "INSERT INTO user (user_id, name, password, role) VALUES (?, ?, ?, ?)";
        int defaultRole = 0;

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);
                ps.setString(2, name);
                ps.setString(3, password);
                ps.setInt(4, defaultRole);

                ps.executeUpdate();
            }
        }
    }

    public static Account read(String userId) throws SQLException {
        String sql = "SELECT user_id, password, name, role FROM user WHERE user_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    userId = rs.getString("user_id");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    int role = rs.getInt("role");

                    return new Account(userId, password, name, role);
                }

                return null;
            }
        }
    }

    public static void updateName(String userId, String name) throws SQLException {
        String sql = "UPDATE user SET name = ? WHERE user_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, userId);

                ps.executeUpdate();
            }
        }
    }

    public static void updatePassword(String userId, String password) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE user_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, password);
                ps.setString(2, userId);

                ps.executeUpdate();
            }
        }
    }

    public static int delete(String userId) throws SQLException {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = Database.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);
                return ps.executeUpdate();
            }
        }
    }
}
