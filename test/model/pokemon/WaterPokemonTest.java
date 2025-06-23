package test.model.pokemon;

import org.junit.jupiter.api.Test;
import pokeclicker.model.pokemon.WaterPokemon;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

public class WaterPokemonTest {

    @Test
    void testConstructorAndGetters() {
        WaterPokemon waterPokemon = new WaterPokemon(
            "Squirtle",
            LevelType.BEGINNER,
            0.0,
            44,
            44,
            true,
            110.0,
            "squirtle.png"
        );

        assertEquals("Squirtle", waterPokemon.getName());
        assertEquals(LevelType.BEGINNER, waterPokemon.getLevel());
        assertEquals(0.0, waterPokemon.getXp());
        assertEquals(44, waterPokemon.getHealth());
        assertEquals(44, waterPokemon.getTotalHealth());
        assertTrue(waterPokemon.isAvailable());
        assertEquals(110.0, waterPokemon.getPrice());
        assertEquals("squirtle.png", waterPokemon.getImagePath());
    }

    @Test
    void testGetTypeReturnsWATER() {
        WaterPokemon waterPokemon = new WaterPokemon(
            "Squirtle",
            LevelType.BEGINNER,
            0.0,
            44,
            44,
            true,
            110.0,
            "squirtle.png"
        );
        assertEquals("WATER", waterPokemon.getType());
    }
}