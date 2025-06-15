package pokeclicker.model.item;

import pokeclicker.model.common.Activatable;
import pokeclicker.model.common.Purchasable;

public abstract class Item implements Purchasable {
    private String name;
    private double price;
    private boolean available = true;
    private String description;

    public Item(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract ItemType getType();

    public abstract double getMultiplierOrDamage();

    public abstract Activatable activate(Activatable activatable);
}
