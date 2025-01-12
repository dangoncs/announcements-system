package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection conn = null;

    public static Connection connect() throws SQLException {
        if(conn == null) {
            String url = "jdbc:sqlite:announcements_system.db";
            conn = DriverManager.getConnection(url);
        }

        return conn;
    }

    public static void disconnect() {
        if(conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                System.err.printf("ERRO ao dessconectar do banco de dados: %s%n", e.getLocalizedMessage());
            }
        }
    }
}
