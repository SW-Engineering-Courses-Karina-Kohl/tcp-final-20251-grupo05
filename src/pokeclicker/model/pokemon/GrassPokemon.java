package pokeclicker.model.pokemon;

public class GrassPokemon extends Pokemon {

    public GrassPokemon(String name, LevelType level, double xp, int health, int totalHealth,
            boolean captured, double price, String imagePath) {
        super(name, level, xp, health, totalHealth, captured, price, imagePath);
    }

    @Override
    public String getType() {
        return "GRASS";
    }
}
