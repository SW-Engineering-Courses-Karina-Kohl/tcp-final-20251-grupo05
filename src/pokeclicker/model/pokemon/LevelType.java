
package pokeclicker.model.pokemon;

public enum LevelType {
    BEGINNER("Beginner"),
    ADVANCED("Advanced");

    private final String level;

    LevelType(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return level;
    }

    public static LevelType fromString(String text) {
        for (LevelType type : LevelType.values()) {
            if (type.level.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Level type: " + text);
    }

    public static LevelType fromInt(int number) {
        return switch (number) {
            case 1 -> BEGINNER;
            case 2 -> ADVANCED;
            default -> throw new IllegalArgumentException("Invalid level number: " + number);
        };
    }

    public int toInt() {
        return switch (this) {
            case BEGINNER -> 1;
            case ADVANCED -> 2;
        };
    }
}
