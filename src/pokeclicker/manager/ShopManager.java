package pokeclicker.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pokeclicker.game.Shop;
import pokeclicker.manager.item.ItemFilter;
import pokeclicker.manager.item.ItemManager;
import pokeclicker.manager.pokemon.*;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

public class ShopManager {
    private ShopManager() {
    }

    public static Shop getShop(User user, Optional<ItemFilter> itemFilterOpt, Optional<PokemonFilter> pokeFilterOpt) {
        ItemFilter itemFilter = itemFilterOpt.orElseGet(ItemFilter::new);
        itemFilter.setAvailable(true);
        itemFilter.setUser(user.getName());

        PokemonFilter pokeFilter = pokeFilterOpt.orElseGet(PokemonFilter::new);
        pokeFilter.setAvailable(true);
        pokeFilter.setUser(user.getName());

        List<Purchasable> purchasables = new ArrayList<>();

        purchasables.addAll(PokemonManager.getAllPokemons(Optional.of(pokeFilter)));
        purchasables.addAll(ItemManager.getAllItems(Optional.of(itemFilter)));

        Shop shop = new Shop(user, purchasables);

        return shop;
    }

    public static void buyPokemonOrItem(User user, Shop shop, Purchasable pokemonOrItem) {
        try {
            Purchasable newPurchasable = shop.buyPokemonOrItem(pokemonOrItem);
            user.spendMoney(newPurchasable.getPrice());
            UserManager.updateUser(user);

            switch (newPurchasable) {
                case Pokemon pokemon -> PokemonManager.updatePokemon(pokemon);
                case Item item -> ItemManager.updateItem(item);
                default -> {
                }
            }
        } catch (Exception e) {
        }
    }
}
