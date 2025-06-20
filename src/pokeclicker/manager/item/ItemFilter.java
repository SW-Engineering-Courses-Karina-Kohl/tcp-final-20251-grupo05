package pokeclicker.manager.item;

import pokeclicker.model.item.ItemType;

public class ItemFilter {
    private ItemType type;
    private Double minPrice;
    private Double maxPrice;
    private String nameContains;
    private Boolean available;
    private String descriptionContains;
    private String user;

    public ItemFilter() {
    }

    public ItemType getType() {
        return type;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public String getNameContains() {
        return nameContains;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getDescriptionContains() {
        return descriptionContains;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setNameContains(String nameContains) {
        this.nameContains = nameContains;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setDescriptionContains(String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }

}
