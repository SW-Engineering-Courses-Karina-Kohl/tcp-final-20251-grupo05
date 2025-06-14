package pokeclicker.model.pokemon;

public class WaterPokemon extends Pokemon {
    public WaterPokemon(String name, LevelType level, double xp, int health, int totalHealth,
            boolean captured, double price, String imagePath) {
        super(name, level, xp, health, totalHealth, captured, price, imagePath);
    }

    @Override
    public String getType() {
        return "WATER";
    }
}
