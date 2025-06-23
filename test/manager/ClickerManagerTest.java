package test.manager;

import org.junit.jupiter.api.Test;
import pokeclicker.manager.ClickerManager;
import pokeclicker.model.User;

import static org.junit.jupiter.api.Assertions.*;

import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.LevelType;

class DummyPokemon extends Pokemon {
    public DummyPokemon(String name) {
        super(name, LevelType.BEGINNER, 0, 1, 1, false, 1, "Dummy");
    }

    @Override
    public String getType() {
        return "Dummy";
    }
}

class DummyUser extends User {
    public DummyUser(String name, double money) {
        super(name, 1.0, money, null);
    }
}

public class ClickerManagerTest {

    @Test
    void testClickUpdatesUserMoneyCorrectly() {
        DummyUser user = new DummyUser("Ash", 100.0);
        double initialMoney = user.getMoney();
        double moneyMultiplier = user.getMoneyMultiplier();
        
        double expectedMoney = initialMoney + moneyMultiplier;

        try {
            ClickerManager.click(user);
        } catch (Exception e) {
            // Ignore "User does not exist" for this unit test
        }
        assertEquals(expectedMoney, user.getMoney(), "User money should increase by the moneyMultiplier after a click.");
    }
}