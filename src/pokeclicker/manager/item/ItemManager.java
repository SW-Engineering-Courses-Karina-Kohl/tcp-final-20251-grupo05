package pokeclicker.manager.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pokeclicker.database.ItemDB;
import pokeclicker.model.item.Item;
import pokeclicker.model.item.ItemType;
import pokeclicker.model.item.MoneyMultiplierItem;
import pokeclicker.model.item.PokemonItem;

public class ItemManager {

    private ItemManager() {
    }

    public static Item createItem(String name, double price, String description, ItemType type,
            double damageOrMultiplier)
            throws IllegalArgumentException {
        if (ItemDB.getItem(name) != null) {
            throw new IllegalArgumentException("This item name already exists");
        }

        Item newItem;
        switch (type) {
            case MONEY_MULTIPLIER -> {
                double multiplier = damageOrMultiplier;
                if (multiplier <= 0) {
                    throw new IllegalArgumentException("Multiplier must be greater than zero");
                }
                newItem = new MoneyMultiplierItem(name, price, description, multiplier);
            }

            case POKEMON -> {
                double damage = damageOrMultiplier;
                if (damage < 0) {
                    throw new IllegalArgumentException("Damage must be non-negative");
                }
                newItem = new PokemonItem(name, price, description, damage);
            }

            default -> throw new IllegalArgumentException("Invalid Item type: " + type);
        }

        ItemDB.insertItem(newItem);
        return newItem;
    }

    public static Item getItem(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        Item item = ItemDB.getItem(name);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + name);
        }
        return item;
    }

    public static List<Item> getAllItems(Optional<ItemFilter> filter) {
        return new ArrayList<>(ItemDB.getAllItems(filter));
    }

    public static void updateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (ItemDB.getItem(item.getName()) == null) {
            throw new IllegalArgumentException("Item not found: " + item.getName());
        }
        ItemDB.updateItem(item);
    }

    public static void deleteItem(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        Item item = ItemDB.getItem(name);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + name);
        }
        ItemDB.deleteItem(name);
    }
}
