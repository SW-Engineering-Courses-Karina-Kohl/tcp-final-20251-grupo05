package pokeclicker.model.common;

public interface Purchasable {
    boolean isAvailable();

    void setAvailable(boolean available);

    double getPrice();
}
