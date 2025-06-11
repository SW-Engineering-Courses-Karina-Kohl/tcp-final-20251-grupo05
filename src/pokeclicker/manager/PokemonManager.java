package pokeclicker.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.FirePokemon;
import pokeclicker.model.pokemon.GrassPokemon;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.model.pokemon.WaterPokemon;

public class PokemonManager {
    private static List<Pokemon> pokemons = new ArrayList<>();
    private static Pokemon currentPokemon;
    private static final String PATH = "src/pokeclicker/pokemons.txt";

    private PokemonManager() {
    }

    public static Pokemon createPokemon(String name, int totalHealth, List<Ability> habilities, double price,
            PokeType type)
            throws IllegalArgumentException {
        if (pokemonNameExists(name)) {
            throw new IllegalArgumentException("The Pokemon name already exists!");
        }

        Pokemon newPokemon;

        if (null == type) {
            throw new IllegalArgumentException("Invalid Pokemon type!");
        } else
            switch (type) {
                case FIRE:
                    newPokemon = new FirePokemon(name, habilities, totalHealth, price, price);
                    break;
                case WATER:
                    newPokemon = new WaterPokemon(name, habilities, totalHealth, price, price);
                    break;
                case GRASS:
                    newPokemon = new GrassPokemon(name, habilities, totalHealth, price, price);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Pokemon type!");
            }

        pokemons.add(newPokemon);
        currentPokemon = newPokemon;
        ShopManager.saveNewToShop(newPokemon);
        ShopManager.saveNewToShop(newPokemon);
        saveToFile();
        return newPokemon;
    }

    private static boolean pokemonNameExists(String name) {
        return pokemons.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }

    private static void saveToFile() {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            String line = String.format("%s,%s,%d,%.2f,%b%n",
                    currentPokemon.getName(),
                    currentPokemon.getLevel(),
                    currentPokemon.getHealth(),
                    currentPokemon.getXp(),
                    currentPokemon.isCaptured());

            writer.write(line);
        } catch (IOException e) {
            System.err.println("Error on saving pokemons to file: " + e.getMessage());
        }
    }

    public static List<Pokemon> getAllPokemons() {
        return new ArrayList<>(pokemons);
    }
}
