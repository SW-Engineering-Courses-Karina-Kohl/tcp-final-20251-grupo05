package pokeclicker.model.pokemon;

public class FirePokemon extends Pokemon {
    public FirePokemon(String name, LevelType level, double xp, int health, int totalHealth,
            boolean available, double price, String imagePath) {
        super(name, level, xp, health, totalHealth, available, price, imagePath);
    }

    @Override
    public String getType() {
        return "FIRE";
    }
}
