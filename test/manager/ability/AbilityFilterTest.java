package test.manager.ability;

import org.junit.jupiter.api.Test;
import pokeclicker.manager.ability.AbilityFilter;
import pokeclicker.model.common.PokeType;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityFilterTest {

    @Test
    void testDefaultConstructorInitializesNulls() {
        AbilityFilter filter = new AbilityFilter();
        assertNull(filter.getType());
        assertNull(filter.getMinDamage());
        assertNull(filter.getMaxDamage());
        assertNull(filter.getMinCure());
        assertNull(filter.getMaxCure());
    }

    @Test
    void testSetAndGetType() {
        AbilityFilter filter = new AbilityFilter();
        filter.setType(PokeType.FIRE);
        assertEquals(PokeType.FIRE, filter.getType());
    }

    @Test
    void testSetAndGetMinDamage() {
        AbilityFilter filter = new AbilityFilter();
        filter.setMinDamage(10.5);
        assertEquals(10.5, filter.getMinDamage());
    }

    @Test
    void testSetAndGetMaxDamage() {
        AbilityFilter filter = new AbilityFilter();
        filter.setMaxDamage(99.9);
        assertEquals(99.9, filter.getMaxDamage());
    }

    @Test
    void testSetAndGetMinCure() {
        AbilityFilter filter = new AbilityFilter();
        filter.setMinCure(5.0);
        assertEquals(5.0, filter.getMinCure());
    }

    @Test
    void testSetAndGetMaxCure() {
        AbilityFilter filter = new AbilityFilter();
        filter.setMaxCure(50.0);
        assertEquals(50.0, filter.getMaxCure());
    }
}