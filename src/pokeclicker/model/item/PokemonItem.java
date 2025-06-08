package pokeclicker.model.item;

import pokeclicker.model.Habilidade;
import pokeclicker.model.common.Activatable;
import pokeclicker.model.pokemon.Pokemon;

public class PokemonItem extends Item {

    private String pokemonName;

    public PokemonItem(String name, int price, String description, String pokemonName) {
        super(name, price, description);
        this.pokemonName = pokemonName;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    @Override
    public Activatable activate(Activatable activatable) {
        if (activatable == null) {
            throw new IllegalArgumentException("Activatable cannot be null");
        }
        if (!(activatable instanceof Pokemon)) {
            throw new IllegalArgumentException("This item can only be used on Users");
        }

        for (Habilidade habilidade : ((Pokemon) activatable).getHabilities()) {
            // Aumentar o dano da habilidade
        }

        return activatable;
    }

}
