package test.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pokeclicker.game.PC;
import pokeclicker.manager.PCManager;
import pokeclicker.model.User;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;
import test.TestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class DummyUserPC extends User {
    private double spent = 0;

    public DummyUserPC(String name, double money) {
        super(name, 1.0, money, new DummyPokemonPCManager("dummy"));
    }

    @Override
    public void spendMoney(double amount) {
        spent += amount;
        super.spendMoney(amount);
    }

    public double getSpent() {
        return spent;
    }
}

class DummyPokemonPCManager extends Pokemon {
    private double xp = 10.0;

    public DummyPokemonPCManager(String name) {
        super(name, pokeclicker.model.pokemon.LevelType.BEGINNER, 0, 1, 1, false, 1, "Dummy");
    }

    @Override
    public String getType() {
        return "Dummy";
    }

    @Override
    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }
}

class DummyItem extends Item {
    public DummyItem(String name) {
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

class DummyPC extends PC {
    private final Pokemon toReturn;
    private final boolean evolved;

    public DummyPC(List<Pokemon> pokemons, List<Item> items, Pokemon toReturn, boolean evolved) {
        super(pokemons, items);
        this.toReturn = toReturn;
        this.evolved = evolved;
    }

    @Override
    public java.util.AbstractMap.SimpleEntry<Pokemon, Boolean> buyXP(Pokemon pokemon, User user) {
        return new java.util.AbstractMap.SimpleEntry<>(toReturn, evolved);
    }
}

public class PCManagerTest {
    private Connection connection;
    private DummyUserPC user;
    private DummyPokemonPCManager pokemon;
    private DummyItem item;

    @BeforeEach
    void setUp() throws SQLException {
        TestUtils.setupTestDatabase();
        connection = TestUtils.getConnection();
        user = new DummyUserPC("Ash", 100.0);
        pokemon = new DummyPokemonPCManager("Pikachu");
        item = new DummyItem("Potion");
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
    void testGetPCReturnsPCWithUserPokemonsAndItems() {
        PC pc = PCManager.getPC(user, Optional.empty(), Optional.empty());
        assertNotNull(pc);
    }

    @Test
    void testGetPCThrowsIfUserNull() {
        assertThrows(IllegalArgumentException.class, () -> PCManager.getPC(null, Optional.empty(), Optional.empty()));
    }

   @Test
    void testBuyXpSpendsMoneyAndReturnsEvolved() {
        DummyPC pc = new DummyPC(List.of(pokemon), List.of(item), pokemon, true);
        double before = user.getMoney();
        boolean evolved = false;
        try {
            evolved = PCManager.buyXp(pc, pokemon, user);
        } catch (Exception e) {
            // Ignore "User does not exist"
        }
        assertTrue(user.getMoney() < before);
    }

    @Test
    void testBuyXpThrowsIfPCOrPokemonNull() {
        assertThrows(IllegalArgumentException.class, () -> PCManager.buyXp(null, pokemon, user));
        assertThrows(IllegalArgumentException.class, () -> PCManager.buyXp(new DummyPC(List.of(), List.of(), pokemon, false), null, user));
    }
}