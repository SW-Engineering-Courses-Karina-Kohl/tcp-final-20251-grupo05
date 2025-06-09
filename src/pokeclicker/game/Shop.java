package pokeclicker.game;

import java.util.ArrayList;
import java.util.List;
import pokeclicker.model.User;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

public class Shop {
    private User user;
    private List<Pokemon> pokemons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Shop(User user) {
        this.user = user;
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

}
