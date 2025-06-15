package pokeclicker.model.item;

public enum ItemType {
    MONEY_MULTIPLIER("Money Multiplier"),
    POKEMON("Pokemon");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static ItemType fromString(String text) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.type.equalsIgnoreCase(text)) {
                return itemType;
            }
        }
        throw new IllegalArgumentException("Invalid Item type: " + text);
    }
}
