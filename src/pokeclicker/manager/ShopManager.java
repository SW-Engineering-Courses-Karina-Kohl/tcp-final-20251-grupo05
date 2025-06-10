package pokeclicker.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import pokeclicker.game.Shop;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

public class ShopManager {
    private static List<Shop> shops;
    private static final String PATH = "src/pokeclicker/shops.txt";
    private static Shop currentShop;

    private ShopManager() {
    }

    public static Shop createShopForUser(User user) {
        Shop shop = new Shop(user);
        shops.add(shop);
        currentShop = shop;
        saveToFile();

        return shop;
    }

    public static Purchasable saveNewToShop(Purchasable pokemonOrItem) {
        if (currentShop == null) {
            throw new IllegalStateException("No current shop available.");
        }
        switch (pokemonOrItem) {
            case null -> throw new IllegalArgumentException("Cannot save a null item or pokemon.");
            case Item item -> currentShop.addItem(item);
            case Pokemon pokemon -> currentShop.addPokemon(pokemon);
            default -> throw new IllegalArgumentException("Unsupported type: " + pokemonOrItem.getClass());
        }
        saveToFile();

        return pokemonOrItem;
    }

    public void buyPokemonOrItem(Purchasable pokemonOrItem) {
        if (currentShop == null) {
            throw new IllegalStateException("No current shop available.");
        }
        currentShop.buyPokemonOrItem(pokemonOrItem);
        saveToFile();
    }

    private static void saveToFile() {
        for (Shop shop : shops) {
            try (FileWriter writer = new FileWriter(PATH, true)) {
                String line = String.format("%s,",
                        shop.getUser().getName());

                if (!shop.getPokemons().isEmpty()) {
                    for (Pokemon pokemon : shop.getPokemons()) {
                        line += String.format("Pokemon: %s Price: %.2f ", pokemon.getName(), pokemon.getPrice());
                    }
                    line += ",";
                }
                if (!shop.getItems().isEmpty()) {
                    for (Item item : shop.getItems()) {
                        line += String.format("Item: %s, Price: %.2f ", item.getName(), item.getPrice());
                    }
                }
                line += "\n";
                writer.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
