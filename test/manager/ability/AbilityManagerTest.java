package test.manager.ability;

import org.junit.jupiter.api.*;
import pokeclicker.manager.ability.AbilityManager;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import test.TestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityManagerTest {
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
    void testCreateAbilitySuccess() {
        Ability ability = AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0);
        assertNotNull(ability);
        assertEquals("Thunder", ability.getName());
        assertEquals("Fire", ability.getType());
    }

    @Test
    void testCreateAbilityThrowsIfExists() {
        AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void testCreateAbilityThrowsIfNameNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.createAbility(null, "desc", PokeType.FIRE, 10, 0));
        assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.createAbility("", "desc", PokeType.FIRE, 10, 0));
    }

    @Test
    void testGetAllAbilitiesReturnsList() {
        AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0);
        AbilityManager.createAbility("Heal", "Restores HP", PokeType.WATER, 0.0, 30.0);
        List<Ability> abilities = AbilityManager.getAllAbilities(Optional.empty());
        assertTrue(abilities.size() >= 2);
    }

    @Test
    void testGetAbilityByNameSuccess() {
        AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0);
        Ability ability = AbilityManager.getAbilityByName("Thunder");
        assertNotNull(ability);
        assertEquals("Thunder", ability.getName());
    }

    @Test
    void testGetAbilityByNameThrowsIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.getAbilityByName("NonExistent"));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testDeleteAbilitySuccess() {
        AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0);
        AbilityManager.deleteAbility("Thunder");
        assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.getAbilityByName("Thunder"));
    }

    @Test
    void testDeleteAbilityThrowsIfNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.deleteAbility("NonExistent"));
    }

    @Test
    void testUpdateAbilitySuccess() {
        AbilityManager.createAbility("Thunder", "FIRE attack", PokeType.FIRE, 50.0, 0.0);
        Ability updated = new Ability("Thunder", "Stronger FIRE attack", PokeType.FIRE, 70.0, 0.0);
        AbilityManager.updateAbility(updated);
        Ability result = AbilityManager.getAbilityByName("Thunder");
        assertEquals(70.0, result.getDamage());
        assertEquals("Stronger FIRE attack", result.getDescription());
    }

    @Test
    void testUpdateAbilityThrowsIfNotFound() {
        Ability ability = new Ability("NonExistent", "desc", PokeType.FIRE, 10, 0);
        assertThrows(IllegalArgumentException.class, () ->
                AbilityManager.updateAbility(ability));
    }
}