package pokeclicker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import pokeclicker.manager.ShopManager;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneSwitcher;
import pokeclicker.model.common.Purchasable;
import pokeclicker.game.Shop;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShopController implements Initializable {
    @FXML
    private Label moneyLabel;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox shopDisplayBox;

    private User currentUser;
    private Shop currentShop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String username = pokeclicker.util.SceneSwitcher.getCurrentUsername();
        currentUser = UserManager.getUser(username);
        currentShop = ShopManager.getShop(currentUser);

        populateShop();
        updateMoneyLabel();
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.currentShop = ShopManager.getShop(user);
        populateShop();
    }

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private void populateShop() {
        shopDisplayBox.getChildren().clear();

        List<Purchasable> purchasables = currentShop.getPurchasables().stream()
                .filter(Purchasable::isAvailable)
                .collect(Collectors.toList());

        for (Purchasable item : purchasables) {
            if (item instanceof Pokemon pokemon) {
                Node card = createPokemonCard(pokemon);
                shopDisplayBox.getChildren().add(card);
            }
        }
    }

    // creates hbox to store pokemon image in card
    private HBox createPokemonCard(Pokemon pokemon) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        try {
            URL imageUrl = getClass().getResource(pokemon.getImagePath());
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                System.err.println("Image not found: " + pokemon.getImagePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // creates vbox to display pokemon info
        VBox info = new VBox();
        info.setSpacing(5);
        Label nameLabel = new Label("Name: " + pokemon.getName());
        Label typeLabel = new Label("Type: " + pokemon.getType());
        Label healthLabel = new Label("Health: " + pokemon.getTotalHealth());
        Label priceLabel = new Label(String.format("Price: $%.2f", pokemon.getPrice()));

        info.getChildren().addAll(nameLabel, typeLabel, healthLabel, priceLabel);

        // Buy Button
        Button buyButton = new Button("Buy");
        buyButton.setOnAction(event -> {
            if (currentUser.getMoney() >= pokemon.getPrice()) { // checks if user has enough money to buy
                try {
                    ShopManager.buyPokemonOrItem(currentUser, currentShop, pokemon); // uses back method to buy pokemon
                    shopDisplayBox.getChildren().remove(card); // removes the pokemon from the shop
                    updateMoneyLabel();
                    System.out.println("Bought: " + pokemon.getName());
                } catch (Exception e) {
                    System.err.println("Error buying Pokemon: " + e.getMessage());
                }
            } else {
                System.out.println("Not enough money to buy " + pokemon.getName());
            }
        });

        card.getChildren().addAll(imageView, info, buyButton);
        return card;
    }

    private void updateMoneyLabel() {
        moneyLabel.setText(String.format("Money: $%.2f", currentUser.getMoney()));
    }

    @FXML
    private void handleBackToHome(ActionEvent event) {
        SceneSwitcher.switchToHome(event, currentUser.getName());
    }

}
