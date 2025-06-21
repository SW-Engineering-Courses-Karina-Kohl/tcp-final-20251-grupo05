package test.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionTest {

    private static final String URL = "jdbc:sqlite::memory:";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
