package test.model.common;

import org.junit.jupiter.api.Test;
import pokeclicker.model.common.Purchasable;

import static org.junit.jupiter.api.Assertions.*;

class DummyPurchasable implements Purchasable {
    private boolean available = true;
    private double price = 42.0;

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

public class PurchasableTest {

    @Test
    void testIsAvailableAndSetAvailable() {
        DummyPurchasable item = new DummyPurchasable();
        assertTrue(item.isAvailable());
        item.setAvailable(false);
        assertFalse(item.isAvailable());
    }

    @Test
    void testGetAndSetPrice() {
        DummyPurchasable item = new DummyPurchasable();
        assertEquals(42.0, item.getPrice());
        item.setPrice(99.9);
        assertEquals(99.9, item.getPrice());
    }
}