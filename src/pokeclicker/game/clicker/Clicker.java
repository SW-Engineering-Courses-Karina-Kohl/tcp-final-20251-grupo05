package pokeclicker.game.clicker;

import pokeclicker.model.User;

public class Clicker {
    // constante para debug: public static final double MULTIPLIER = 1.2;
    private int totalMoney = 0;
    private int moneyPerClick = 1;
    private final User user;
    private int totalClicks = 0;
    private static int totalGlobalClicks = 0;

    public Clicker(User user) {
        this.user = user;
    }

    public int getMoneyPerClick() {
        return moneyPerClick;
    }

    public void setMoneyPerClick(int moneyPerClick) {
        this.moneyPerClick = moneyPerClick;
    }

    public User getUser() {
        return user;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public static int getTotalGlobalClicks() {
        return totalGlobalClicks;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public User registerClick() {
        totalClicks++;
        totalGlobalClicks++;
        double earned = moneyPerClick * user.getMoneyMultiplier();
        totalMoney += earned;
        user.earnMoney(earned);
        return user;
    }

    public void resetClicks() {
        totalClicks = 0;
        totalMoney = 0;
    }
}
