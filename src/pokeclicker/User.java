package pokeclicker;

import java.util.List;

public class User {
    private String name;
    private Pokemon favPokemon = null;
    private List<Pokemon> pokemons = null;
    private List<Item> items = null;
    private double money = 0.0;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Pokemon getFavPokemon() {
        return favPokemon;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavPokemon(Pokemon favPokemon) {
        this.favPokemon = favPokemon;
    }

    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    public void addItems(Item item) {
        this.items.add(item);
    }

    public void spendMoney(double money) {
        if (this.money < money) {
            throw new IllegalArgumentException("Not enough money to spend");
        }
        if (money < 0) {
            throw new IllegalArgumentException("Cannot spend negative money");
        }
        this.money -= money;
    }

    public void earnMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Cannot earn negative money");
        }
        this.money += money;
    }
}
