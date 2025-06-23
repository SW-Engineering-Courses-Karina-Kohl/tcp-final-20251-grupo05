package pokeclicker.model.item;

import pokeclicker.model.common.Activatable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcreteItem extends Item {
    private ItemType type = ItemType.MONEY_MULTIPLIER;
    private double value;

    public ConcreteItem(String name, double price, String description, double value) {
        super(name, price, description);
        this.value = value;
    }

    @Override
    public Activatable activate(Activatable activatable) {
        return activatable;
    }

    @Override
    public double getMultiplierOrDamage() {
        return value;
    }

    @Override
    public ItemType getType() {
        return type;
    }
}

class MockActivatable implements Activatable {}

class ItemTest {
    private ConcreteItem testItem;
    private String initialName = "Lucky Egg";
    private double initialPrice = 2000.0;
    private String initialDescription = "Aumenta o dinheiro ganho.";
    private ItemType initialType = ItemType.MONEY_MULTIPLIER;
    private double initialValue = 2.0;

    @BeforeEach
    void setUp() {
        testItem = new ConcreteItem(initialName, initialPrice, initialDescription, initialValue);
    }

    @Test
    void testConstructorInitialization() {
        assertNotNull(testItem);
        assertEquals(initialName, testItem.getName());
        assertEquals(initialPrice, testItem.getPrice());
        assertEquals(initialDescription, testItem.getDescription());
        assertTrue(testItem.isAvailable());
        assertEquals(initialType, testItem.getType());
        assertEquals(initialValue, testItem.getMultiplierOrDamage());
    }

    @Test
    void testGetName() {
        assertEquals(initialName, testItem.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(initialPrice, testItem.getPrice());
    }

    @Test
    void testIsAvailable() {
        assertTrue(testItem.isAvailable());
        testItem.setAvailable(false);
        assertFalse(testItem.isAvailable());
    }

    @Test
    void testSetAvailableTrue() {
        testItem.setAvailable(false);
        assertFalse(testItem.isAvailable());
        testItem.setAvailable(true);
        assertTrue(testItem.isAvailable());
    }

    @Test
    void testSetAvailableFalse() {
        testItem.setAvailable(true);
        assertTrue(testItem.isAvailable());
        testItem.setAvailable(false);
        assertFalse(testItem.isAvailable());
    }

    @Test
    void testGetDescription() {
        assertEquals(initialDescription, testItem.getDescription());
    }

    @Test
    void testSetDescription() {
        String newDescription = "Um item que dobra o dinheiro!";
        testItem.setDescription(newDescription);
        assertEquals(newDescription, testItem.getDescription());
    }

    @Test
    void testGetType() {
        assertEquals(initialType, testItem.getType());
    }

    @Test
    void testGetMultiplierOrDamage() {
        assertEquals(initialValue, testItem.getMultiplierOrDamage());
    }

    @Test
    void testActivate() {
        MockActivatable mockActivatable = new MockActivatable();
        Activatable activated = testItem.activate(mockActivatable);
        assertSame(mockActivatable, activated);
    }

    @Test
    void testSetDescriptionNull() {
        testItem.setDescription(null);
        assertNull(testItem.getDescription());
    }

    @Test
    void testSetDescriptionEmpty() {
        testItem.setDescription("");
        assertEquals("", testItem.getDescription());
    }
}