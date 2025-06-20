package pokeclicker.manager.pokemon;

import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.LevelType;

public class PokemonFilter {
    private String name;
    private PokeType type;
    private LevelType minLevel;
    private Integer minHealth;
    private Boolean available;
    private Double maxPrice;
    private Ability ability;
    private String userName;

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

    public Boolean getAvailable() {
        return available;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Ability getAbility() {
        return ability;
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
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

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
}
