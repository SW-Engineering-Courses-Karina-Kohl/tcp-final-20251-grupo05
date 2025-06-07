
package pokeclicker.model.pokemon;

public enum LevelType {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
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
}
