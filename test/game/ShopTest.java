package test.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import pokeclicker.model.pokemon.Pokemon; 
import pokeclicker.model.pokemon.LevelType;

import pokeclicker.game.Shop;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;


class DummyPurchasable implements Purchasable {
    private final String name;
    private final double price;
    private boolean available = true;

    public DummyPurchasable(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DummyPurchasable that = (DummyPurchasable) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}

public class ShopTest {

    private User user;
    private Shop shop;
    private DummyPurchasable affordableItem;
    private DummyPurchasable expensiveItem;
    private DummyPurchasable itemNotInShop;
    private DummyPokemon dummyPokemon;

    @BeforeEach
    void setUp() {
        dummyPokemon = new DummyPokemon("TestMon");

        user = new User("TestPlayer", 1.0, 100.0, dummyPokemon);

        affordableItem = new DummyPurchasable("Potion", 50.0);
        expensiveItem = new DummyPurchasable("Master Ball", 200.0);
        itemNotInShop = new DummyPurchasable("Rare Candy", 75.0);
        
        List<Purchasable> shopItems = new ArrayList<>(Arrays.asList(affordableItem, expensiveItem));

        shop = new Shop(user, shopItems);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        assertEquals(user, shop.getUser(), "Shop should be associated with the correct user.");
        assertEquals(2, shop.getPurchasables().size(), "Shop should have the correct number of items.");
        assertTrue(shop.getPurchasables().contains(affordableItem), "Shop should contain the affordable item.");
        assertTrue(shop.getPurchasables().contains(expensiveItem), "Shop should contain the expensive item.");
    }

    @Test
    void testBuyItemSuccess() {
        assertTrue(affordableItem.isAvailable(), "Item should be available before purchase.");
        
        Purchasable boughtItem = shop.buyPokemonOrItem(affordableItem);

        assertNotNull(boughtItem, "The bought item should not be null.");
        assertEquals(affordableItem, boughtItem, "The returned item should be the one we tried to buy.");
        assertFalse(boughtItem.isAvailable(), "The item should be marked as unavailable after purchase.");
    }


    @Test
    void testBuyItemThrowsExceptionNotEnoughMoney() {
        assertTrue(expensiveItem.isAvailable(), "Item should still be available before a failed purchase attempt.");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            shop.buyPokemonOrItem(expensiveItem);
        });

        assertEquals("Not enough money to buy this item.", exception.getMessage());
        assertTrue(expensiveItem.isAvailable(), "Item should remain available after a failed purchase.");
    }

    @Test
    void testBuyItemThrowsExceptionItemNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            shop.buyPokemonOrItem(itemNotInShop);
        });
        
        assertEquals("Item not found in the shop.", exception.getMessage());
    }

    @Test
    void testBuyItemSuccessDeductsMoney() {
        double initialMoney = user.getMoney();
        double itemPrice = affordableItem.getPrice();

        shop.buyPokemonOrItem(affordableItem);

        double expectedMoney = initialMoney - itemPrice;
        assertEquals(expectedMoney, user.getMoney(), "User's money should be deducted by the item's price after purchase.");
    }
}