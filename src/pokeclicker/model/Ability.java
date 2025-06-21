package pokeclicker.model;

import pokeclicker.model.common.PokeType;

public class Ability {
    private String name;
    private String description;
    private PokeType type;
    private double damage;
    private double cure;

    public Ability(String name, String description, PokeType type, double damage, double cure) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.damage = damage;
        this.cure = cure;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type.toString();
    }

    public double getCure() {
        return cure;
    }

    public double getDamage() {
        return damage;
    }

    public void updateDamage(double damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }
}
