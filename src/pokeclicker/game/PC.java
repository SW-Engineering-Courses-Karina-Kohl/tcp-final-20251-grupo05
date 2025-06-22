package pokeclicker.game;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pokeclicker.model.User;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;

/*
Na MAIN é preciso que isso ocorra para que o PC e o User funcione de maneira correta.
    User novoUsuario = new User("NomeDoJogador");
    PC pcDoNovoUsuario = new PC();
*/

public class PC {
    private final List<Pokemon> pokemons;
    private final Map<Item, Integer> itemQuantities;

    public PC(List<Pokemon> pokemons, List<Item> items) {
        this.pokemons = new ArrayList<>(pokemons);
        this.itemQuantities = new HashMap<>();
        for (Item item : items) {
            this.itemQuantities.put(item, 0);
        }
    }

    public List<Pokemon> getPokemons() {
        return new ArrayList<>(pokemons);
    }

    public Map<Item, Integer> getItemQuantities() {
        return new HashMap<>(itemQuantities);
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }

    public void addItem(Item item) {
        this.itemQuantities.put(item, this.itemQuantities.getOrDefault(item, 0) + 1);
    }

    public void removeItem(Item item, int amountToRemove) {
        if (!itemQuantities.containsKey(item)) {
            throw new IllegalArgumentException("Item " + item.getName() + " not found.");
        }

        int currentAmount = itemQuantities.get(item);
        if (currentAmount < amountToRemove) {
            throw new IllegalArgumentException("Not enough available to remove: " + item.getName());
        }

        int newAmount = currentAmount - amountToRemove;
        if (newAmount == 0) {
            itemQuantities.remove(item);
        } else {
            itemQuantities.put(item, newAmount);
        }
    }

    public int getItemCount(Item item) {
        return this.itemQuantities.getOrDefault(item, 0);
    }

    public SimpleEntry<Pokemon, Boolean> buyXP(Pokemon pokemon, User user) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null");
        }
        if (!pokemons.contains(pokemon)) {
            throw new IllegalArgumentException("Pokemon is not yours!");
        }
        if (user.getMoney() >= pokemon.getXp() + 1) {
            Boolean evolved = pokemon.gainXp(1);
            return new SimpleEntry<>(pokemon, evolved);
        } else {
            throw new IllegalArgumentException("Not enough money to buy XP.");
        }
    }

}