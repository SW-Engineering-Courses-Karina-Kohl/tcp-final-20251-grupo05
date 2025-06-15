package pokeclicker.model.pokemon;

public class GrassPokemon extends Pokemon {

    public GrassPokemon(String name, LevelType level, double xp, int health, int totalHealth,
            boolean available, double price, String imagePath) {
        super(name, level, xp, health, totalHealth, available, price, imagePath);
    }

    @Override
    public String getType() {
        return "GRASS";
    }
}
