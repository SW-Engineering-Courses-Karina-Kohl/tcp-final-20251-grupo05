package pokeclicker;

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

    public static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "name TEXT PRIMARY KEY," +
                "fav_pokemon TEXT," +
                "money REAL," +
                "money_multiplier REAL" +
                ");";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'user' criada ou já existe.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
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

    public static void insertUser(String name, String favPokemon, double money, double moneyMultiplier) {
        String sql = "INSERT INTO user(name, fav_pokemon, money, money_multiplier) VALUES(?,?,?,?)";
        try (Connection conn = connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, favPokemon);
            pstmt.setDouble(3, money);
            pstmt.setDouble(4, moneyMultiplier);
            pstmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
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