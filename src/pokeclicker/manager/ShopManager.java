package pokeclicker.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        updateShopFile(pokemonOrItem);

        return pokemonOrItem;
    }

    private static void saveToFile() {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            String line = String.format("%s,,%n",
                    currentShop.getUser().getName());
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateShopFile(Purchasable pokemonOrItem) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PATH));

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(currentShop.getUser().getName())) {
                    StringBuilder newLine = new StringBuilder(parts[0]);

                    if (pokemonOrItem instanceof Pokemon pokemon) {
                        newLine.append(",").append(pokemon.getName());
                        if (parts.length > 2) {
                            newLine.append(",").append(parts[2]);
                        } else {
                            newLine.append(",");
                        }
                    } else {
                        if (parts.length > 1) {
                            newLine.append(",").append(parts[1]);
                        } else {
                            newLine.append(",");
                        }
                        Item item = (Item) pokemonOrItem;
                        newLine.append(",").append(item.getName());
                    }

                    lines.set(i, newLine.toString());
                    break;
                }
            }

            Files.write(Paths.get(PATH), lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
