package pokeclicker.model.pokemon;

import java.util.List;
import pokeclicker.model.Ability;

public class FirePokemon extends Pokemon {
    public FirePokemon(String name, List<Ability> habilities, int totalHealth, double price, String imagePath) {
        super(name, habilities, totalHealth, price, imagePath);
    }
}
