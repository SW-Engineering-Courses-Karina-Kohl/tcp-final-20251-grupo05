package test.model.pokemon;

import org.junit.jupiter.api.Test;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTypeTest {

    @Test
    void testToStringReturnsCorrectLevel() {
        assertEquals("Beginner", LevelType.BEGINNER.toString());
        assertEquals("Advanced", LevelType.ADVANCED.toString());
    }

    @Test
    void testFromStringValidInputs() {
        assertEquals(LevelType.BEGINNER, LevelType.fromString("Beginner"));
        assertEquals(LevelType.ADVANCED, LevelType.fromString("Advanced"));
    }

    @Test
    void testFromStringCaseInsensitive() {
        assertEquals(LevelType.BEGINNER, LevelType.fromString("Beginner"));
        assertEquals(LevelType.ADVANCED, LevelType.fromString("Advanced"));
    }

    @Test
    void testFromStringThrowsOnInvalidType() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> LevelType.fromString("Expert"));
        assertTrue(ex.getMessage().contains("Invalid Level type"));
    }

    @Test
    void testFromIntValidInputs() {
        assertEquals(LevelType.BEGINNER, LevelType.fromInt(1));
        assertEquals(LevelType.ADVANCED, LevelType.fromInt(2));
    }

    @Test
    void testFromIntThrowsOnInvalidNumber() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> LevelType.fromInt(0));
        assertTrue(ex.getMessage().contains("Invalid level number"));
        ex = assertThrows(IllegalArgumentException.class, () -> LevelType.fromInt(4));
        assertTrue(ex.getMessage().contains("Invalid level number"));
    }

    @Test
    void testToInt() {
        assertEquals(1, LevelType.BEGINNER.toInt());
        assertEquals(2, LevelType.ADVANCED.toInt());
    }
}