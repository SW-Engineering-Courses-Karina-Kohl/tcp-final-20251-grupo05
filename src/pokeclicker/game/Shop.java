package pokeclicker.game;

import java.util.ArrayList;
import java.util.List;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

public class Shop {
    private User user;
    private List<Pokemon> pokemons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private PC pc;

    public Shop(User user) {
        this.user = user;
        this.pc = pc;
    }

    public User getUser() {
        return user;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removePokemon(Pokemon pokemon) {
        if (pokemons.contains(pokemon)) {
            pokemons.remove(pokemon);
        } else {
            throw new IllegalArgumentException("Pokemon not found in the shop.");
        }
    }

    public void removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
        } else {
            throw new IllegalArgumentException("Item not found in the shop.");
        }
    }

    public void buyPokemonOrItem(Purchasable pokemonOrItem) {
        if (pokemonOrItem instanceof Pokemon pokemon) {
            if (user.getMoney() >= pokemon.getPrice()) {
                user.spendMoney(pokemon.getPrice());
                pc.addPokemon(pokemon);
                removePokemon(pokemon);
            } else {
                throw new IllegalArgumentException("Not enough money to buy this Pokemon.");
            }
        } else if (pokemonOrItem instanceof Item item) {
            if (user.getMoney() >= item.getPrice()) {
                user.spendMoney(item.getPrice());
                pc.addItem(item);
                removeItem(item);
            } else {
                throw new IllegalArgumentException("Not enough money to buy this item.");
            }
        } else {
            throw new IllegalArgumentException("Unsupported type: " + pokemonOrItem.getClass());
        }
    }
}
