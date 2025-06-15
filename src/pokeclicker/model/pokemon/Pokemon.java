package pokeclicker.model.pokemon;

import java.util.List;
import pokeclicker.model.Ability;
import pokeclicker.model.common.Activatable;
import pokeclicker.model.common.Purchasable;

public abstract class Pokemon implements Activatable, Purchasable {
    private String name;
    private LevelType level;
    private List<Ability> abilities;
    private double xp;
    private int health;
    private int totalHealth;
    private boolean available = true;
    private double price;
    private String imagePath;
    private static final double INTERMEDIATE_MIN_XP = 100;
    private static final double ADVANCED_MIN_XP = 300;

    public Pokemon(String name, LevelType level, double xp, int health, int totalHealth,
            boolean available, double price, String imagePath) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (totalHealth <= 0) {
            throw new IllegalArgumentException("Total health must be greater than zero");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.name = name;
        this.totalHealth = health;
        this.health = totalHealth;
        this.level = level;
        this.price = price;
        this.imagePath = imagePath;
        this.xp = xp;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public LevelType getLevel() {
        return level;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public double getXp() {
        return xp;
    }

    public int getHealth() {
        return health;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public void gainXp(double xp) {
        if (xp < 0) {
            throw new IllegalArgumentException("XP cannot be negative");
        }
        this.xp += xp;
        updateLevel();
    }

    private void updateLevel() {
        if (xp >= ADVANCED_MIN_XP) {
            level = LevelType.ADVANCED;
        } else if (xp >= INTERMEDIATE_MIN_XP) {
            level = LevelType.INTERMEDIATE;
        }
    }

    public void gainHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health += health;
        if (this.health > totalHealth) {
            this.health = totalHealth;
        }
    }

    public void loseHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health -= health;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @Override
    public void setAvailable(boolean captured) {
        this.available = captured;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract String getType();
}
