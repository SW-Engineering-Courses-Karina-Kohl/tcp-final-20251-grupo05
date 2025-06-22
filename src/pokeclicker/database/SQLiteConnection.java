package pokeclicker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static String url = "jdbc:sqlite:game.db";

    // For testing only
    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    // For testing only
    public static String getUrl() {
        return url;
    }

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("SQLite connection established!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
            return null;
        }
    }

}
