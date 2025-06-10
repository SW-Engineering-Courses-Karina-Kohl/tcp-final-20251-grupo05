package pokeclicker.model;

<<<<<<< HEAD
import pokeclicker.model.common.PokeType;

=======
>>>>>>> 71b2d92 (Finalização logica loja)
public class Ability {
    private String name;
    private String description;
    private PokeType type;
    private double damage;
    private double cure;

<<<<<<< HEAD
    public Ability(String name, String description, PokeType type, double damage, double cure) {
=======
    public Ability(String name, String description, String type, double damage, double cure) {
>>>>>>> 71b2d92 (Finalização logica loja)
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

    public PokeType getType() {
        return type;
    }

    public double getCure() {
        return cure;
    }

    public double getDamage() {
        return damage;
    }
<<<<<<< HEAD
=======

>>>>>>> 71b2d92 (Finalização logica loja)
}
