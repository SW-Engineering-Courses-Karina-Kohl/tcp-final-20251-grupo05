package pokeclicker.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pokeclicker.manager.pokemon.PokemonFilter;
import pokeclicker.model.pokemon.FirePokemon;
import pokeclicker.model.pokemon.GrassPokemon;
import pokeclicker.model.pokemon.LevelType;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.WaterPokemon;

public class PokemonDB {
    public static void createPokemonTable() {
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (" +
                "name TEXT PRIMARY KEY," +
                "type TEXT," +
                "level INTEGER," +
                "xp REAL," +
                "health INTEGER," +
                "total_health INTEGER," +
                "captured BOOLEAN," +
                "price REAL" +
                ");";
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Pokemon table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating Pokemon table: " + e.getMessage());
        }
    }

    public static void insertPokemon(Pokemon pokemon) {
        String sql = "INSERT INTO pokemon(name, type, level, health, total_health, captured, price, xp) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pokemon.getName());
            pstmt.setString(2, pokemon.getType());
            pstmt.setInt(3, pokemon.getLevel().toInt());
            pstmt.setInt(4, pokemon.getHealth());
            pstmt.setInt(5, pokemon.getTotalHealth());
            pstmt.setBoolean(6, pokemon.isCaptured());
            pstmt.setDouble(7, pokemon.getPrice());
            pstmt.setDouble(8, pokemon.getXp());
            pstmt.executeUpdate();
            System.out.println("Pokemon inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting pokemon: " + e.getMessage());
        }
    }

    public static void updatePokemon(Pokemon pokemon) {
        String sql = "UPDATE pokemon SET level = ?, xp = ?, health = ?, captured = ? WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pokemon.getLevel().toInt());
            pstmt.setDouble(2, pokemon.getXp());
            pstmt.setInt(3, pokemon.getHealth());
            pstmt.setBoolean(4, pokemon.isCaptured());
            pstmt.setString(5, pokemon.getName());
            pstmt.executeUpdate();
            System.out.println("Pokemon updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating pokemon: " + e.getMessage());
        }
    }

    public static void deletePokemon(String name) {
        String sql = "DELETE FROM pokemon WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Pokemon deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting pokemon: " + e.getMessage());
        }
    }

    public static Pokemon getPokemon(String name) {
        String sql = "SELECT * FROM pokemon WHERE name = ?";
        Pokemon pokemon;
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                LevelType level = LevelType.fromInt(rs.getInt("level"));
                double xp = rs.getDouble("xp");
                int health = rs.getInt("health");
                int totalHealth = rs.getInt("total_health");
                boolean captured = rs.getBoolean("captured");
                double price = rs.getDouble("price");

                switch (type) {
                    case "FIRE" -> pokemon = new FirePokemon(name, level, xp, health, totalHealth, captured, price);
                    case "WATER" -> pokemon = new WaterPokemon(name, level, xp, health, totalHealth, captured, price);
                    case "GRASS" -> pokemon = new GrassPokemon(name, level, xp, health, totalHealth, captured, price);
                    default -> {
                        System.err.println("Unknown Pokemon type: " + type);
                        return null;
                    }
                }
                return pokemon;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching pokemon: " + e.getMessage());
        }
        return null;
    }

    public static List<Pokemon> getAllPokemons(Optional<PokemonFilter> filter) {
        List<Pokemon> pokemons = new ArrayList<>();

        String sql = "SELECT * FROM pokemon";
        if (filter.isPresent()) {
            String conditions = getConditions(filter.get());
            if (!conditions.equals("1")) {
                sql += " WHERE " + conditions;
            }
        }
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                LevelType level = LevelType.fromInt(rs.getInt("level"));
                double xp = rs.getDouble("xp");
                int health = rs.getInt("health");
                int totalHealth = rs.getInt("total_health");
                boolean captured = rs.getBoolean("captured");
                double price = rs.getDouble("price");

                Pokemon pokemon = switch (type) {
                    case "FIRE" -> new FirePokemon(name, level, xp, health, totalHealth, captured, price);
                    case "WATER" -> new WaterPokemon(name, level, xp, health, totalHealth, captured, price);
                    case "GRASS" -> new GrassPokemon(name, level, xp, health, totalHealth, captured, price);
                    default -> {
                        System.err.println("Unknown Pokemon type: " + type);
                        yield null;
                    }
                };

                pokemons.add(pokemon);

            }
        } catch (SQLException e) {
            System.out.println("Error fetching all pokemons: " + e.getMessage());
        }

        return pokemons;
    }

    private static String getConditions(PokemonFilter filter) {
        StringBuilder conditions = new StringBuilder("1");
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            conditions.append(" AND name LIKE '%").append(filter.getName()).append("%'");
        }
        if (filter.getType() != null) {
            conditions.append(" AND type = '").append(filter.getType().toString()).append("'");
        }
        if (filter.getMinLevel() != null) {
            conditions.append(" AND level >= ").append(filter.getMinLevel().toInt());
        }
        if (filter.getMinHealth() != null) {
            conditions.append(" AND health >= ").append(filter.getMinHealth());
        }
        if (filter.getCaptured() != null) {
            conditions.append(" AND captured = ").append(filter.getCaptured());
        }
        if (filter.getMaxPrice() != null) {
            conditions.append(" AND price <= ").append(filter.getMaxPrice());
        }
        return conditions.toString();
    }

}
