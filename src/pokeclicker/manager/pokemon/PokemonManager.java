package pokeclicker.manager.pokemon;

import java.util.List;
import java.util.Optional;
import pokeclicker.database.AbilityDB;
import pokeclicker.database.PokemonAbilityDB;
import pokeclicker.database.PokemonDB;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.FirePokemon;
import pokeclicker.model.pokemon.GrassPokemon;
import pokeclicker.model.pokemon.LevelType;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.WaterPokemon;

public class PokemonManager {

    private PokemonManager() {
    }

    public static Pokemon createPokemon(String name, int totalHealth, List<Ability> habilities, double price,
            PokeType type, String imagePath, String userName)
            throws IllegalArgumentException {
        if (PokemonDB.getPokemon(name) != null) {
            throw new IllegalArgumentException("The Pokemon name already exists!");
        }

        Pokemon newPokemon;

        switch (type) {
            case FIRE ->
                newPokemon = new FirePokemon(name, LevelType.BEGINNER, 0.0, totalHealth, totalHealth, true, price,
                        imagePath);
            case WATER ->
                newPokemon = new WaterPokemon(name, LevelType.BEGINNER, 0.0, totalHealth, totalHealth, true, price,
                        imagePath);
            case GRASS ->
                newPokemon = new GrassPokemon(name, LevelType.BEGINNER, 0.0, totalHealth, totalHealth, true, price,
                        imagePath);
            default -> throw new IllegalArgumentException("Invalid Pokemon type!");
        }

        PokemonDB.insertPokemon(newPokemon, userName);
        return newPokemon;
    }

    private static Pokemon getPokemon(String name) {
        Pokemon pokemon = PokemonDB.getPokemon(name);
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon does not exist");
        }

        return pokemon;
    }

    public static List<Pokemon> getAllPokemons(Optional<PokemonFilter> filter) {
        return PokemonDB.getAllPokemons(filter);
    }

    public static void updatePokemon(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null");
        }
        String name = pokemon.getName();
        if (name == null || PokemonDB.getPokemon(name) == null) {
            throw new IllegalArgumentException("Pokemon name cannot be null or empty");
        }
        PokemonDB.updatePokemon(pokemon);
    }

    public static void deletePokemon(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Pokemon name cannot be null or empty");
        }
        if (PokemonDB.getPokemon(name) == null) {
            throw new IllegalArgumentException("Pokemon does not exist");
        }
        PokemonDB.deletePokemon(name);
    }

    public static void addAbilityToPokemon(String pokemonName, String abilityName) {
        Pokemon pokemon = getPokemon(pokemonName);
        Ability ability = AbilityDB.getAbility(abilityName);
        if (pokemonName == null || pokemon == null) {
            throw new IllegalArgumentException("Pokemon does not exist or name is null");
        }
        if (abilityName == null || ability == null) {
            throw new IllegalArgumentException("Ability does not exist or name is null");
        }
        if (!pokemon.getType().toLowerCase().equals(ability.getType().toLowerCase())) {
            throw new IllegalArgumentException("Ability type does not match Pokemon type");
        }

        PokemonAbilityDB.addAbilityToPokemon(pokemonName, abilityName);
    }

    public static void removeAbilityFromPokemon(String pokemonName, String abilityName) {
        boolean hasAbility = PokemonAbilityDB.getPokemonAbilities(pokemonName)
                .stream()
                .anyMatch(ability -> ability.getName().equals(abilityName));
        if (!hasAbility) {
            throw new IllegalArgumentException("Pokemon does not have this ability");
        }
        PokemonAbilityDB.removeAbilityFromPokemon(pokemonName, abilityName);
    }
}
