package pokeclicker.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.Node;
import pokeclicker.manager.PCManager;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import pokeclicker.game.PC;
import pokeclicker.manager.item.ItemManager;

public class PcController implements Initializable {
    @FXML
    private ImageView pokeballimg;
    @FXML
    private ImageView homeimg;
    @FXML
    private ImageView profileimg;
    @FXML
    private ImageView shopimg;

    @FXML
    private Rectangle PCrectangle;
    @FXML
    private Rectangle shoprectangle;
    @FXML
    private Rectangle homerectangle;
    @FXML
    private Rectangle profilerectangle;

    @FXML
    TabPane pcTabPane;

    @FXML
    private Line longline;
    @FXML
    private Line shortline1;
    @FXML
    private Line shortline2;
    @FXML
    private Line shortline3;

    @FXML
    private ScrollPane pcPokemonSP;

    @FXML
    private ScrollPane pcItemSP;
    @FXML
    private Button PC;
    @FXML
    private Button shop;
    @FXML
    private Button home;
    @FXML
    private Button profile;

    // Popup FXML fields
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private VBox popupContent;
    @FXML
    private ImageView popupImage;
    @FXML
    private Label popupName;
    @FXML
    private Label popupType;
    @FXML
    private Label popupHealth;
    @FXML
    private Label popupXP;
    private Pokemon currentPopupPokemon;
    @FXML
    private Label popupXpPrice;
    @FXML
    private Button buyXpButton;
    @FXML
    private Label popupAbility;

    private PC pc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneIconUtil.setupSelectionBarImages(pokeballimg, homeimg, profileimg, shopimg);
        PCrectangle.setFill(javafx.scene.paint.Color.RED);

        pc = PCManager.getPC(UserManager.getUser(SceneSwitcher.getCurrentUsername()), Optional.empty(),
                Optional.empty());

        pcPokemonSP.setContent(createPokemonGrid(pc.getPokemons().toArray(new Pokemon[0])));
        pcItemSP.setContent(createItemGrid(pc.getItemQuantities()));

    }

    private HBox createPokemonCard(Pokemon pokemon) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setStyle(
                "-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8; -fx-font-size: 16px;");
        card.setPrefSize(270, 120);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
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

        VBox info = new VBox();
        info.setSpacing(5);

        Label nameLabel = new Label("Name: " + pokemon.getName());
        nameLabel.setWrapText(true);

        // Colored type display
        TextFlow typeLabelFlow = new TextFlow();
        Text typePrefix = new Text("Type: ");
        typePrefix.setFill(Color.web("#2c3e50"));
        Text typeValue = new Text(pokemon.getType());

        switch (pokemon.getType().toUpperCase()) {
            case "FIRE" -> typeValue.setFill(Color.RED);
            case "WATER" -> typeValue.setFill(Color.BLUE);
            case "GRASS" -> typeValue.setFill(Color.GREEN);
            default -> typeValue.setFill(Color.BLACK);
        }

        typeValue.setStyle("-fx-font-weight: bold;");
        typeLabelFlow.getChildren().addAll(typePrefix, typeValue);

        Label healthLabel = new Label("Health: " + pokemon.getTotalHealth());
        healthLabel.setWrapText(true);

        Label abilityLabel = new Label("Ability: " + pokemon.getAbilities());

        Label xpLabel = new Label("XP: " + pokemon.getXp());

        // Add Details button
        Button detailsButton = new Button("Details");
        detailsButton.setOnAction(e -> {
            System.out.println("Details clicked for: " + pokemon.getName());
            PokemonPopup(pokemon);
        });

        HBox.setHgrow(info, Priority.ALWAYS);
        info.getChildren().addAll(nameLabel, typeLabelFlow, healthLabel, abilityLabel, xpLabel, detailsButton);

        card.getChildren().addAll(imageView, info);
        return card;
    }

    private GridPane createPokemonGrid(Pokemon[] pokemonArray) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 3);
            column.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(column);
        }
        int numRows = Math.ceilDiv(pokemonArray.length, 3);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < 3; col++) {
                if (pokemonArray.length == (row * 3) + col) {
                    break;
                } else {
                    Node pokemonCard = createPokemonCard(pokemonArray[(row * 3) + col]);
                    gridPane.add(pokemonCard, col, row);
                }

            }
        }

        return gridPane;
    }

    private GridPane createItemGrid(Map<Item, Integer> itemsQuantities) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 3);
            column.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(column);
        }
        List<Map.Entry<Item, Integer>> itemEntries = new ArrayList<>(itemsQuantities.entrySet());

        int numItems = itemEntries.size();
        int numRows = Math.ceilDiv(numItems, 3);

        int itemIndex = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < 3; col++) {
                if (itemIndex < numItems) {
                    Map.Entry<Item, Integer> entry = itemEntries.get(itemIndex);
                    Item item = entry.getKey();

                    VBox itemContainer = createItemCard(item);
                    gridPane.add(itemContainer, col, row);

                    itemIndex++;
                } else {
                    break;
                }

            }
        }
        return gridPane;
    }

    private VBox createItemCard(Item item) {
        HBox cardContent = new HBox();
        cardContent.setSpacing(15);
        cardContent.setStyle(
                "-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8; -fx-font-size: 16px;");
        cardContent.setPrefSize(176, 120);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);

        String itemType = item.getType().toString();

        try {
            URL imageUrl = itemType.equals("Pokemon") ? getClass().getResource("../../img/potion.png")
                    : getClass().getResource("../../img/amuletcoin.png");
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                System.err.println("Image not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox info = new VBox();
        info.setSpacing(5);

        Label nameLabel = new Label("Name: " + item.getName());
        nameLabel.setWrapText(true);

        TextFlow typeLabelFlow = new TextFlow();
        Text typePrefix = new Text("Type: ");
        typePrefix.setFill(Color.web("#2c3e50"));
        Text typeValue = new Text();

        switch (itemType) {
            case "Money Multiplier":
                typeValue = new Text("MONEY");
                typeValue.setFill(Color.GOLDENROD);
                break;
            case "Pokemon":
                typeValue = new Text("PKMN");
                typeValue.setFill(Color.MAGENTA);
                break;
            default:
                typeValue.setFill(Color.BLACK);
                break;
        }

        typeValue.setStyle("-fx-font-weight: bold;");
        typeLabelFlow.getChildren().addAll(typePrefix, typeValue);

        Label descriptionLabel = new Label("Desc: " + item.getDescription());

        HBox.setHgrow(info, Priority.ALWAYS);
        info.getChildren().addAll(nameLabel, typeLabelFlow, descriptionLabel);

        cardContent.getChildren().addAll(imageView, info);

        Button useButton = new Button();
        useButton.setText("USE");
        useButton.setMaxWidth(88);
        useButton.setId("itemType");

        VBox itemContainer = new VBox();
        itemContainer.setSpacing(10);
        itemContainer.getChildren().addAll(cardContent, useButton);
        itemContainer.setPrefWidth(176);
        itemContainer.setAlignment(Pos.CENTER);

        useButton.setOnAction(event -> {
            try {
                ItemManager.activateItem(item.getName(), UserManager.getUser(SceneSwitcher.getCurrentUsername()));
                System.out.println("Used item: " + item.getName());
                ((GridPane) itemContainer.getParent()).getChildren().remove(itemContainer);
            } catch (Exception e) {
                System.err.println("Error using item: " + e.getMessage());
            }
        });

        return itemContainer;
    }

    public void PokemonPopup(Pokemon pokemon) {
        if (pokemon == null)
            return;
        currentPopupPokemon = pokemon;

        try {
            URL imageUrl = getClass().getResource(pokemon.getImagePath());
            if (imageUrl != null) {
                popupImage.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                popupImage.setImage(null);
            }
        } catch (Exception e) {
            popupImage.setImage(null);
        }
        popupName.setText("Name: " + pokemon.getName());
        popupType.setText("Type: " + pokemon.getType());
        popupHealth.setText("Health: " + pokemon.getTotalHealth());
        popupXP.setText("XP: " + pokemon.getXp());
        popupAbility.setText("Ability: " + pokemon.getAbilities());

        popupXpPrice.setText("XP Price: $" + String.format("%.2f", pokemon.getXp() + 1));

        overlayPane.setVisible(true);
        overlayPane.toFront();
    }

    @FXML
    private void buyXp(ActionEvent event) {
        if (currentPopupPokemon == null)
            return;
        User user = UserManager.getUser(SceneSwitcher.getCurrentUsername());
        PC pc = this.pc;

        try {
            boolean evolved = PCManager.buyXp(pc, currentPopupPokemon, user);
            PokemonPopup(currentPopupPokemon);
            if (evolved) {
                System.out.println(currentPopupPokemon.getName() + " evolved!");
            }
        } catch (Exception e) {
            System.out.println("Could not buy XP: " + e.getMessage());
        }
    }

    @FXML
    private void closePopup(ActionEvent event) {
        overlayPane.setVisible(false);
        overlayPane.toBack();
    }

    @FXML
    private void PC(ActionEvent event) {
        System.out.println("PC clicked");
        PCrectangle.setFill(javafx.scene.paint.Color.RED);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
    }

    @FXML
    private void shop(ActionEvent event) {
        System.out.println("Shop clicked");
        shoprectangle.setFill(javafx.scene.paint.Color.GREEN);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
        SceneSwitcher.switchToShop(event, SceneSwitcher.getCurrentUsername());
    }

    @FXML
    private void home(ActionEvent event) {
        System.out.println("Home clicked");
        homerectangle.setFill(javafx.scene.paint.Color.BLUE);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);

        SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());

    }

    @FXML
    private void profile(ActionEvent event) {
        System.out.println("Profile clicked");
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.PURPLE);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);

        SceneSwitcher.switchToProfile(event, SceneSwitcher.getCurrentUsername());

    }

}