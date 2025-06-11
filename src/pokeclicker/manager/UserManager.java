package pokeclicker.manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pokeclicker.SQLiteConnection;
import pokeclicker.model.User;

public class UserManager {
    private UserManager() {
    }

    // Cria um novo usuário no banco
    public static boolean createUser(User user) {
        if (getUserByName(user.getName()) != null) {
            System.out.println("Usuário já existe!");
            return false;
        }
        SQLiteConnection.insertUser(
                user.getName(),
                user.getFavPokemon() != null ? user.getFavPokemon().getName() : null,
                user.getMoney(),
                user.getMoneyMultiplier());
        return true;
    }

    // Busca todos os usuários do banco
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(rs.getString("name"));
                // Adapte para buscar o Pokémon real se quiser
                user.setFavPokemon(null);
                user.setMoney(rs.getDouble("money"));
                user.updateMultiplier(rs.getDouble("money_multiplier") - 1.0); // moneyMultiplier default é 1.0
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuários: " + e.getMessage());
        }
        return users;
    }

    // Busca um usuário pelo nome
    public static User getUserByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("name"));
                user.setFavPokemon(null); // Adapte se quiser buscar o Pokémon real
                user.setMoney(rs.getDouble("money"));
                user.updateMultiplier(rs.getDouble("money_multiplier") - 1.0);
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    // Atualiza dados do usuário
    public static boolean updateUser(User user) {
        String sql = "UPDATE user SET fav_pokemon = ?, money = ?, money_multiplier = ? WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFavPokemon() != null ? user.getFavPokemon().getName() : null);
            pstmt.setDouble(2, user.getMoney());
            pstmt.setDouble(3, user.getMoneyMultiplier());
            pstmt.setString(4, user.getName());
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    // Remove usuário do banco
    public static boolean deleteUser(String name) {
        String sql = "DELETE FROM user WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
            return false;
        }
    }
}