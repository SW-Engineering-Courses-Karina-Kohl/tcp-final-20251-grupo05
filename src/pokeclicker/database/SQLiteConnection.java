package pokeclicker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:game.db";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("SQLite connection established!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
            return null;
        }
    }

}