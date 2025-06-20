package pokeclicker.manager;

import java.util.List;
import java.util.Optional;
import pokeclicker.game.PC;
import pokeclicker.manager.item.ItemFilter;
import pokeclicker.manager.item.ItemManager;
import pokeclicker.manager.pokemon.PokemonFilter;
import pokeclicker.manager.pokemon.PokemonManager;
import pokeclicker.model.User;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

public class PCManager {
    private PCManager() {
    }

    public static PC getPC(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        ItemFilter itemFilter = new ItemFilter();
        PokemonFilter pokeFilter = new PokemonFilter();

        itemFilter.setAvailable(false);
        itemFilter.setUser(user.getName());
        pokeFilter.setAvailable(false);
        pokeFilter.setUser(user.getName());

        List<Pokemon> pokemons = PokemonManager.getAllPokemons(Optional.of(pokeFilter));
        List<Item> items = (ItemManager.getAllItems(Optional.of(itemFilter)));

        PC pc = new PC(pokemons, items, user.getFavoritePokemon());

        return pc;
    }

}
