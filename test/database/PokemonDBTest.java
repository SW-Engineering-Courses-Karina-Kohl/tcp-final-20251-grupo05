package test.database;

import org.junit.jupiter.api.*;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.FirePokemon;
import pokeclicker.model.pokemon.LevelType;
import pokeclicker.database.PokemonDB;
import pokeclicker.manager.pokemon.PokemonFilter;
import test.TestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import pokeclicker.model.common.PokeType;

public class PokemonDBTest {
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        TestUtils.setupTestDatabase();
        connection = TestUtils.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        TestUtils.cleanTestDatabase();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        TestUtils.resetDatabaseUrl();
    }
    
     @Test
    void testInsertAndGetPokemon() throws SQLException {
        TestUtils.insertTestPokemon();
        Pokemon retrieved = PokemonDB.getPokemon("Charmander");
        assertNotNull(retrieved, "Pokemon should be found in the database");
        assertEquals("Charmander", retrieved.getName());
        assertEquals(LevelType.BEGINNER, retrieved.getLevel());
        assertEquals(40, retrieved.getHealth());
        assertEquals(40, retrieved.getTotalHealth());
        assertTrue(retrieved.isAvailable());
        assertEquals(200.0, retrieved.getPrice());
        assertEquals("src/img/charmander.png", retrieved.getImagePath());
    }

    @Test
    void testUpdatePokemon() throws SQLException {
        TestUtils.insertTestPokemon();
        Pokemon pokemon = PokemonDB.getPokemon("Charmander");
        assertNotNull(pokemon);

        pokemon.setAvailable(false);
        pokemon.gainHealth(20);
        pokemon.loseHealth(0);
        pokemon.gainXp(100.0);

        PokemonDB.updatePokemon(pokemon);

        Pokemon updated = PokemonDB.getPokemon("Charmander");
        assertNotNull(updated);
        assertFalse(updated.isAvailable());
        assertEquals(40, updated.getHealth());
        assertEquals(100.0, updated.getXp());
        assertEquals(LevelType.INTERMEDIATE, updated.getLevel());
    }

    @Test
    void testDeletePokemon() throws SQLException {
        TestUtils.insertTestPokemon();
        PokemonDB.deletePokemon("Charmander");
        Pokemon deleted = PokemonDB.getPokemon("Charmander");
        assertNull(deleted, "Pokemon should be deleted and not found in the database");
    }

    @Test
    void testGetAllPokemons() throws SQLException {
        TestUtils.insertTestPokemon();
        List<Pokemon> pokemons = PokemonDB.getAllPokemons(Optional.empty());
        assertFalse(pokemons.isEmpty(), "Should retrieve at least one pokemon");
        boolean found = pokemons.stream().anyMatch(p -> p.getName().equals("Charmander"));
        assertTrue(found, "Inserted pokemon should be present in the list");
    }
}

