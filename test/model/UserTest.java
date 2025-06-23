package test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

class DummyPokemon extends Pokemon {
    public DummyPokemon(String name) {
        super(name, LevelType.BEGINNER, 0, 1, 1, false, 1, "Dummy");
    }
    @Override
    public String getType() { return "Dummy"; }
}

public class UserTest {

    private User user;
    private DummyPokemon dummyPokemon;

    @BeforeEach
    void setUp() {
        dummyPokemon = new DummyPokemon("Pikachu");
        user = new User("Ash", 2.0, 100.0, dummyPokemon);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Ash", user.getName());
        assertEquals(2.0, user.getMoneyMultiplier());
        assertEquals(100.0, user.getMoney());
        assertEquals(dummyPokemon, user.getFavoritePokemon());
    }

    @Test
    void testSetName() {
        user.setName("Misty");
        assertEquals("Misty", user.getName());
    }

    @Test
    void testSetMoneyValid() {
        user.setMoney(50.0);
        assertEquals(50.0, user.getMoney());
    }

    @Test
    void testSetMoneyThrowsIfNegative() {
        assertThrows(IllegalArgumentException.class, () -> user.setMoney(-1.0));
    }

    @Test
    void testSetFavoritePokemonValid() {
        DummyPokemon bulbasaur = new DummyPokemon("Bulbasaur");
        user.setFavoritePokemon(bulbasaur);
        assertEquals(bulbasaur, user.getFavoritePokemon());
    }

    @Test
    void testSetFavoritePokemonThrowsIfNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setFavoritePokemon(null));
    }

    @Test
    void testUpdateMultiplierValid() {
        user.updateMultiplier(3.0);
        assertEquals(6.0, user.getMoneyMultiplier());
    }

    @Test
    void testUpdateMultiplierThrowsIfZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> user.updateMultiplier(0));
        assertThrows(IllegalArgumentException.class, () -> user.updateMultiplier(-2.0));
    }

    @Test
    void testSpendMoneyValid() {
        user.spendMoney(30.0);
        assertEquals(70.0, user.getMoney());
    }

    @Test
    void testSpendMoneyThrowsIfNotEnough() {
        assertThrows(IllegalArgumentException.class, () -> user.spendMoney(200.0));
    }

    @Test
    void testSpendMoneyThrowsIfNegative() {
        assertThrows(IllegalArgumentException.class, () -> user.spendMoney(-10.0));
    }

    @Test
    void testEarnMoneyValid() {
        user.earnMoney(50.0);
        assertEquals(150.0, user.getMoney());
    }

    @Test
    void testEarnMoneyThrowsIfNegative() {
        assertThrows(IllegalArgumentException.class, () -> user.earnMoney(-5.0));
    }
}