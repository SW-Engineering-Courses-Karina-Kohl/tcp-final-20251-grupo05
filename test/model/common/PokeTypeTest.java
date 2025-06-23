package test.model.common;

import org.junit.jupiter.api.Test;
import pokeclicker.model.common.PokeType;

import static org.junit.jupiter.api.Assertions.*;

public class PokeTypeTest {

    @Test
    void testToStringReturnsCorrectType() {
        assertEquals("Grass", PokeType.GRASS.toString());
        assertEquals("Water", PokeType.WATER.toString());
        assertEquals("Fire", PokeType.FIRE.toString());
    }

    @Test
    void testFromStringValidInputs() {
        assertEquals(PokeType.GRASS, PokeType.fromString("Grass"));
        assertEquals(PokeType.WATER, PokeType.fromString("Water"));
        assertEquals(PokeType.FIRE, PokeType.fromString("Fire"));
    }

    @Test
    void testFromStringCaseInsensitive() {
        assertEquals(PokeType.GRASS, PokeType.fromString("grass"));
        assertEquals(PokeType.WATER, PokeType.fromString("WATER"));
        assertEquals(PokeType.FIRE, PokeType.fromString("fIrE"));
    }

    @Test
    void testFromStringThrowsOnInvalidType() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> PokeType.fromString("Electric"));
        assertTrue(ex.getMessage().contains("Invalid Pokemon type"));
    }
}