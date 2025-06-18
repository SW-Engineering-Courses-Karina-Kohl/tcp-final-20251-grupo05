package pokeclicker.manager;

import pokeclicker.game.clicker.Clicker;
import pokeclicker.model.User;

public class ClickerManager {
    private ClickerManager() {

    }

    public void click(User user) {
        Clicker clicker = new Clicker(user);
        User updatedUser = clicker.registerClick();

        UserManager.updateUser(updatedUser);
    }

}
