package pokeclicker.model.pokemon;

import java.util.List;
import pokeclicker.model.Ability;

public class WaterPokemon extends Pokemon {
    public WaterPokemon(String name, List<Ability> habilities, int totalHealth, double price, String imagePath) {
        super(name, habilities, totalHealth, price, imagePath);
    }
}
