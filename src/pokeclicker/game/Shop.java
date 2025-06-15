package pokeclicker.game;

import java.util.ArrayList;
import java.util.List;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;

public class Shop {
    private User user;
    private List<Purchasable> purchasables = new ArrayList<>();

    public Shop(User user, List<Purchasable> purchasables) {
        this.user = user;
        this.purchasables = purchasables;
    }

    public User getUser() {
        return user;
    }

    public List<Purchasable> getPurchasables() {
        return purchasables;
    }

    public Purchasable buyPokemonOrItem(Purchasable purchasable) {
        if (!purchasables.contains(purchasable)) {
            throw new IllegalArgumentException("Item not found in the shop.");
        }
        if (user.getMoney() >= purchasable.getPrice()) {
            purchasable.setAvailable(false);
            return purchasable;
        } else {
            throw new IllegalArgumentException("Not enough money to buy this item.");
        }
    }
}
