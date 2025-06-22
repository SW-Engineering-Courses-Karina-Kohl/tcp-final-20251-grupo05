package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pokeclicker.database.*;

public class TestUtils {
    public static final String TEST_DB_URL = "jdbc:sqlite:test.db";

    public static void setupTestDatabase() throws SQLException {
        SQLiteConnection.setUrl(TEST_DB_URL);
        cleanTestDatabase();
    }

    public static void cleanTestDatabase() throws SQLException {
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS user");
            UserDB.createUserTable();
        }
    }

    public static void resetDatabaseUrl() {
        SQLiteConnection.setUrl("jdbc:sqlite:game.db");
    }

    public static Connection getConnection() throws SQLException {
        return SQLiteConnection.connect();
    }
}
