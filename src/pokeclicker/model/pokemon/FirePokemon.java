package pokeclicker.model.pokemon;

public class FirePokemon extends Pokemon {
    public FirePokemon(String name, LevelType level, double xp, int health, int totalHealth,
            boolean captured, double price, String imagePath) {
        super(name, level, xp, health, totalHealth, captured, price, imagePath);
    }

    @Override
    public String getType() {
        return "FIRE";
    }
}
