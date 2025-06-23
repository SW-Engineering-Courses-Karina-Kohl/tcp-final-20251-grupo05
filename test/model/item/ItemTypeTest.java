package test.model.item;

import org.junit.jupiter.api.Test;
import pokeclicker.model.item.ItemType;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTypeTest {

    @Test
    void testToStringReturnsCorrectType() {
        assertEquals("Money Multiplier", ItemType.MONEY_MULTIPLIER.toString());
        assertEquals("Pokemon", ItemType.POKEMON.toString());
    }

    @Test
    void testFromStringValidInputs() {
        assertEquals(ItemType.MONEY_MULTIPLIER, ItemType.fromString("Money Multiplier"));
        assertEquals(ItemType.POKEMON, ItemType.fromString("Pokemon"));
    }

    @Test
    void testFromStringCaseInsensitive() {
        assertEquals(ItemType.MONEY_MULTIPLIER, ItemType.fromString("money multiplier"));
        assertEquals(ItemType.POKEMON, ItemType.fromString("POKEMON"));
    }

    @Test
    void testFromStringThrowsOnInvalidType() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> ItemType.fromString("Potion"));
        assertTrue(ex.getMessage().contains("Invalid Item type"));
    }
}