package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pokeclicker.manager.ShopManager;
import pokeclicker.model.User;
import pokeclicker.model.common.Purchasable;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.game.Shop;

public class ShopController implements Initializable {

    @FXML
    private VBox pokemonListVBox;

    private User currentUser;
    private Shop shop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (currentUser != null) {
            loadShop();

        }
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.shop = ShopManager.getShop(user);
        loadShop();
    }

    private void loadShop() {
        pokemonListVBox.getChildren().clear();

        List<Purchasable> purchasables = shop.getPurchasables();

        for (Purchasable p : purchasables) {
            if (p instanceof Pokemon pokemon) {
                HBox card = createPokemonCard(pokemon);
                pokemonListVBox.getChildren().add(card);
            }
        }
    }

    private HBox createPokemonCard(Pokemon pokemon) {
        HBox card = new HBox();
        card.setSpacing(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: black; -fx-border-radius: 8; -fx-background-color: #f0f0f0;");
        System.out.println("Trying to load image: " + pokemon.getImagePath());
        ImageView imageView = new ImageView(new Image(getClass().getResource(pokemon.getImagePath()).toExternalForm()));
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        VBox info = new VBox(5);
        Label name = new Label("Name: " + pokemon.getName());
        Label health = new Label("HP: " + pokemon.getTotalHealth());
        Label price = new Label("Price: $" + pokemon.getPrice());

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> {
            ShopManager.buyPokemonOrItem(currentUser, shop, pokemon);
            loadShop();
        });

        info.getChildren().addAll(name, health, price, buyButton);
        card.getChildren().addAll(imageView, info);
        return card;
    }
}
