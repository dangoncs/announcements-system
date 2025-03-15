package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Database() {
    }

    public static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:announcements_system.db";
        return DriverManager.getConnection(url);
    }
}
