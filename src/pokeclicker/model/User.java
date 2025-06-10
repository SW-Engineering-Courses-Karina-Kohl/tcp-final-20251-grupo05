package pokeclicker.model;

import pokeclicker.model.common.Activatable;
import pokeclicker.model.pokemon.Pokemon;

public class User
        implements Activatable {
    private String name;
    private Pokemon favPokemon = null;
    private double money = 0.0;
    private double moneyMultiplier = 1.0;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Pokemon getFavPokemon() {
        return favPokemon;
    }

    public double getMoney() {
        return money;
    }

    public double getMoneyMultiplier() {
        return moneyMultiplier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavPokemon(Pokemon favPokemon) {
        this.favPokemon = favPokemon;
    }

    public void updateMultiplier(double multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be greater than zero");
        }
        this.moneyMultiplier += multiplier;
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
        this.money += money * moneyMultiplier;
    }
}
