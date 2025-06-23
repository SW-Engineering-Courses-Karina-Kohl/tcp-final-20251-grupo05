package test.manager.pokemon;

import org.junit.jupiter.api.*;

import pokeclicker.database.AbilityDB;
import pokeclicker.manager.pokemon.PokemonManager;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.Pokemon;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonManagerTest {

    @BeforeEach
    void setUp() {
        // Clean up test pokemons if they exist
        try { PokemonManager.deletePokemon("Charmander"); } catch (Exception ignored) {}
        try { PokemonManager.deletePokemon("Squirtle"); } catch (Exception ignored) {}
        try { PokemonManager.deletePokemon("Bulbasaur"); } catch (Exception ignored) {}
    }

    @Test
    void testCreatePokemonSuccess() {
        Pokemon pokemon = PokemonManager.createPokemon(
                "Charmander", 39, List.of(), 100.0, PokeType.FIRE, "img.png", "Ash");
        assertNotNull(pokemon);
        assertEquals("Charmander", pokemon.getName());
        assertEquals("FIRE", pokemon.getType());
    }

    @Test
    void testCreatePokemonThrowsIfExists() {
        PokemonManager.createPokemon("Charmander", 39, List.of(), 100.0, PokeType.FIRE, "img.png", "Ash");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                PokemonManager.createPokemon("Charmander", 39, List.of(), 100.0, PokeType.FIRE, "img.png", "Ash"));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void testGetAllPokemonsReturnsList() {
        PokemonManager.createPokemon("Bulbasaur", 45, List.of(), 100.0, PokeType.GRASS, "img.png", "Ash");
        List<Pokemon> pokemons = PokemonManager.getAllPokemons(Optional.empty());
        assertTrue(pokemons.stream().anyMatch(p -> p.getName().equals("Bulbasaur")));
    }

    @Test
    void testUpdatePokemonSuccess() {
        Pokemon pokemon = PokemonManager.createPokemon("Squirtle", 44, List.of(), 100.0, PokeType.WATER, "img.png", "Ash");
        pokemon.setAvailable(false);
        PokemonManager.updatePokemon(pokemon);
    }

    @Test
    void testUpdatePokemonThrowsIfNull() {
        assertThrows(IllegalArgumentException.class, () -> PokemonManager.updatePokemon(null));
    }

    @Test
    void testDeletePokemonSuccess() {
        PokemonManager.createPokemon("Charmander", 39, List.of(), 100.0, PokeType.FIRE, "img.png", "Ash");
        PokemonManager.deletePokemon("Charmander");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                PokemonManager.deletePokemon("Charmander"));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void testDeletePokemonThrowsIfNameNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> PokemonManager.deletePokemon(null));
        assertThrows(IllegalArgumentException.class, () -> PokemonManager.deletePokemon(""));
    }

    @Test
    void testAddAbilityToPokemonThrowsIfTypeMismatch() {
        Ability fireAbility = new Ability("TestEmber", "A test fire ability", PokeType.FIRE, 10, 0);
        AbilityDB.insertAbility(fireAbility);
        PokemonManager.createPokemon("Charmander", 39, List.of(), 100.0, PokeType.WATER, "img.png", "Ash");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                PokemonManager.addAbilityToPokemon("Charmander", "TestEmber"));
        assertTrue(ex.getMessage().equalsIgnoreCase("Ability type does not match Pokemon type"));
    }

    @Test
    void testRemoveAbilityFromPokemonThrowsIfNotPresent() {
        PokemonManager.createPokemon("Squirtle", 44, List.of(), 100.0, PokeType.WATER, "img.png", "Ash");
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                PokemonManager.removeAbilityFromPokemon("Squirtle", "NonExistentAbility"));
        assertTrue(ex.getMessage().contains("does not have this ability"));
    }
}