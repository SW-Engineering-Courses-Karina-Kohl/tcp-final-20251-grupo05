package pokeclicker.model;

public class Item {
    private String name;
    private String effect;
    private int price;
    private boolean available;

    public Item(String name, String effect, int price, boolean available) {
        this.name = name;
        this.effect = effect;
        this.price = price;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}