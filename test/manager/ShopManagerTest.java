package test.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pokeclicker.game.Shop;
import pokeclicker.manager.ShopManager;
import pokeclicker.manager.item.ItemFilter;
import pokeclicker.manager.pokemon.PokemonFilter;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DummyUser extends User {
    private double moneySpent = 0;

    public DummyUser(String name, double money) {
        super(name, 1.0, money, new DummyPokemon("dummy"));
    }

    @Override
    public void spendMoney(double amount) {
        moneySpent += amount;
        super.spendMoney(amount);
    }

    public double getMoneySpent() {
        return moneySpent;
    }
}

class ShopTestDummyPokemon extends Pokemon {
    public ShopTestDummyPokemon(String name) {
        super(name, pokeclicker.model.pokemon.LevelType.BEGINNER, 0, 1, 1, false, 1, "Dummy");
    }

    @Override
    public String getType() {
        return "Dummy";
    }
}

class ShopTestDummyItem extends Item {
    public ShopTestDummyItem(String name) {
        super(name, 1.0, "desc");
    }

    @Override
    public pokeclicker.model.item.ItemType getType() {
        return pokeclicker.model.item.ItemType.POKEMON;
    }

    @Override
    public double getMultiplierOrDamage() {
        return 1.0;
    }

    @Override
    public pokeclicker.model.common.Activatable activate(pokeclicker.model.common.Activatable a) {
        return a;
    }
}

class DummyShop extends Shop {
    private final Purchasable toReturn;
    public boolean buyCalled = false;

    public DummyShop(User user, List<Purchasable> purchasables, Purchasable toReturn) {
        super(user, purchasables);
        this.toReturn = toReturn;
    }

    @Override
    public Purchasable buyPokemonOrItem(Purchasable p) {
        buyCalled = true;
        return toReturn;
    }
}

public class ShopManagerTest {

    private DummyUser user;
    private ShopTestDummyPokemon pokemon;
    private ShopTestDummyItem item;

    @BeforeEach
    void setUp() {
        user = new DummyUser("Ash", 100.0);
        pokemon = new ShopTestDummyPokemon("Pikachu");
        item = new ShopTestDummyItem("Potion");
    }

    @Test
    void testGetShopReturnsShopWithUserAndPurchasables() {
        Shop shop = ShopManager.getShop(user, Optional.empty(), Optional.empty());
        assertNotNull(shop);
        assertEquals(user, shop.getUser());
    }

    @Test
    void testBuyPokemonOrItemSpendsMoney() {
        DummyShop shop = new DummyShop(user, List.of(pokemon, item), pokemon);
        double before = user.getMoney();
        ShopManager.buyPokemonOrItem(user, shop, pokemon);
        assertTrue(shop.buyCalled);
        assertTrue(user.getMoney() < before);
    }

    @Test
    void testBuyPokemonOrItemHandlesExceptionGracefully() {
        Shop shop = new Shop(user, List.of(pokemon, item)) {
            @Override
            public Purchasable buyPokemonOrItem(Purchasable p) {
                throw new RuntimeException("fail");
            }
        };
        assertDoesNotThrow(() -> ShopManager.buyPokemonOrItem(user, shop, pokemon));
    }
}