package pokeclicker.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pokeclicker.model.Ability;

public class PokemonAbilityDB {
    public static void createPokemonAbilityTable() {
        String sql = "CREATE TABLE IF NOT EXISTS pokemon_ability (" +
                "pokemon_name TEXT," +
                "ability_name TEXT," +
                "PRIMARY KEY (pokemon_name, ability_name)," +
                "FOREIGN KEY (pokemon_name) REFERENCES pokemon(name)," +
                "FOREIGN KEY (ability_name) REFERENCES ability(name)" +
                ");";
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Pokemon-Ability relationship table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating Pokemon-Ability relationship table: " + e.getMessage());
        }
    }

    public static void addAbilityToPokemon(String pokemonName, String abilityName) {
        String sql = "INSERT INTO pokemon_ability(pokemon_name, ability_name) VALUES(?,?)";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pokemonName);
            pstmt.setString(2, abilityName);
            pstmt.executeUpdate();
            System.out.println("Ability added to Pokemon successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding ability to pokemon: " + e.getMessage());
        }
    }

    public static List<Ability> getPokemonAbilities(String pokemonName) {
        List<Ability> abilities = new ArrayList<>();
        String sql = "SELECT a.* FROM ability a " +
                "JOIN pokemon_ability pa ON a.name = pa.ability_name " +
                "WHERE pa.pokemon_name = ?";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pokemonName);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                abilities.add(new Ability(
                        rs.getString("name"),
                        rs.getString("description"),
                        pokeclicker.model.common.PokeType.fromString(rs.getString("type")),
                        rs.getDouble("damage"),
                        rs.getDouble("cure")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching pokemon abilities: " + e.getMessage());
        }
        return abilities;
    }

    public static void removeAbilityFromPokemon(String pokemonName, String abilityName) {
        String sql = "DELETE FROM pokemon_ability WHERE pokemon_name = ? AND ability_name = ?";
        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pokemonName);
            pstmt.setString(2, abilityName);
            pstmt.executeUpdate();
            System.out.println("Ability removed from Pokemon successfully!");
        } catch (SQLException e) {
            System.out.println("Error removing ability from pokemon: " + e.getMessage());
        }
    }
}
