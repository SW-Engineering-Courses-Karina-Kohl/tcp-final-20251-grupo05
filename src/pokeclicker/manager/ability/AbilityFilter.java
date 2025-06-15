package pokeclicker.manager.ability;

import pokeclicker.model.common.PokeType;

public class AbilityFilter {
    private PokeType type;
    private Double minDamage;
    private Double maxDamage;
    private Double minCure;
    private Double maxCure;

    public AbilityFilter() {
    }

    public PokeType getType() {
        return type;
    }

    public Double getMinDamage() {
        return minDamage;
    }

    public Double getMaxDamage() {
        return maxDamage;
    }

    public Double getMinCure() {
        return minCure;
    }

    public Double getMaxCure() {
        return maxCure;
    }

    public void setType(PokeType type) {
        this.type = type;
    }

    public void setMinDamage(Double minDamage) {
        this.minDamage = minDamage;
    }

    public void setMaxDamage(Double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void setMinCure(Double minCure) {
        this.minCure = minCure;
    }

    public void setMaxCure(Double maxCure) {
        this.maxCure = maxCure;
    }
}