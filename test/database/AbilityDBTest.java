package test.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pokeclicker.model.Ability;
import pokeclicker.model.User;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.Pokemon;
import test.TestUtils;
import pokeclicker.database.*;
import pokeclicker.manager.ability.AbilityFilter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class AbilityDBTest {
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
    void testCreateAbilityTable() throws SQLException {
        TestUtils.insertTestAbility();

        Ability retrieved = AbilityDB.getAbility("Fireball");
        assertNotNull(retrieved, "Table should exist and allow ability insertion");
        assertEquals("Fireball", retrieved.getName());
        assertEquals("Throws a huge fireball", retrieved.getDescription());
        assertEquals("Fire", retrieved.getType());
        assertEquals(20.0, retrieved.getDamage());
        assertEquals(0.0, retrieved.getCure());
    }

    @Test
    void testGetAllAbilities() throws SQLException {
        TestUtils.insertTestAbility();

        List<Ability> abilities = AbilityDB.getAllAbilities(Optional.empty());

        assertFalse(abilities.isEmpty(), "Should retrieve at least one ability");

        Ability found = abilities.stream()
            .filter(a -> a.getName().equals("Fireball"))
            .findFirst()
            .orElse(null);

        assertNotNull(found, "Inserted ability should be present in the list");
        assertEquals("Fireball", found.getName());
        assertEquals("Throws a huge fireball", found.getDescription());
        assertEquals("Fire", found.getType());
        assertEquals(20.0, found.getDamage());
        assertEquals(0.0, found.getCure());
    }
    
    @Test
    void testUpdateAbility() throws SQLException {
        TestUtils.insertTestAbility();

        Ability updated = new Ability(
            "Fireball",
            "Throws a MASSIVE fireball",
            PokeType.FIRE,
            99.0,
            10.0
        );
        AbilityDB.updateAbility(updated);

        Ability retrieved = AbilityDB.getAbility("Fireball");
        assertNotNull(retrieved);
        assertEquals("Throws a MASSIVE fireball", retrieved.getDescription());
        assertEquals("Fire", retrieved.getType());
        assertEquals(99.0, retrieved.getDamage());
        assertEquals(10.0, retrieved.getCure());
    }

    @Test
    void testDeleteAbility() throws SQLException {
        TestUtils.insertTestAbility();

        AbilityDB.deleteAbility("Fireball");

        Ability retrieved = AbilityDB.getAbility("Fireball");
        assertNull(retrieved, "Ability should be deleted and not found in the database");
    }

    @Test
    void testBuildAbilityConditions_AllFields() throws Exception {
        TestUtils.insertTestAbility();

        AbilityFilter filter = TestUtils.getTestAbilityFilter();

        List<Ability> abilities = AbilityDB.getAllAbilities(Optional.of(filter));

        assertFalse(abilities.isEmpty(), "Should retrieve at least one ability with the filter");

        Ability found = abilities.get(0);
        assertEquals("Fireball", found.getName());
        assertEquals("Throws a huge fireball", found.getDescription());
        assertEquals("Fire", found.getType());
        assertTrue(found.getDamage() >= 10.0 && found.getDamage() <= 50.0);
        assertTrue(found.getCure() >= 0.0 && found.getCure() <= 20.0);
    }
}
