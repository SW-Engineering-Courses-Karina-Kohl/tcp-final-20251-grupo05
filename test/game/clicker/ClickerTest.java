package test.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokeclicker.game.clicker.Clicker;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.LevelType;

public class ClickerTest {
    private User user;
    private Clicker clicker;
    private DummyPokemonClicker dummyPokemonClicker;

    @BeforeEach
    void setUp() {
        dummyPokemonClicker = new DummyPokemonClicker("ClickyMon");
        user = new User("ClickerPlayer", 1.0, 0.0, dummyPokemonClicker);
        clicker = new Clicker(user);
    }

    @Test
    void testConstructorInitialization() {
        assertEquals(user, clicker.getUser(), "Clicker should be associated with the correct user.");
        assertEquals(1, clicker.getMoneyPerClick(), "Default money per click should be 1.");
        assertEquals(0, clicker.getTotalClicks(), "Initial total clicks should be 0.");
        assertEquals(0.0, clicker.getTotalMoney(), "Initial total money earned should be 0.0.");
    }

    @Test
    void testRegisterSingleClick() {
        double initialUserMoney = user.getMoney();
        int initialGlobalClicks = Clicker.getTotalGlobalClicks();

        clicker.registerClick();

        assertEquals(1, clicker.getTotalClicks(), "Total clicks for the instance should be 1.");
        assertEquals(1.0, clicker.getTotalMoney(), "Total money for the instance should be 1.0.");
        assertEquals(initialGlobalClicks + 1, Clicker.getTotalGlobalClicks(), "Total global clicks should increment by 1.");
        
        double expectedUserMoney = initialUserMoney + (1.0 * user.getMoneyMultiplier());
        assertEquals(expectedUserMoney, user.getMoney(), "User's money should increase by the correct amount.");
    }

    @Test
    void testRegisterMultipleClicks() {
        clicker.registerClick();
        clicker.registerClick();
        clicker.registerClick();

        assertEquals(3, clicker.getTotalClicks(), "Total clicks should be 3 after three clicks.");
        assertEquals(3.0, clicker.getTotalMoney(), "Total money earned should be 3.0.");
        assertEquals(3.0, user.getMoney(), "User's money should be 3.0.");
    }

    @Test
    void testRegisterClickWithMultiplier() {
        User userWithMultiplier = new User("MultiplierPlayer", 2.5, 0.0, dummyPokemonClicker);
        Clicker clickerForMultiplier = new Clicker(userWithMultiplier);
        
        clickerForMultiplier.registerClick();

        assertEquals(2.5, clickerForMultiplier.getTotalMoney(), "Total money should account for the 2.5x multiplier.");
        assertEquals(2.5, userWithMultiplier.getMoney(), "User's money should increase by 2.5.");
    }

    @Test
    void testSetMoneyPerClick() {
        clicker.setMoneyPerClick(10);
        
        clicker.registerClick();

        assertEquals(10, clicker.getMoneyPerClick(), "getMoneyPerClick should return the new value.");
        assertEquals(10.0, clicker.getTotalMoney(), "Total money should be 10 for a single click.");
        assertEquals(10.0, user.getMoney(), "User's money should increase by 10.");
    }

    @Test
    void testResetClicks() {
        int initialGlobalClicks = Clicker.getTotalGlobalClicks();
        
        clicker.registerClick();
        clicker.registerClick();
        
        assertEquals(2, clicker.getTotalClicks());
        assertEquals(2.0, clicker.getTotalMoney());

        clicker.resetClicks();

        assertEquals(0, clicker.getTotalClicks(), "Total clicks for the instance should be reset to 0.");
        assertEquals(0.0, clicker.getTotalMoney(), "Total money for the instance should be reset to 0.0.");
        assertEquals(initialGlobalClicks + 2, Clicker.getTotalGlobalClicks(), "Global clicks should not be affected by reset.");
    }
}

class DummyPokemonClicker extends Pokemon {
    public DummyPokemonClicker(String name) {
        super(name, LevelType.BEGINNER, 0, 1, 1, false, 1, "Dummy");
    }

    @Override
    public String getType() {
        return "Dummy";
    }
}
