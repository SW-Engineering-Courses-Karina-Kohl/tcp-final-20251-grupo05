package pokeclicker.manager.pokemon;

import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.LevelType;

public class PokemonFilter {
    private String name;
    private PokeType type;
    private LevelType minLevel;
    private Integer minHealth;
    private Boolean captured;
    private Double maxPrice;
    private Ability ability;

    public PokemonFilter() {
    }

    public String getName() {
        return name;
    }

    public PokeType getType() {
        return type;
    }

    public LevelType getMinLevel() {
        return minLevel;
    }

    public Integer getMinHealth() {
        return minHealth;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PokeType type) {
        this.type = type;
    }

    public void setMinLevel(LevelType minLevel) {
        this.minLevel = minLevel;
    }

    public void setMinHealth(Integer minHealth) {
        this.minHealth = minHealth;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
}
