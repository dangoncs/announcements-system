package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Account;

public class AccountDAO {

    private AccountDAO() {
    }

    public static void create(String userId, String name, String password) throws SQLException {
        String sql = "INSERT INTO user (user_id, name, password, role) VALUES (?, ?, ?, ?)";
        int defaultRole = 0;

        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setInt(4, defaultRole);

            ps.executeUpdate();
        }
    }

    public static Account read(String userId) throws SQLException {
        String sql = "SELECT password, name, role FROM user WHERE user_id = ?";

        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    int role = rs.getInt("role");

                    return new Account(userId, name, password, role);
                }

                return null;
            }
        }
    }

    public static void updateName(String userId, String name) throws SQLException {
        String sql = "UPDATE user SET name = ? WHERE user_id = ?";

        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, userId);

            ps.executeUpdate();
        }
    }

    public static void updatePassword(String userId, String password) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE user_id = ?";

        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, userId);

            ps.executeUpdate();
        }
    }

    public static int delete(String userId) throws SQLException {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = Database.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate();
        }
    }
}
