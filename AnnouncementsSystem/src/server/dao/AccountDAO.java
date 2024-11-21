package server.dao;

import server.entities.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection conn;

    public AccountDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Account account) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO user (user_id, name, password) VALUES (?, ?, ?)");
            ps.setString(1, account.getUserId());
            ps.setString(2, account.getName());
            ps.setString(3, account.getPassword());

            ps.executeUpdate();
        } finally {
            if(ps != null) ps.close();
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
                account.setPassword(rs.getString("name"));
                account.setName(rs.getString("password"));

                return account;
            }

            return null;
        } finally {
            if(ps != null) ps.close();
            if(rs != null) rs.close();
            Database.disconnect();
        }
    }

    public List<Account> searchAll() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> allAccounts = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM user ORDER BY user_id");
            rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account();

                account.setUserId(rs.getString("user_id"));
                account.setName(rs.getString("name"));
                account.setPassword(rs.getString("password"));

                allAccounts.add(account);
            }

            return allAccounts;
        } finally {
            if(ps != null) ps.close();
            if(rs != null) rs.close();
            Database.disconnect();
        }
    }

    public void update(Account account) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE user SET name = ?, password = ? WHERE user_id = ?");
            ps.setString(1, account.getName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getUserId());

            ps.executeUpdate();
        }
        finally {
            if(ps != null) ps.close();
            Database.disconnect();
        }
    }

    public int delete(String userId) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM user WHERE user_id = ?");
            ps.setString(1, userId);
            return ps.executeUpdate();
        }
        finally {
            if(ps != null) ps.close();
            Database.disconnect();
        }
    }
}
