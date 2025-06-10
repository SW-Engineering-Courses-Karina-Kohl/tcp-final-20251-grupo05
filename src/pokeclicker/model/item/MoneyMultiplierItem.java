package pokeclicker.model.item;

import pokeclicker.model.User;
import pokeclicker.model.common.Activatable;

public class MoneyMultiplierItem extends Item {

    private double multiplier;

    public MoneyMultiplierItem(String name, double price, String description, double multiplier) {
        super(name, price, description);
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public Activatable activate(Activatable activatable) {
        if (activatable == null) {
            throw new IllegalArgumentException("Activatable cannot be null");
        }
        if (!(activatable instanceof User)) {
            throw new IllegalArgumentException("Este item só pode ser usado em Users");
        }

        User user = (User) activatable;
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be greater than zero");
        }

        user.updateMultiplier(multiplier);
        return user;
    }

}
