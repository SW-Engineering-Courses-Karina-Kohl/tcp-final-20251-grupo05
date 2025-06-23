package test.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; 

import pokeclicker.database.ItemDB;
import pokeclicker.manager.item.ItemFilter;
import pokeclicker.model.item.Item;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.item.MoneyMultiplierItem;
import test.TestUtils;

public class ItemDBTest {
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
    void testInsertAndGetItem() throws SQLException {
        TestUtils.insertTestItem();

        Item retrieved = ItemDB.getItem("Double");

        assertNotNull(retrieved, "Item should be found in the database");
        assertEquals("Double", retrieved.getName());
        assertEquals(50.0, retrieved.getPrice());
        assertEquals("Double the users money", retrieved.getDescription());
        assertEquals(2.0, retrieved.getMultiplierOrDamage());
        assertEquals(MoneyMultiplierItem.class, retrieved.getClass());
    }

    @Test
    void testGetAllItems() throws SQLException {
        TestUtils.insertTestItem();
        List<Item> items = ItemDB.getAllItems(Optional.empty());
        assertFalse(items.isEmpty(), "Should retrieve at least one item");
        boolean found = items.stream().anyMatch(i -> i.getName().equals("Double"));
        assertTrue(found, "Inserted item should be present in the list");
    }

    @Test
    void testUpdateItem() throws SQLException {
        TestUtils.insertTestItem();
        Item item = ItemDB.getItem("Double");
        assertNotNull(item);

        item.setDescription("Updated description");
        item.setAvailable(false);

        if (item instanceof MoneyMultiplierItem) {
            ((MoneyMultiplierItem) item).setMultiplier(3.0);
        }

        ItemDB.updateItem(item);

        Item updated = ItemDB.getItem("Double");
        assertNotNull(updated);
        assertEquals("Updated description", updated.getDescription());
        assertFalse(updated.isAvailable());
        if (updated instanceof MoneyMultiplierItem) {
            assertEquals(3.0, ((MoneyMultiplierItem) updated).getMultiplierOrDamage());
        }
    }

    @Test
    void testDeleteItem() throws SQLException {
        TestUtils.insertTestItem();
        ItemDB.deleteItem("Double");
        Item deleted = ItemDB.getItem("Double");
        assertNull(deleted, "Item should be deleted and not found in the database");
    }

    @Test
    void testGetAllItemsWithFilter() throws SQLException {
        TestUtils.insertTestItem();

        ItemFilter filter = TestUtils.getTestAllItemsFilter();

        List<Item> items = ItemDB.getAllItems(Optional.of(filter));
        assertFalse(items.isEmpty(), "Should retrieve at least one item with the filter");
        Item found = items.get(0);
        assertEquals("Double", found.getName());
        assertEquals(MoneyMultiplierItem.class, found.getClass());
    }
}