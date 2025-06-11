package pokeclicker.model.common;

public enum PokeType {
    GRASS("Grass"),
    WATER("Water"),
    FIRE("Fire");

    private final String type;

    PokeType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static PokeType fromString(String text) {
        for (PokeType type : PokeType.values()) {
            if (type.type.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Pokemon type: " + text);
    }
}
