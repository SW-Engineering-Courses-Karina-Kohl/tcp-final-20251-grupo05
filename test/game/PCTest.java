package test.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import pokeclicker.model.pokemon.LevelType;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.common.Activatable; 
/*
class DummyItem extends pokeclicker.model.item.Item {
    private final String name;

    public DummyItem(String name) {
        super(name, 0.0, "A dummy item.");
        this.name = name;
    }

    @Override
    public String getName() { return name; }

    @Override
    public ItemType getType() {
        return ItemType.POKEMON;
    }

    @Override
    public double getMultiplierOrDamage() {
        return 0.0;
    }

    @Override
    public Activatable activate(Activatable activatable) {
        return activatable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DummyItem)) return false;
        DummyItem that = (DummyItem) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }
}

class DummyPokemon extends pokeclicker.model.pokemon.Pokemon {
    private final String name;

    public DummyPokemon(String name) {
        super(name, LevelType.BEGINNER, 1.0, 1, 100, false, 1.0, "Dummy Species");
        this.name = name;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getType() {
        return "DummyType";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DummyPokemon)) return false;
        DummyPokemon that = (DummyPokemon) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }
}
*/
public class PCTest {

   /* private DummyPokemon p1, p2, p3;
    private DummyItem i1, i2, i3;
    private pokeclicker.game.PC pc;

    @BeforeEach
    void setUp() {
        p1 = new DummyPokemon("Pikachu");
        p2 = new DummyPokemon("Bulbasaur");
        p3 = new DummyPokemon("Charmander");
        i1 = new DummyItem("Potion");
        i2 = new DummyItem("Pokeball");
        i3 = new DummyItem("Revive");
        pc = new pokeclicker.game.PC(Arrays.asList(p1, p2), Arrays.asList(i1, i2), p1);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        List<pokeclicker.model.pokemon.Pokemon> pokemons = pc.getPokemons();
        assertTrue(pokemons.contains(p1));
        assertTrue(pokemons.contains(p2));
        assertEquals(p1, pc.getFavoritePokemon());
        assertEquals(0, pc.getItemCount(i1));
        assertEquals(0, pc.getItemCount(i2));
        assertEquals(0, pc.getItemCount(i3));
    }

    @Test
    void testAddPokemon() {
        pc.addPokemon(p3);
        List<pokeclicker.model.pokemon.Pokemon> pokemons = pc.getPokemons();
        assertTrue(pokemons.contains(p3));
        assertEquals(3, pokemons.size());
    }

    @Test
    void testAddItem() {
        pc.addItem(i1);
        assertEquals(1, pc.getItemCount(i1));
        pc.addItem(i1);
        assertEquals(2, pc.getItemCount(i1));
        pc.addItem(i3);
        assertEquals(1, pc.getItemCount(i3));
    }

    @Test
    void testRemoveItemReducesCountAndRemovesWhenZero() {
        pc.addItem(i1);
        pc.addItem(i1);
        pc.removeItem(i1, 1);
        assertEquals(1, pc.getItemCount(i1));
        pc.removeItem(i1, 1);
        assertEquals(0, pc.getItemCount(i1));
        assertFalse(pc.getItemQuantities().containsKey(i1));
    }

    @Test
    void testRemoveItemThrowsIfNotEnough() {
        pc.addItem(i2);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> pc.removeItem(i2, 2));
        assertTrue(ex.getMessage().contains("Not enough"));
    }

    @Test
    void testRemoveItemThrowsIfNotPresent() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> pc.removeItem(i3, 1));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testGetItemCountForAbsentItem() {
        assertEquals(0, pc.getItemCount(i3));
    }

    @Test
    void testSetAndGetFavoritePokemon() {
        pc.setFavoritePokemon(p2);
        assertEquals(p2, pc.getFavoritePokemon());
    }*/
}