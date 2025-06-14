package pokeclicker.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pokeclicker.manager.ability.AbilityFilter;
import pokeclicker.model.Ability;

public class AbilityDB {
    public static void createAbilityTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ability (" +
                "name TEXT PRIMARY KEY," +
                "description TEXT," +
                "type TEXT," +
                "damage REAL," +
                "cure REAL" +
                ");";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Ability table created or already exists.");
        } catch (java.sql.SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertAbility(Ability ability) {
        String sql = "INSERT INTO ability(name, description, type, damage, cure) VALUES(?,?,?,?,?)";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ability.getName());
            pstmt.setString(2, ability.getDescription());
            pstmt.setString(3, ability.getType().toString());
            pstmt.setDouble(4, ability.getDamage());
            pstmt.setDouble(5, ability.getCure());
            pstmt.executeUpdate();
            System.out.println("Ability inserted successfully!");
        } catch (java.sql.SQLException e) {
            System.out.println("Error inserting ability: " + e.getMessage());
        }
    }

    public static Ability getAbility(String name) {
        String sql = "SELECT * FROM ability WHERE name = ?";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ability(
                        rs.getString("name"),
                        rs.getString("description"),
                        pokeclicker.model.common.PokeType.fromString(rs.getString("type")),
                        rs.getDouble("damage"),
                        rs.getDouble("cure"));
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error fetching ability: " + e.getMessage());
        }
        return null;
    }

    public static List<Ability> getAllAbilities(Optional<AbilityFilter> filter) {
        String sql = "SELECT * FROM ability";
        if (filter != null && filter.isPresent()) {
            String conditions = buildAbilityFilter(filter.get());
            if (!conditions.equals("1")) {
                sql += " WHERE " + conditions;
            }
        }
        List<Ability> abilities = new ArrayList<>();
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                abilities.add(new Ability(
                        rs.getString("name"),
                        rs.getString("description"),
                        pokeclicker.model.common.PokeType.fromString(rs.getString("type")),
                        rs.getDouble("damage"),
                        rs.getDouble("cure")));
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error fetching all abilities: " + e.getMessage());
        }
        return abilities;
    }

    public static void updateAbility(Ability ability) {
        String sql = "UPDATE ability SET description = ?, type = ?, damage = ?, cure = ? WHERE name = ?";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ability.getDescription());
            pstmt.setString(2, ability.getType().toString());
            pstmt.setDouble(3, ability.getDamage());
            pstmt.setDouble(4, ability.getCure());
            pstmt.setString(5, ability.getName());
            pstmt.executeUpdate();
            System.out.println("Ability updated successfully!");
        } catch (java.sql.SQLException e) {
            System.out.println("Error updating ability: " + e.getMessage());
        }
    }

    public static void deleteAbility(String name) {
        String sql = "DELETE FROM ability WHERE name = ?";
        try (java.sql.Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Ability deleted successfully!");
        } catch (java.sql.SQLException e) {
            System.out.println("Error deleting ability: " + e.getMessage());
        }
    }

    private static String buildAbilityFilter(AbilityFilter filter) {
        List<String> conditions = new ArrayList<>();
        if (filter.getType() != null) {
            conditions.add("type = '" + filter.getType().toString() + "'");
        }
        if (filter.getMinDamage() != null) {
            conditions.add("damage >= " + filter.getMinDamage());
        }
        if (filter.getMaxDamage() != null) {
            conditions.add("damage <= " + filter.getMaxDamage());
        }
        if (filter.getMinCure() != null) {
            conditions.add("cure >= " + filter.getMinCure());
        }
        if (filter.getMaxCure() != null) {
            conditions.add("cure <= " + filter.getMaxCure());
        }

        if (conditions.isEmpty()) {
            return "1"; // No filter, return all
        }
        return String.join(" AND ", conditions);
    }
}
