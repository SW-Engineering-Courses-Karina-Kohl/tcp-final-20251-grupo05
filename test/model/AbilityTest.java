package test.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pokeclicker.model.common.PokeType;
import pokeclicker.model.Ability;

public class AbilityTest {
    @Test
    public void testAbilityConstructorAndGetters() {
        PokeType type = PokeType.FIRE;
        Ability ability = new Ability("Flame Burst", "A burst of fire", type, 50.0, 10.0);

        assertEquals("Flame Burst", ability.getName());
        assertEquals("A burst of fire", ability.getDescription());
        assertEquals("Fire", ability.getType());
        assertEquals(50.0, ability.getDamage(), 0.0001);
        assertEquals(10.0, ability.getCure(), 0.0001);
    }

    @Test
    public void testUpdateDamage() {
        Ability ability = new Ability("Tackle", "A physical attack", PokeType.FIRE, 20.0, 0.0);
        ability.updateDamage(35.5);
        assertEquals(35.5, ability.getDamage(), 0.0001);
    }

    @Test
    public void testUpdateDamageNegativeThrows() {
        Ability ability = new Ability("Tackle", "A physical attack", PokeType.FIRE, 20.0, 0.0);
        try {
            ability.updateDamage(-5.0);
            org.junit.jupiter.api.Assertions.fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Exception was thrown as expected
        }
    }
}