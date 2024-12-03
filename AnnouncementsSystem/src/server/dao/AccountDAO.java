package server.dao;

import server.entities.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    private final Connection conn;

    public AccountDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(String userId, String name, String password) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO user (user_id, name, password) VALUES (?, ?, ?)")) {
            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, password);

            ps.executeUpdate();
        } finally {
            Database.disconnect();
        }
    }

    public Account searchByUser(String userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            ps.setString(1, userId);

            rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account();

                account.setUserId(rs.getString("user_id"));
                account.setPassword(rs.getString("password"));
                account.setName(rs.getString("name"));

                return account;
            }

            return null;
        } finally {
            if(ps != null) ps.close();
            if(rs != null) rs.close();
            Database.disconnect();
        }
    }

    public void updateName(String userId, String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE user SET name = ? WHERE user_id = ?")) {
            ps.setString(1, name);
            ps.setString(2, userId);

            ps.executeUpdate();
        } finally {
            Database.disconnect();
        }
    }

    public void updatePassword(String userId, String password) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE user SET password = ? WHERE user_id = ?")) {
            ps.setString(1, password);
            ps.setString(2, userId);

            ps.executeUpdate();
        } finally {
            Database.disconnect();
        }
    }

    public int delete(String userId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE user_id = ?")) {
            ps.setString(1, userId);
            return ps.executeUpdate();
        } finally {
            Database.disconnect();
        }
    }

}
