package test.model.item;

import org.junit.jupiter.api.Test;
import pokeclicker.model.item.PokemonItem;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.common.Activatable;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.LevelType;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DummyPokemon extends Pokemon {
    private List<Ability> abilities = new ArrayList<>();
    public DummyPokemon(String name) {
        super(name, LevelType.BEGINNER, 0, 1, 1, false, 1, "dummy");
    }
    @Override
    public String getType() { return "Fire"; }
    @Override
    public List<Ability> getAbilities() { return abilities; }
    public void addAbility(Ability ability) { abilities.add(ability); }
}

public class PokemonItemTest {

    @Test
    void testConstructorAndGetters() {
        PokemonItem item = new PokemonItem("Fire Stone", 500.0, "Boosts fire abilities.", 10.0);
        assertEquals("Fire Stone", item.getName());
        assertEquals(500.0, item.getPrice());
        assertEquals("Boosts fire abilities.", item.getDescription());
        assertEquals(10.0, item.getMultiplierOrDamage());
        assertEquals(ItemType.POKEMON, item.getType());
        assertTrue(item.isAvailable());
    }

    @Test
    void testSetDamage() {
        PokemonItem item = new PokemonItem("Fire Stone", 500.0, "Boosts fire abilities.", 10.0);
        item.setDamage(20.0);
        assertEquals(20.0, item.getMultiplierOrDamage());
    }

    @Test
    void testActivateWithValidPokemon() {
        PokemonItem item = new PokemonItem("Fire Stone", 500.0, "Boosts fire abilities.", 15.0);
        DummyPokemon pokemon = new DummyPokemon("Charmander");
        Ability ability = new Ability("Ember", "Fire attack", PokeType.FIRE, 5.0, 0.0);
        pokemon.addAbility(ability);

        Activatable result = item.activate(pokemon);
        assertSame(pokemon, result);
        assertEquals(15.0, ability.getDamage());
    }

    @Test
    void testActivateThrowsIfNull() {
        PokemonItem item = new PokemonItem("Fire Stone", 500.0, "Boosts fire abilities.", 10.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> item.activate(null));
        assertTrue(ex.getMessage().toLowerCase().contains("cannot be null"));
    }

    @Test
    void testActivateThrowsIfNotPokemon() {
        PokemonItem item = new PokemonItem("Fire Stone", 500.0, "Boosts fire abilities.", 10.0);
        Activatable notAPokemon = new Activatable() {};
        Exception ex = assertThrows(IllegalArgumentException.class, () -> item.activate(notAPokemon));
        assertTrue(ex.getMessage().toLowerCase().contains("can only be used on pokemons"));
    }
}