package pokeclicker.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pokeclicker.model.Item;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;

public class UserManager {
    private static List<User> users = new ArrayList<>();
    private static User currentUser;
    private static final String PATH = "src/pokeclicker/users.txt";

    private UserManager() {
    }

    public static User createUser(String name) throws IllegalArgumentException {
        if (userNameExists(name)) {
            throw new IllegalArgumentException("The User name already exists!");
        }
        User newUser = new User(name);
        users.add(newUser);
        currentUser = newUser;
        saveToFile();
        return newUser;
    }

    private static boolean userNameExists(String name) {
        return users.stream()
                .anyMatch(u -> u.getName().equalsIgnoreCase(name));
    }

    private static void saveToFile() {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            // favPokemon
            String favPokemon = (currentUser.getFavPokemon() != null)
                    ? currentUser.getFavPokemon().getName()
                    : "null";
            // pokemons
            String pokemons = (currentUser.getPokemons() != null && !currentUser.getPokemons().isEmpty())
                    ? currentUser.getPokemons().stream().map(Pokemon::getName).collect(Collectors.joining(";"))
                    : "null";
            // items
            String items = (currentUser.getItems() != null && !currentUser.getItems().isEmpty())
                    ? currentUser.getItems().stream().map(Item::getName).collect(Collectors.joining(";"))
                    : "null";
            // money
            double money = currentUser.getMoney();

            String line = String.format("%s,%s,%s,%s,%.2f%n",
                    currentUser.getName(),
                    favPokemon,
                    pokemons,
                    items,
                    money);

            writer.write(line);
        } catch (IOException e) {
            System.err.println("Error on saving users to file: " + e.getMessage());
        }
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public static User getUserByName(String name) {
        return users.stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}