package pokeclicker.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pokeclicker.manager.pokemon.PokemonFilter;
import pokeclicker.model.Ability;
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
                "available BOOLEAN," +
                "price REAL," +
                "image_path TEXT," +
                "user TEXT," +
                "FOREIGN KEY (user) REFERENCES user(name)" +
                ");";
        try (Connection conn = SQLiteConnection.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Pokemon table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating Pokemon table: " + e.getMessage());
        }
    }

    public static void insertPokemon(Pokemon pokemon, String userName) {
        String sql = "INSERT INTO pokemon(name, type, level, health, total_health, available, price, xp, image_path, user)"
                +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pokemon.getName());
            pstmt.setString(2, pokemon.getType());
            pstmt.setInt(3, pokemon.getLevel().toInt());
            pstmt.setInt(4, pokemon.getHealth());
            pstmt.setInt(5, pokemon.getTotalHealth());
            pstmt.setBoolean(6, pokemon.isAvailable());
            pstmt.setDouble(7, pokemon.getPrice());
            pstmt.setDouble(8, pokemon.getXp());
            pstmt.setString(9, pokemon.getImagePath());
            pstmt.setString(10, userName);
            pstmt.executeUpdate();
            System.out.println("Pokemon inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting pokemon: " + e.getMessage());
        }
    }

    public static void updatePokemon(Pokemon pokemon) {
        String sql = "UPDATE pokemon SET level = ?, xp = ?, health = ?, available = ?, image_path = ? WHERE name = ?";
        try (Connection conn = SQLiteConnection.connect();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pokemon.getLevel().toInt());
            pstmt.setDouble(2, pokemon.getXp());
            pstmt.setInt(3, pokemon.getHealth());
            pstmt.setBoolean(4, pokemon.isAvailable());
            pstmt.setString(5, pokemon.getImagePath());
            pstmt.setString(6, pokemon.getName());
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
        Pokemon pokemon = null;
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
                boolean available = rs.getBoolean("available");
                double price = rs.getDouble("price");
                String imagePath = rs.getString("image_path");

                switch (type) {
                    case "FIRE" ->
                        pokemon = new FirePokemon(name, level, xp, health, totalHealth, available, price, imagePath);
                    case "WATER" ->
                        pokemon = new WaterPokemon(name, level, xp, health, totalHealth, available, price, imagePath);
                    case "GRASS" ->
                        pokemon = new GrassPokemon(name, level, xp, health, totalHealth, available, price, imagePath);
                    default -> {
                        System.err.println("Unknown Pokemon type: " + type);
                        return null;
                    }
                }
                List<Ability> abilities = PokemonAbilityDB.getPokemonAbilities(name);
                if (pokemon != null) {
                    pokemon.setAbilities(abilities);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching pokemon: " + e.getMessage());
        }
        return pokemon;
    }

    // Em PokemonDB.java

public static List<Pokemon> getAllPokemons(Optional<PokemonFilter> filterOpt) {
    List<Pokemon> pokemons = new ArrayList<>();
    // Query SQL base com placeholders '?'
    StringBuilder sql = new StringBuilder("SELECT * FROM pokemon");
    List<Object> params = new ArrayList<>(); // Lista para guardar os parâmetros

    // Constrói a cláusula WHERE e a lista de parâmetros se o filtro existir
    if (filterOpt.isPresent()) {
        PokemonFilter filter = filterOpt.get();
        List<String> conditions = new ArrayList<>();

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            conditions.add("name LIKE ?");
            params.add("%" + filter.getName() + "%");
        }
        if (filter.getType() != null) {
            conditions.add("type = ?");
            params.add(filter.getType().toString());
        }
        if (filter.getMinLevel() != null) {
            conditions.add("level >= ?");
            params.add(filter.getMinLevel().toInt());
        }
        if (filter.getMinHealth() != null) {
            conditions.add("health >= ?");
            params.add(filter.getMinHealth());
        }
        if (filter.getAvailable() != null) {
            conditions.add("available = ?");
            // Converte o boolean para inteiro (1 = true, 0 = false) para o SQLite
            params.add(filter.getAvailable() ? 1 : 0);
        }
        if (filter.getMaxPrice() != null) {
            conditions.add("price <= ?");
            params.add(filter.getMaxPrice());
        }
        if (filter.getUser() != null) {
            conditions.add("user = ?");
            params.add(filter.getUser());
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
    }

    try (Connection conn = SQLiteConnection.connect();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

        // Aplica os parâmetros na PreparedStatement
        for (int i = 0; i < params.size(); i++) {
            pstmt.setObject(i + 1, params.get(i));
        }

        var rs = pstmt.executeQuery();
        while (rs.next()) {
            // A sua lógica para criar os objetos Pokemon a partir do resultado está ótima
            String name = rs.getString("name");
            String type = rs.getString("type");
            LevelType level = LevelType.fromInt(rs.getInt("level"));
            double xp = rs.getDouble("xp");
            int health = rs.getInt("health");
            int totalHealth = rs.getInt("total_health");
            boolean available = rs.getBoolean("available");
            double price = rs.getDouble("price");
            String imagePath = rs.getString("image_path");

            Pokemon pokemon = switch (type) {
                case "FIRE" -> new FirePokemon(name, level, xp, health, totalHealth, available, price, imagePath);
                case "WATER" -> new WaterPokemon(name, level, xp, health, totalHealth, available, price, imagePath);
                case "GRASS" -> new GrassPokemon(name, level, xp, health, totalHealth, available, price, imagePath);
                default -> {
                    System.err.println("Unknown Pokemon type: " + type);
                    yield null;
                }
            };

            if (pokemon != null) {
                List<Ability> abilities = PokemonAbilityDB.getPokemonAbilities(name);
                pokemon.setAbilities(abilities);
                pokemons.add(pokemon);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error fetching all pokemons: " + e.getMessage());
    }

    return pokemons;
}

// Você pode apagar o método "private static String getConditions(...)" agora.

    private static String getConditions(PokemonFilter filter) {
        StringBuilder conditions = new StringBuilder("1");
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            conditions.append(" AND name LIKE '%").append(filter.getName()).append("%'");
        }
        if (filter.getType() != null) {
            conditions.append(" AND type = '").append(filter.getType().name()).append("'");
        }
        if (filter.getMinLevel() != null) {
            conditions.append(" AND level >= ").append(filter.getMinLevel().toInt());
        }
        if (filter.getMinHealth() != null) {
            conditions.append(" AND health >= ").append(filter.getMinHealth());
        }
        if (filter.getAvailable() != null) {
            conditions.append(" AND available = ").append(filter.getAvailable());
        }
        if (filter.getMaxPrice() != null) {
            conditions.append(" AND price <= ").append(filter.getMaxPrice());
        }
        if (filter.getUser() != null) {
            conditions.append(" AND user = '").append(filter.getUser()).append("'");
        }
        return conditions.toString();
    }

}
