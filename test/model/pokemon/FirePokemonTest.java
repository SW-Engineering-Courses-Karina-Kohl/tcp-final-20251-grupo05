package test.model.pokemon;

import org.junit.jupiter.api.Test;
import pokeclicker.model.pokemon.FirePokemon;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

public class FirePokemonTest {

    @Test
    void testConstructorAndGetters() {
        FirePokemon firePokemon = new FirePokemon(
            "Charmander",
            LevelType.BEGINNER,
            0.0,
            39,
            39,
            true,
            100.0,
            "charmander.png"
        );

        assertEquals("Charmander", firePokemon.getName());
        assertEquals(LevelType.BEGINNER, firePokemon.getLevel());
        assertEquals(0.0, firePokemon.getXp());
        assertEquals(39, firePokemon.getHealth());
        assertEquals(39, firePokemon.getTotalHealth());
        assertTrue(firePokemon.isAvailable());
        assertEquals(100.0, firePokemon.getPrice());
        assertEquals("charmander.png", firePokemon.getImagePath());
    }

    @Test
    void testGetTypeReturnsFIRE() {
        FirePokemon firePokemon = new FirePokemon(
            "Charmander",
            LevelType.BEGINNER,
            0.0,
            39,
            39,
            true,
            100.0,
            "charmander.png"
        );
        assertEquals("FIRE", firePokemon.getType());
    }
}