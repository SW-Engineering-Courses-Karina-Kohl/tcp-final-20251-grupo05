package pokeclicker.model.item;

import pokeclicker.model.Ability;
import pokeclicker.model.common.Activatable;
import pokeclicker.model.pokemon.Pokemon;

public class PokemonItem extends Item {

    private double damage;

    public PokemonItem(String name, double price, String description, double damage) {
        super(name, price, description);
        this.damage = damage;
    }

    @Override
    public double getMultiplierOrDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public Activatable activate(Activatable activatable) {
        if (activatable == null) {
            throw new IllegalArgumentException("Activatable cannot be null");
        }
        if (!(activatable instanceof Pokemon)) {
            throw new IllegalArgumentException("This item can only be used on Pokemons");
        }

        for (Ability ability : ((Pokemon) activatable).getAbilities()) {
            ability.updateDamage(damage);
        }

        return activatable;
    }

    @Override
    public ItemType getType() {
        return ItemType.POKEMON;
    }

}
