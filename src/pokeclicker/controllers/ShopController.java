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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;

import pokeclicker.manager.ShopManager;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneSwitcher;
import pokeclicker.model.common.Purchasable;
import pokeclicker.model.item.Item;
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

    // Use uma imagem fixa para itens
    String itemImg;
    String variableLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String username = SceneSwitcher.getCurrentUsername(); // seta o username pro shop controller
        currentUser = UserManager.getUser(username); // carrega o user baseado no username
        currentShop = ShopManager.getShop(currentUser); // carrega a shop pro user

        populateShop(); // serve para inserir pokemons/itens na loja no carregamento (utiliza o método
                        // que cria os cards)
        updateMoneyLabel(); // atualiza a label do dinheiro do usuario
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
        shopDisplayBox.getChildren().clear(); // limpa a loja

        List<Purchasable> purchasables = currentShop.getPurchasables().stream()
                .filter(Purchasable::isAvailable) // filtra pokemons disponiveis
                .collect(Collectors.toList());

        for (Purchasable purchasable : purchasables) { // se for compravel, insere na loja
            if (purchasable instanceof Pokemon pokemon) {
                Node card = createPokemonCard(pokemon);
                shopDisplayBox.getChildren().add(card);
            }
            if (purchasable instanceof Item item) {
                Node card = createItemCard(item);
                shopDisplayBox.getChildren().add(card);
            }
        }
    }

    private HBox createPokemonCard(Pokemon pokemon) {
        HBox card = new HBox(); // hbox é um container que separa itens horizontalmente
        card.setSpacing(15);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8;");

        ImageView imageView = new ImageView(); // cria um novo image view direto do controller
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        try {
            URL imageUrl = getClass().getResource(pokemon.getImagePath()); // pega a imagem de cada pokemon
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                System.err.println("Image not found: " + pokemon.getImagePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox info = new VBox();
        info.setSpacing(5);

        Label nameLabel = new Label("Name: " + pokemon.getName());

        TextFlow typeLabelFlow = new TextFlow(); // textflow serve pra definir cores diferentes em partes diferentes da
                                                 // mesma label

        Text typePrefix = new Text("Type: ");
        typePrefix.setFill(Color.web("#2c3e50"));
        Text typeValue = new Text(pokemon.getType());

        switch (pokemon.getType().toUpperCase()) { // estiliza o tipo na loja
            case "FIRE" -> typeValue.setFill(Color.RED);
            case "WATER" -> typeValue.setFill(Color.BLUE);
            case "GRASS" -> typeValue.setFill(Color.GREEN);
            default -> typeValue.setFill(Color.BLACK);
        }

        typeValue.setStyle("-fx-font-weight: bold;");
        typeLabelFlow.getChildren().addAll(typePrefix, typeValue); // adiciona os casos diferentes no textflow

        Label healthLabel = new Label("Health: " + pokemon.getTotalHealth()); // cria as labels dinamicamente
        Label priceLabel = new Label(String.format("Price: $%.2f", pokemon.getPrice()));

        info.getChildren().addAll(nameLabel, typeLabelFlow, healthLabel, priceLabel);

        Button buyButton = new Button("Buy"); // cria o botão
        buyButton.setOnAction(event -> {
            if (currentUser.getMoney() >= pokemon.getPrice()) { /*
                                                                 * verifica se o usuario tem dinheiro para comprar o
                                                                 * pokémon
                                                                 */

                try {
                    ShopManager.buyPokemonOrItem(currentUser, currentShop, pokemon);// se ele tem dinheiro, compra
                    shopDisplayBox.getChildren().remove(card); // remove o pokemon da shop
                    updateMoneyLabel(); // atualiza a label do dinheiro
                    System.out.println("Bought: " + pokemon.getName());
                } catch (Exception e) {
                    System.err.println("Error buying Pokemon: " + e.getMessage());
                }
            } else {
                System.out.println("Not enough money to buy " + pokemon.getName());
                updateCantAffordLabel();
            }
        });

        card.getChildren().addAll(imageView, info, buyButton); // adiciona imagem, informação e o botão ao card criado
        return card; // retorna o cartão pra ser inserido na scene no initialize
    }

    /*
     * o item é adicionado de forma similar ao pokemon, apenas diferencia na imagem,
     * que é definida pelo tipo.
     */
    private HBox createItemCard(Item item) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        if (item.getType().toString().equals("Money Multiplier")) { /*
                                                                     * verifica se o item tem tipo multiplier, ou
                                                                     * pokemon
                                                                     */
            itemImg = "/img/amuletcoin.png";
            variableLabel = "Multiplier: ";
        }
        if (item.getType().toString().equals("Pokemon")) {
            itemImg = "/img/potion.png";
            variableLabel = "Damage: ";
        }
        try {
            URL imageUrl = getClass().getResource(itemImg);
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                System.err.println("Default item image not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox info = new VBox();
        info.setSpacing(5);
        Label nameLabel = new Label("Name: " + item.getName());
        Label typeLabel = new Label("Type: " + item.getType());
        Label damageOrMultiplierLabel = new Label(variableLabel + item.getMultiplierOrDamage());
        Label descriptionLabel = new Label("Description: " + item.getDescription());
        Label priceLabel = new Label(String.format("Price: $%.2f", item.getPrice()));

        info.getChildren().addAll(nameLabel, typeLabel, damageOrMultiplierLabel, descriptionLabel, priceLabel);

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(event -> {
            if (currentUser.getMoney() >= item.getPrice()) {
                try {
                    ShopManager.buyPokemonOrItem(currentUser, currentShop, item);
                    shopDisplayBox.getChildren().remove(card);
                    updateMoneyLabel();
                    System.out.println("Bought: " + item.getName());
                } catch (Exception e) {
                    System.err.println("Error buying Item: " + e.getMessage());
                }
            } else {
                System.out.println("Not enough money to buy " + item.getName());
                updateCantAffordLabel();
            }
        });

        card.getChildren().addAll(imageView, info, buyButton);
        return card;
    }

    private void updateMoneyLabel() {
        moneyLabel.setText(String.format("Money: $%.2f", currentUser.getMoney()));
    }

    private void updateCantAffordLabel() {
        moneyLabel.setText(String.format("Money: $%.2f - Can't afford!", currentUser.getMoney()));
    }

    @FXML
    private void handleBackToHome(ActionEvent event) { // botão de retorno a home
        SceneSwitcher.switchToHome(event, currentUser.getName());
    }
}