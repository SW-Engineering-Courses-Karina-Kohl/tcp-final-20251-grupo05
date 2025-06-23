package test.model.item;

import org.junit.jupiter.api.Test;
import pokeclicker.model.User;
import pokeclicker.model.item.MoneyMultiplierItem;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.common.Activatable;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyMultiplierItemTest {

    @Test
    void testConstructorAndGetters() {
        MoneyMultiplierItem item = new MoneyMultiplierItem("Lucky Egg", 2000.0, "Increases earned money.", 2.0);
        assertEquals("Lucky Egg", item.getName());
        assertEquals(2000.0, item.getPrice());
        assertEquals("Increases earned money.", item.getDescription());
        assertEquals(2.0, item.getMultiplierOrDamage());
        assertEquals(ItemType.MONEY_MULTIPLIER, item.getType());
        assertTrue(item.isAvailable());
    }

    @Test
    void testSetMultiplier() {
        MoneyMultiplierItem item = new MoneyMultiplierItem("Lucky Egg", 2000.0, "Increases earned money.", 2.0);
        item.setMultiplier(3.5);
        assertEquals(3.5, item.getMultiplierOrDamage());
    }

    @Test
    void testActivateWithValidUser() {
        MoneyMultiplierItem item = new MoneyMultiplierItem("Lucky Egg", 2000.0, "Increases earned money.", 2.0);
        User user = new User("Ash", 1.0, 100.0, null);
        Activatable result = item.activate(user);
        assertSame(user, result);
        assertEquals(2.0, user.getMoneyMultiplier());
    }

    @Test
    void testActivateThrowsIfNull() {
        MoneyMultiplierItem item = new MoneyMultiplierItem("Lucky Egg", 2000.0, "Increases earned money.", 2.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> item.activate(null));
        assertTrue(ex.getMessage().toLowerCase().contains("cannot be null"));
    }

    @Test
    void testActivateThrowsIfNotUser() {
        MoneyMultiplierItem item = new MoneyMultiplierItem("Lucky Egg", 2000.0, "Increases earned money.", 2.0);
        Activatable notAUser = new Activatable() {};
        Exception ex = assertThrows(IllegalArgumentException.class, () -> item.activate(notAUser));
        assertTrue(ex.getMessage().toLowerCase().contains("só pode ser usado em users") || ex.getMessage().toLowerCase().contains("only be used on users"));
    }

    @Test
    void testActivateThrowsIfMultiplierNonPositive() {
        MoneyMultiplierItem item = new MoneyMultiplierItem("Lucky Egg", 2000.0, "Increases earned money.", 0.0);
        User user = new User("Ash", 1.0, 100.0, null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> item.activate(user));
        assertTrue(ex.getMessage().toLowerCase().contains("greater than zero"));
    }
}