package pokeclicker.manager;

import java.util.AbstractMap.SimpleEntry;
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

    public static PC getPC(User user, Optional<ItemFilter> itemFilterOpt, Optional<PokemonFilter> pokeFilterOpt) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        ItemFilter itemFilter = itemFilterOpt.orElseGet(ItemFilter::new);
        itemFilter.setAvailable(false);
        itemFilter.setUser(user.getName());

        PokemonFilter pokeFilter = pokeFilterOpt.orElseGet(PokemonFilter::new);
        pokeFilter.setAvailable(false);
        pokeFilter.setUser(user.getName());

        List<Pokemon> pokemons = PokemonManager.getAllPokemons(Optional.of(pokeFilter));
        List<Item> items = ItemManager.getAllItems(Optional.of(itemFilter));

        PC pc = new PC(pokemons, items);

        return pc;
    }

    public static boolean buyXp(PC pc, Pokemon pokemon, User user) {
        if (pc == null || pokemon == null) {
            throw new IllegalArgumentException("PC and Pokemon cannot be null");
        }

        SimpleEntry<Pokemon, Boolean> result = pc.buyXP(pokemon, user);
        Pokemon newPokemon = result.getKey();
        Boolean evolved = result.getValue();

        if(newPokemon.getXp() < 100)
        {
            user.spendMoney(newPokemon.getXp());
        }

        UserManager.updateUser(user);
        PokemonManager.updatePokemon(newPokemon);

        return evolved;
    }

}
