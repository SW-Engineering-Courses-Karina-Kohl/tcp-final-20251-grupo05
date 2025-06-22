package test.manager.pokemon;

import org.junit.jupiter.api.Test;
import pokeclicker.manager.pokemon.PokemonFilter;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.LevelType;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonFilterTest {

    @Test
    void testDefaultConstructorInitializesNulls() {
        PokemonFilter filter = new PokemonFilter();
        assertNull(filter.getName());
        assertNull(filter.getType());
        assertNull(filter.getMinLevel());
        assertNull(filter.getMinHealth());
        assertNull(filter.getAvailable());
        assertNull(filter.getMaxPrice());
        assertNull(filter.getAbility());
        assertNull(filter.getUser());
    }

    @Test
    void testSetAndGetName() {
        PokemonFilter filter = new PokemonFilter();
        filter.setName("Pikachu");
        assertEquals("Pikachu", filter.getName());
    }

    @Test
    void testSetAndGetType() {
        PokemonFilter filter = new PokemonFilter();
        filter.setType(PokeType.FIRE);
        assertEquals(PokeType.FIRE, filter.getType());
    }

    @Test
    void testSetAndGetMinLevel() {
        PokemonFilter filter = new PokemonFilter();
        filter.setMinLevel(LevelType.BEGINNER);
        assertEquals(LevelType.BEGINNER, filter.getMinLevel());
    }

    @Test
    void testSetAndGetMinHealth() {
        PokemonFilter filter = new PokemonFilter();
        filter.setMinHealth(50);
        assertEquals(50, filter.getMinHealth());
    }

    @Test
    void testSetAndGetAvailable() {
        PokemonFilter filter = new PokemonFilter();
        filter.setAvailable(true);
        assertTrue(filter.getAvailable());
    }

    @Test
    void testSetAndGetMaxPrice() {
        PokemonFilter filter = new PokemonFilter();
        filter.setMaxPrice(100.0);
        assertEquals(100.0, filter.getMaxPrice());
    }

    @Test
    void testSetAndGetAbility() {
        PokemonFilter filter = new PokemonFilter();
        Ability ability = new Ability("Thunder", "desc", PokeType.FIRE, 50.0, 0.0);
        filter.setAbility(ability);
        assertEquals(ability, filter.getAbility());
    }

    @Test
    void testSetAndGetUser() {
        PokemonFilter filter = new PokemonFilter();
        filter.setUser("Ash");
        assertEquals("Ash", filter.getUser());
    }
}