package pokeclicker.model.item;

import pokeclicker.model.common.Activatable;
import pokeclicker.model.common.Purchasable;

public abstract class Item implements Purchasable {
    private String name;
    private int price;
    private boolean available = true;
    private String description;

    public Item(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
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

    public abstract Activatable activate(Activatable activatable);
}
