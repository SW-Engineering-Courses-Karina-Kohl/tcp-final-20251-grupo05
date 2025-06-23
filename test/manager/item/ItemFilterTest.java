package test.manager.item;

import org.junit.jupiter.api.Test;
import pokeclicker.manager.item.ItemFilter;
import pokeclicker.model.item.ItemType;

import static org.junit.jupiter.api.Assertions.*;

public class ItemFilterTest {

    @Test
    void testDefaultConstructorInitializesNulls() {
        ItemFilter filter = new ItemFilter();
        assertNull(filter.getType());
        assertNull(filter.getMinPrice());
        assertNull(filter.getMaxPrice());
        assertNull(filter.getNameContains());
        assertNull(filter.getAvailable());
        assertNull(filter.getDescriptionContains());
        assertNull(filter.getUser());
    }

    @Test
    void testSetAndGetType() {
        ItemFilter filter = new ItemFilter();
        filter.setType(ItemType.POKEMON);
        assertEquals(ItemType.POKEMON, filter.getType());
    }

    @Test
    void testSetAndGetMinPrice() {
        ItemFilter filter = new ItemFilter();
        filter.setMinPrice(10.0);
        assertEquals(10.0, filter.getMinPrice());
    }

    @Test
    void testSetAndGetMaxPrice() {
        ItemFilter filter = new ItemFilter();
        filter.setMaxPrice(99.9);
        assertEquals(99.9, filter.getMaxPrice());
    }

    @Test
    void testSetAndGetNameContains() {
        ItemFilter filter = new ItemFilter();
        filter.setNameContains("Potion");
        assertEquals("Potion", filter.getNameContains());
    }

    @Test
    void testSetAndGetAvailable() {
        ItemFilter filter = new ItemFilter();
        filter.setAvailable(true);
        assertTrue(filter.getAvailable());
    }

    @Test
    void testSetAndGetDescriptionContains() {
        ItemFilter filter = new ItemFilter();
        filter.setDescriptionContains("heals");
        assertEquals("heals", filter.getDescriptionContains());
    }

    @Test
    void testSetAndGetUser() {
        ItemFilter filter = new ItemFilter();
        filter.setUser("Ash");
        assertEquals("Ash", filter.getUser());
    }
}