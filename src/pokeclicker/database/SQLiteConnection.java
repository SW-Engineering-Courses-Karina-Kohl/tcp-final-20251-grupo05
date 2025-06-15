package pokeclicker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void createPokemonTable() {
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "type TEXT," +
                "level INTEGER," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES user(id)" +
                ");";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'pokemon' criada ou já existe.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela de pokemon: " + e.getMessage());
        }
    }

    public static void insertPokemon(String name, String type, int level, int userId) {
        String sql = "INSERT INTO pokemon(name, type, level, user_id) VALUES(?,?,?,?)";
        try (Connection conn = connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, level);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
            System.out.println("Pokemon inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pokemon: " + e.getMessage());
        }
    }

}