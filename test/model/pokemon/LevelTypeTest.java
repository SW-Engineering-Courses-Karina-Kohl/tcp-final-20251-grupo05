package test.model.pokemon;

import org.junit.jupiter.api.Test;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTypeTest {

    @Test
    void testToStringReturnsCorrectLevel() {
        assertEquals("Beginner", LevelType.BEGINNER.toString());
        assertEquals("Intermediate", LevelType.INTERMEDIATE.toString());
        assertEquals("Advanced", LevelType.ADVANCED.toString());
    }

    @Test
    void testFromStringValidInputs() {
        assertEquals(LevelType.BEGINNER, LevelType.fromString("Beginner"));
        assertEquals(LevelType.INTERMEDIATE, LevelType.fromString("Intermediate"));
        assertEquals(LevelType.ADVANCED, LevelType.fromString("Advanced"));
    }

    @Test
    void testFromStringCaseInsensitive() {
        assertEquals(LevelType.BEGINNER, LevelType.fromString("beginner"));
        assertEquals(LevelType.INTERMEDIATE, LevelType.fromString("INTERMEDIATE"));
        assertEquals(LevelType.ADVANCED, LevelType.fromString("aDvAnCeD"));
    }

    @Test
    void testFromStringThrowsOnInvalidType() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> LevelType.fromString("Expert"));
        assertTrue(ex.getMessage().contains("Invalid Level type"));
    }

    @Test
    void testFromIntValidInputs() {
        assertEquals(LevelType.BEGINNER, LevelType.fromInt(1));
        assertEquals(LevelType.INTERMEDIATE, LevelType.fromInt(2));
        assertEquals(LevelType.ADVANCED, LevelType.fromInt(3));
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
        assertEquals(2, LevelType.INTERMEDIATE.toInt());
        assertEquals(3, LevelType.ADVANCED.toInt());
    }
}