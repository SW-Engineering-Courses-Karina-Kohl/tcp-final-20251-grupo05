package pokeclicker.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pokeclicker.model.item.Item;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.item.MoneyMultiplierItem;
import pokeclicker.model.item.PokemonItem;

public class ItemManager {
    private static List<Item> items = new ArrayList<>();
    private static Item currentItem;
    private static final String PATH = "src/pokeclicker/items.txt";

    private ItemManager() {
    }

    public static Item createItem(String name, int price, String description, ItemType type, double damageOrMultiplier)
            throws IllegalArgumentException {
        if (itemNameExists(name)) {
            throw new IllegalArgumentException("This item name already exists");
        }

        Item newItem;
        switch (type) {
            case MONEY_MULTIPLIER:
                double multiplier = damageOrMultiplier;
                if (multiplier <= 0) {
                    throw new IllegalArgumentException("Multiplier must be greater than zero");
                }
                newItem = new MoneyMultiplierItem(name, price, description, multiplier);
                break;

            case POKEMON:
                double damage = damageOrMultiplier;
                if (damage < 0) {
                    throw new IllegalArgumentException("Damage must be non-negative");
                }
                newItem = new PokemonItem(name, price, description, damage);
                break;

            default:
                throw new IllegalArgumentException("Invalid Item type: " + type);
        }

        items.add(newItem);
        currentItem = newItem;
        ShopManager.saveNewToShop(newItem);
        saveToFile();
        return newItem;
    }

    private static boolean itemNameExists(String name) {
        return items.stream()
                .anyMatch(i -> i.getName().equalsIgnoreCase(name));
    }

    private static void saveToFile() {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            String line = String.format("%s,%d,%s,%b,%f%n",
                    currentItem.getName(),
                    currentItem.getPrice(),
                    currentItem.getDescription(),
                    currentItem.isAvailable(),
                    currentItem instanceof MoneyMultiplierItem
                            ? ((MoneyMultiplierItem) currentItem).getMultiplier()
                            : ((PokemonItem) currentItem).getDamage());

            writer.write(line);
        } catch (IOException e) {
            System.err.println("Error on saving items to file: " + e.getMessage());
        }
    }

    public static List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public static List<Item> getAvailableItems() {
        return items.stream()
                .filter(Item::isAvailable)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
