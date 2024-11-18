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
            //TODO: Create database and sql statement
            ps = conn.prepareStatement("");

            ps.executeUpdate();
        } finally {
            if(ps != null) ps.close();
        }
    }

    public Account searchByUser(String user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //TODO: Create database and sql statement
            ps = conn.prepareStatement("");
            rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account();

                account.setUser(rs.getString(""));
                account.setPassword(rs.getString(""));
                account.setName(rs.getString(""));

                return account;
            }

            return null;
        } finally {
            if(ps != null) ps.close();
            if(rs != null) rs.close();
        }
    }

    public List<Account> searchAll() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> allAccounts = new ArrayList<>();

        try {
            //TODO: Create database and sql statement
            ps = conn.prepareStatement("");
            rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account();

                account.setUser(rs.getString(""));
                account.setPassword(rs.getString(""));
                account.setName(rs.getString(""));

                allAccounts.add(account);
            }

            return allAccounts;
        } finally {
            if(ps != null) ps.close();
            if(rs != null) rs.close();
        }
    }

    public int delete(String username) throws SQLException {
        PreparedStatement ps = null;

        try {
            //TODO: Create database and sql statement
            ps = conn.prepareStatement("");
            return ps.executeUpdate();
        }
        finally {
            if(ps != null) ps.close();
        }
    }
}
