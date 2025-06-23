package pokeclicker.model;

import pokeclicker.model.common.Activatable;
import pokeclicker.model.pokemon.Pokemon;

public class User
        implements Activatable {
    private String name;
    private double money = 0.0;
    private double moneyMultiplier = 1.0;
    private Pokemon favoritePokemon;

    public User(String name, double moneyMultiplier, double money, Pokemon favoritePokemon) {
        this.name = name;
        this.money = money;
        this.moneyMultiplier = moneyMultiplier;
        this.favoritePokemon = favoritePokemon;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public double getMoneyMultiplier() {
        return moneyMultiplier;
    }

    public Pokemon getFavoritePokemon() {
        return favoritePokemon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.money = money;
    }

    public void setFavoritePokemon(Pokemon favoritePokemon) {
        if (favoritePokemon == null) {
            throw new IllegalArgumentException("Favorite Pokemon cannot be null");
        }
        this.favoritePokemon = favoritePokemon;
    }

    public void updateMultiplier(double multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be greater than zero");
        }
        this.moneyMultiplier *= multiplier;
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
