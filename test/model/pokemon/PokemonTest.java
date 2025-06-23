package test.model.pokemon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.LevelType;
import pokeclicker.model.Ability;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DummyPokemon extends Pokemon {
    public DummyPokemon(String name, LevelType level, double xp, int health, int totalHealth,
                        boolean available, double price, String imagePath) {
        super(name, level, xp, health, totalHealth, available, price, imagePath);
    }
    @Override
    public String getType() {
        return "DUMMY";
    }
}

public class PokemonTest {

    private DummyPokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new DummyPokemon("Testmon", LevelType.BEGINNER, 0.0, 50, 50, true, 100.0, "test.png");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Testmon", pokemon.getName());
        assertEquals(LevelType.BEGINNER, pokemon.getLevel());
        assertEquals(0.0, pokemon.getXp());
        assertEquals(50, pokemon.getHealth());
        assertEquals(50, pokemon.getTotalHealth());
        assertTrue(pokemon.isAvailable());
        assertEquals(100.0, pokemon.getPrice());
        assertEquals("test.png", pokemon.getImagePath());
    }

    @Test
    void testSetName() {
        pokemon.setName("NewName");
        assertEquals("NewName", pokemon.getName());
    }

    @Test
    void testSetAbilities() {
        List<Ability> abilities = new ArrayList<>();
        Ability ability = new Ability("Test", "desc", pokeclicker.model.common.PokeType.FIRE, 10, 0);
        abilities.add(ability);
        pokemon.setAbilities(abilities);
        assertEquals(abilities, pokemon.getAbilities());
    }

    @Test
    void testGainXpAndLevelUp() {
        assertFalse(pokemon.gainXp(50)); // Should not level up
        assertEquals(50.0, pokemon.getXp());
        assertEquals(LevelType.BEGINNER, pokemon.getLevel());

        assertTrue(pokemon.gainXp(100)); // Should level up to INTERMEDIATE
        assertEquals(LevelType.INTERMEDIATE, pokemon.getLevel());

        assertTrue(pokemon.gainXp(200)); // Should level up to ADVANCED
        assertEquals(LevelType.ADVANCED, pokemon.getLevel());
    }

    @Test
    void testGainXpThrowsIfNegative() {
        assertThrows(IllegalArgumentException.class, () -> pokemon.gainXp(-10));
    }

    @Test
    void testGainHealth() {
        pokemon.loseHealth(30);
        assertEquals(20, pokemon.getHealth());
        pokemon.gainHealth(10);
        assertEquals(30, pokemon.getHealth());
        pokemon.gainHealth(50); // Should not exceed totalHealth
        assertEquals(50, pokemon.getHealth());
    }

    @Test
    void testGainHealthThrowsIfNegative() {
        assertThrows(IllegalArgumentException.class, () -> pokemon.gainHealth(-5));
    }

    @Test
    void testLoseHealth() {
        pokemon.loseHealth(10);
        assertEquals(40, pokemon.getHealth());
        pokemon.loseHealth(50); // Should not go below 0
        assertEquals(0, pokemon.getHealth());
    }

    @Test
    void testLoseHealthThrowsIfNegative() {
        assertThrows(IllegalArgumentException.class, () -> pokemon.loseHealth(-5));
    }

    @Test
    void testSetAvailable() {
        pokemon.setAvailable(false);
        assertFalse(pokemon.isAvailable());
        pokemon.setAvailable(true);
        assertTrue(pokemon.isAvailable());
    }

    @Test
    void testIsAlive() {
        assertTrue(pokemon.isAlive());
        pokemon.loseHealth(50);
        assertFalse(pokemon.isAlive());
    }

    @Test
    void testConstructorThrowsIfNameNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new DummyPokemon(null, LevelType.BEGINNER, 0.0, 50, 50, true, 100.0, "test.png"));
        assertThrows(IllegalArgumentException.class, () ->
            new DummyPokemon("", LevelType.BEGINNER, 0.0, 50, 50, true, 100.0, "test.png"));
    }

    @Test
    void testConstructorThrowsIfTotalHealthNonPositive() {
        assertThrows(IllegalArgumentException.class, () ->
            new DummyPokemon("Test", LevelType.BEGINNER, 0.0, 0, 0, true, 100.0, "test.png"));
        assertThrows(IllegalArgumentException.class, () ->
            new DummyPokemon("Test", LevelType.BEGINNER, 0.0, -1, -1, true, 100.0, "test.png"));
    }

    @Test
    void testConstructorThrowsIfPriceNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            new DummyPokemon("Test", LevelType.BEGINNER, 0.0, 50, 50, true, -10.0, "test.png"));
    }
}