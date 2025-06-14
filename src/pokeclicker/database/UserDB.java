package pokeclicker.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pokeclicker.model.User;

public class UserDB {
    public static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "name TEXT PRIMARY KEY," +
                "money REAL," +
                "money_multiplier REAL" +
                ");";
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertUser(User user) {
        String name = user.getName();
        double money = user.getMoney();
        double moneyMultiplier = user.getMoneyMultiplier();

        String sql = "INSERT INTO user(name, money, money_multiplier) VALUES(?,?,?)";
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, money);
            pstmt.setDouble(3, moneyMultiplier);
            pstmt.executeUpdate();
            System.out.println("User inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    public static User getUser(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("name"));
                user.setMoney(rs.getDouble("money"));
                user.updateMultiplier(rs.getDouble("money_multiplier") - 1.0);
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public static User updateUser(User user) {
        String sql = "UPDATE user SET money = ?, money_multiplier = ? WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, user.getMoney());
            pstmt.setDouble(2, user.getMoneyMultiplier());
            pstmt.setString(3, user.getName());
            pstmt.executeUpdate();
            System.out.println("User updated successfully!");
            return user;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
        return null;
    }

    public static boolean deleteUser(String name) {
        String sql = "DELETE FROM user WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

}
