import dao.AccountDAO;

import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] ignoredArgs) {
        dbInsertTest();
        new Thread(DatabaseTest::dbUpdateTest).start();
        new Thread(DatabaseTest::dbSelectTest).start();
        new Thread(DatabaseTest::dbDeleteTest).start();
    }

    private static void dbInsertTest() {
        try {
            System.out.println("Inserting...");
            new AccountDAO().create("testUser", "Test user", "pass");
            System.out.println("Inserted");
        } catch (SQLException e) {
            System.err.printf("Insert error: %s%n", e.getLocalizedMessage());
        }
    }

    private static void dbUpdateTest() {
        try {
            System.out.println("Updating...");
            new AccountDAO().updateName("testUser", "Test User");
            new AccountDAO().updatePassword("testUser", "Pass");
            System.out.println("Updated");
        } catch (SQLException e) {
            System.err.printf("Update error: %s%n", e.getLocalizedMessage());
        }
    }

    private static void dbSelectTest() {
        try {
            System.out.println("Selecting...");
            System.out.println(new AccountDAO().searchByUser("testUser"));
        } catch (SQLException e) {
            System.err.printf("Select error: %s%n", e.getLocalizedMessage());
        }
    }

    private static void dbDeleteTest() {
        try {
            System.out.println("Deleting...");
            new AccountDAO().delete("testUser");
            System.out.println("Deleted");
        } catch (SQLException e) {
            System.err.printf("Delete error: %s%n", e.getLocalizedMessage());
        }
    }
}
