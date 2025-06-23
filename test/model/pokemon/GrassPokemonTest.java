package test.model.pokemon;

import org.junit.jupiter.api.Test;
import pokeclicker.model.pokemon.GrassPokemon;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

public class GrassPokemonTest {

    @Test
    void testConstructorAndGetters() {
        GrassPokemon grassPokemon = new GrassPokemon(
            "Bulbasaur",
            LevelType.BEGINNER,
            0.0,
            45,
            45,
            true,
            120.0,
            "bulbasaur.png"
        );

        assertEquals("Bulbasaur", grassPokemon.getName());
        assertEquals(LevelType.BEGINNER, grassPokemon.getLevel());
        assertEquals(0.0, grassPokemon.getXp());
        assertEquals(45, grassPokemon.getHealth());
        assertEquals(45, grassPokemon.getTotalHealth());
        assertTrue(grassPokemon.isAvailable());
        assertEquals(120.0, grassPokemon.getPrice());
        assertEquals("bulbasaur.png", grassPokemon.getImagePath());
    }

    @Test
    void testGetTypeReturnsGRASS() {
        GrassPokemon grassPokemon = new GrassPokemon(
            "Bulbasaur",
            LevelType.BEGINNER,
            0.0,
            45,
            45,
            true,
            120.0,
            "bulbasaur.png"
        );
        assertEquals("GRASS", grassPokemon.getType());
    }
}