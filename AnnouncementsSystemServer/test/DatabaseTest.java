import dao.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] ignoredArgs) {
        try(Connection _ = Database.connect()) {
            System.out.println("Conexão ao SQLite estabelecida com sucesso.");
        } catch (SQLException e) {
            System.err.printf("[ERROR] %s%n", e.getLocalizedMessage());
        } finally {
            Database.disconnect();
        }
    }


}
