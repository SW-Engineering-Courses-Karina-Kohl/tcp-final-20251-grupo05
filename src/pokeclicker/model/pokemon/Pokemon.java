package pokeclicker.model.pokemon;

import java.util.List;
import pokeclicker.model.Habilidade;
import pokeclicker.model.common.Activatable;
import pokeclicker.model.common.Purchasable;

public abstract class Pokemon implements Activatable, Purchasable {
    private String name;
    private Level level;
    private List<Habilidade> habilities;
    private double xp = 0.0;
    private int health;
    private int totalHealth;
    private boolean captured = false;

    public Pokemon(String name, List<Habilidade> habilities, int totalHealth) {
        this.name = name;
        this.habilities = habilities;
        this.totalHealth = health;
        this.health = totalHealth;
        this.level = new Level();
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level.getCurrentLevel();
    }

    public List<Habilidade> getHabilities() {
        return habilities;
    }

    public double getXp() {
        return xp;
    }

    public int getHealth() {
        return health;
    }

    public boolean isCaptured() {
        return captured;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHabilities(Habilidade hability) {
        this.habilities.add(hability);
    }

    public void gainXp(double xp) {
        if (xp < 0) {
            throw new IllegalArgumentException("XP cannot be negative");
        }
        this.xp += xp;
        this.level.gainXp(xp);
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

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    public boolean isAlive() {
        return health > 0;
    }

}
