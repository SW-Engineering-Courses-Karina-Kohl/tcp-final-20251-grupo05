package pokeclicker.model.pokemon;

public class Level {
    private LevelType currentLevel;
    private double currentXp;
    private static final double BEGINNER_MIN_XP = 0;
    private static final double INTERMEDIATE_MIN_XP = 100;
    private static final double ADVANCED_MIN_XP = 300;

    public Level() {
        this.currentLevel = LevelType.BEGINNER;
        this.currentXp = 0;
    }

    public void gainXp(double newXp) {
        this.currentXp = newXp;
        updateLevel();
    }

    private void updateLevel() {
        if (currentXp >= ADVANCED_MIN_XP) {
            currentLevel = LevelType.ADVANCED;
        } else if (currentXp >= INTERMEDIATE_MIN_XP) {
            currentLevel = LevelType.INTERMEDIATE;
        }
    }

    public String getCurrentLevel() {
        switch (currentLevel) {
            case BEGINNER:
                return "Beginner";
            case INTERMEDIATE:
                return "Intermediate";
            case ADVANCED:
                return "Advanced";
            default:
                return "Unknown";
        }
    }

    public double getXpToNextLevel() {
        switch (currentLevel) {
            case BEGINNER:
                return INTERMEDIATE_MIN_XP - currentXp;
            case INTERMEDIATE:
                return ADVANCED_MIN_XP - currentXp;
            case ADVANCED:
                return 0;
            default:
                return 0;
        }
    }
}