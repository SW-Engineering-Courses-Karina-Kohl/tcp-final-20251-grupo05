package pokeclicker.model;

public class Habilidade {
    private String name;
    private String description;
    private String type;
    private double damage;
    private double cure;

    public Habilidade(String name, String description, String type, double damage, double cure) {
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
        return type;
    }

    public double getCure() {
        return cure;
    }

    public double getDamage() {
        return damage;
    }
    
}
