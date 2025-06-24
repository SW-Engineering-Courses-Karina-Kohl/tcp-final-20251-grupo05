package test.manager.item;

import org.junit.jupiter.api.*;
import pokeclicker.manager.item.ItemManager;
import pokeclicker.model.item.Item;
import pokeclicker.model.item.ItemType;
import test.TestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemManagerTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        TestUtils.setupTestDatabase();
        connection = TestUtils.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        TestUtils.cleanTestDatabase();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        TestUtils.resetDatabaseUrl();
    }

    @Test
    void testCreateItemSuccess() {
        Item item = ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash");
        assertNotNull(item);
        assertEquals("Potion", item.getName());
        assertEquals(10.0, item.getPrice());
        assertEquals("Heals HP", item.getDescription());
        assertEquals(ItemType.POKEMON, item.getType());
    }

    @Test
    void testCreateItemThrowsIfExists() {
        ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash"));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void testGetItemSuccess() {
        ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash");
        Item item = ItemManager.getItem("Potion");
        assertNotNull(item);
        assertEquals("Potion", item.getName());
    }

    @Test
    void testGetItemThrowsIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            ItemManager.getItem("NonExistent"));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testGetAllItemsReturnsList() {
        ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash");
        ItemManager.createItem("Multiplier", 15.0, "Doubles money", ItemType.MONEY_MULTIPLIER, 2.0, "Ash");
        List<Item> items = ItemManager.getAllItems(Optional.empty());
        assertTrue(items.size() >= 2);
    }

    @Test
    void testUpdateItemSuccess() {
        ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash");
        Item item = ItemManager.getItem("Potion");
        item.setAvailable(true);
        ItemManager.updateItem(item);
        Item updated = ItemManager.getItem("Potion");
        assertTrue(updated.isAvailable());
    }

    @Test
    void testUpdateItemThrowsIfNotFound() {
        Item fake = new pokeclicker.model.item.PokemonItem("Fake", 1.0, "desc", 1.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            ItemManager.updateItem(fake));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testDeleteItemSuccess() {
        ItemManager.createItem("Potion", 10.0, "Heals HP", ItemType.POKEMON, 20.0, "Ash");
        ItemManager.deleteItem("Potion");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            ItemManager.getItem("Potion"));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testDeleteItemThrowsIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            ItemManager.deleteItem("NonExistent"));
        assertTrue(ex.getMessage().contains("not found"));
    }
}