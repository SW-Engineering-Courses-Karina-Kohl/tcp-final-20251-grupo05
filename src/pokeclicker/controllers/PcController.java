package pokeclicker.controllers;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.Node;
import pokeclicker.manager.PCManager;
import pokeclicker.manager.UserManager;
import pokeclicker.model.Ability;
import pokeclicker.model.User;
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.LevelType;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import pokeclicker.game.PC;
import pokeclicker.manager.item.ItemManager;
import pokeclicker.manager.pokemon.PokemonFilter;
import pokeclicker.manager.pokemon.PokemonManager;

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

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private AnchorPane overlayPane2;
    @FXML
    private AnchorPane abilityPane;
    @FXML
    private VBox abilitydisplaybox;
    @FXML
    private VBox popupContent;
    @FXML
    private ImageView popupImage;
    @FXML
    private Label moneyerrorxp;
    @FXML
    private ProgressBar popupXPBar;
    @FXML
    private Label maxLvl;
    @FXML
    private Label evolveLabel;
    @FXML
    private Button evolveButton;

    @FXML
    private Label popupName;
    @FXML
    private Label popupType;
    @FXML
    private Label popupHealth;
    @FXML
    private Label popupXP;
    @FXML
    private Pokemon currentPopupPokemon;
    @FXML
    private Label popupXpPrice;
    @FXML
    private Button buyXpButton;
    @FXML
    private Label popupAbility;
    @FXML
    private TextFlow abilityTypeFlow;
    @FXML
    private ComboBox<String> filterTypeCombo;
    @FXML
    private TextField filterNameField;
    @FXML
    private ComboBox<String> filterLevelCombo;

    private PC pc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneIconUtil.setupSelectionBarImages(pokeballimg, homeimg, profileimg, shopimg);
        PCrectangle.setFill(javafx.scene.paint.Color.RED);

        pc = PCManager.getPC(UserManager.getUser(SceneSwitcher.getCurrentUsername()), Optional.empty(),
                Optional.empty());

        evolveButton.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent;");

        pcPokemonSP.setContent(createPokemonGrid(pc.getPokemons().toArray(new Pokemon[0])));
        pcItemSP.setContent(createItemGrid(pc.getItemQuantities()));
        filterTypeCombo.getItems().clear();
        for (pokeclicker.model.common.PokeType type : pokeclicker.model.common.PokeType.values()) {
            filterTypeCombo.getItems().add(type.toString());
        }
        filterLevelCombo.getItems().addAll("BEGINNER", "ADVANCED");
    }

    private String getAbilityNames(Pokemon pokemon) {
        List<Ability> abilities = pokemon.getAbilities();
        if (abilities == null || abilities.isEmpty())
            return "None";
        return abilities.stream().map(Ability::getName).collect(java.util.stream.Collectors.joining(", "));
    }

    private HBox createPokemonCard(Pokemon pokemon) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setStyle(
                "-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8; -fx-font-size: 16px;");
        card.setPrefSize(270, 120);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(90);
        imageView.setPreserveRatio(true);

        System.out.println("Pokemon: " + pokemon.getName() + "pokemon.getImagePath(): " + pokemon.getImagePath());

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
        typeLabelFlow.setTextAlignment(TextAlignment.CENTER);
        typeLabelFlow.getChildren().addAll(typePrefix, typeValue);

        Label healthLabel = new Label("Health: " + pokemon.getTotalHealth());
        healthLabel.setWrapText(true);

        Label abilityLabel = new Label("Ability: " + getAbilityNames(pokemon));

        Label xpLabel = new Label("XP: " + pokemon.getXp());

        Button detailsButton = new Button("Details");
        detailsButton.setOnAction(e -> {
            System.out.println("Details clicked for: " + pokemon.getName());
            PokemonPopup(pokemon);
        });

        Button favoriteButton = new Button("Favorite");
        favoriteButton.setOnAction(e -> {
            User user = UserManager.getUser(SceneSwitcher.getCurrentUsername());
            user.setFavoritePokemon(pokemon);
            UserManager.updateUser(user);
            System.out.println("Set favorite Pokemon to: " + pokemon.getName());
        });

        HBox.setHgrow(info, Priority.ALWAYS);
        info.getChildren().addAll(nameLabel, typeLabelFlow, healthLabel, abilityLabel, xpLabel, detailsButton,
                favoriteButton);

        HBox blank = new HBox();
        blank.setPrefWidth(20);

        info.setAlignment(Pos.CENTER);
        card.setAlignment(Pos.CENTER);

        card.getChildren().addAll(blank, imageView, info);

        PokemonManager.updatePokemon(pokemon);

        return card;
    }

    private VBox createAbilityCard(Ability ability) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setStyle(
                "-fx-padding: 10; -fx-background-color: #e0f7fa; -fx-border-radius: 8; -fx-font-size: 15px; -fx-background-radius: 8;");
        card.setPrefWidth(250);

        Label nameLabel = new Label("Name: " + ability.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");
        TextFlow typeFlow = new TextFlow();
        Text typeLabel = new Text("Type: ");
        Text typeValue = new Text(ability.getType());
        switch (ability.getType().toUpperCase()) {
            case "FIRE" -> typeValue.setFill(Color.RED);
            case "WATER" -> typeValue.setFill(Color.BLUE);
            case "GRASS" -> typeValue.setFill(Color.GREEN);
            default -> typeValue.setFill(Color.BLACK);
        }
        typeValue.setStyle("-fx-font-weight: bold;");
        typeFlow.getChildren().addAll(typeLabel, typeValue);

        Label damageLabel = new Label("Damage: " + ability.getDamage());
        Label cureLabel = new Label("Cure: " + ability.getCure());
        Label descLabel = new Label("Description: " + ability.getDescription());
        descLabel.setWrapText(true);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(
                "-fx-background-color: #e57373; -fx-text-fill: white; -fx-font-size:12px; -fx-font-weight: bold;");

        deleteButton.setPrefWidth(75);
        deleteButton.setMinHeight(0);
        deleteButton.setPrefHeight(28);
        deleteButton.setOnAction(e -> {
            try {
                pokeclicker.manager.ability.AbilityManager.deleteAbility(ability.getName());
                currentPopupPokemon.getAbilities().removeIf(a -> a.getName().equals(ability.getName()));
                showAbilitiesPopup(null);

                pc = PCManager.getPC(UserManager.getUser(SceneSwitcher.getCurrentUsername()), Optional.empty(),
                        Optional.empty());
                pcPokemonSP.setContent(createPokemonGrid(pc.getPokemons().toArray(new Pokemon[0])));
            } catch (Exception ex) {
                System.err.println("Error deleting ability: " + ex.getMessage());
            }
        });

        card.getChildren().addAll(nameLabel, typeFlow, damageLabel, cureLabel, descLabel, deleteButton);
        return card;
    }

    private GridPane createPokemonGrid(Pokemon[] pokemonArray) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(20));

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 2);
            column.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(column);
        }
        int numRows = Math.ceilDiv(pokemonArray.length, 2);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < 2; col++) {
                if (pokemonArray.length == (row * 2) + col) {
                    break;
                } else {
                    Node pokemonCard = createPokemonCard(pokemonArray[(row * 2) + col]);
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
            URL imageUrl = itemType.equals("Pokemon")
                    ? getClass().getResource("../../img/potion.png")
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
                String itemTypeName = item.getType().toString();
                if (itemTypeName.equals("Money Multiplier")) {
                    ItemManager.activateItem(item.getName(), UserManager.getUser(SceneSwitcher.getCurrentUsername()));
                    ((GridPane) itemContainer.getParent()).getChildren().remove(itemContainer);
                    SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());
                } else if (itemTypeName.equals("Pokemon")) {
                    showPokemonSelectPopup(item, itemContainer);
                    // ((GridPane) itemContainer.getParent()).getChildren().remove(itemContainer);
                }
            } catch (Exception e) {
                System.err.println("Error using item: " + e.getMessage());
            }
        });

        return itemContainer;
    }

    private void showPokemonSelectPopup(Item item, VBox itemContainer) {
        // Cria um VBox para listar os pokémons
        VBox selectBox = new VBox(10);
        selectBox.setAlignment(Pos.CENTER);
        selectBox.setStyle(
                "-fx-background-color: #fff; -fx-padding: 20; -fx-border-radius: 8; -fx-background-radius: 8;");
        // Limpa e mostra o overlayPane
        overlayPane2.getChildren().clear();
        overlayPane2.getChildren().add(selectBox);

        AnchorPane.setTopAnchor(selectBox, (overlayPane2.getHeight() - selectBox.getHeight()) / 2);
        AnchorPane.setLeftAnchor(selectBox, (overlayPane2.getWidth() - selectBox.getWidth()) / 2);

        selectBox.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            AnchorPane.setTopAnchor(selectBox, (overlayPane2.getHeight() - newBounds.getHeight()) / 2);
            AnchorPane.setLeftAnchor(selectBox, (overlayPane2.getWidth() - newBounds.getWidth()) / 2);
        });
        overlayPane2.heightProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(selectBox, (newVal.doubleValue() - selectBox.getHeight()) / 2);
        });
        overlayPane2.widthProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(selectBox, (newVal.doubleValue() - selectBox.getWidth()) / 2);
        });
        Label title = new Label("Escolha um Pokémon para usar o item:");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        User user = UserManager.getUser(SceneSwitcher.getCurrentUsername());
        List<Pokemon> pokemons = PCManager.getPC(user, Optional.empty(), Optional.empty()).getPokemons();

        for (Pokemon pokemon : pokemons) {
            Button pokeBtn = new Button(pokemon.getName());
            pokeBtn.setOnAction(e -> {
                ItemManager.activateItem(item.getName(), pokemon);
                User userAtual = UserManager.getUser(SceneSwitcher.getCurrentUsername());
                pc = PCManager.getPC(userAtual, Optional.empty(), Optional.empty());
                System.out.println("Used item: " + item.getName() + " on " + pokemon.getName());
                // Fecha o popup
                overlayPane2.setVisible(false);
                overlayPane2.toBack();
                ((GridPane) itemContainer.getParent()).getChildren().remove(itemContainer);
                // Atualiza a lista de itens
                refreshPokemonGrid();
                SceneSwitcher.switchToPC(e, SceneSwitcher.getCurrentUsername());
                // PokemonPopup(pokemon);
                // pcItemSP.setContent(createItemGrid(pc.getItemQuantities()));
            });
            selectBox.getChildren().add(pokeBtn);
        }

        // Botão para cancelar
        Button cancelBtn = new Button("Cancelar");
        cancelBtn.setOnAction(e -> {
            overlayPane2.setVisible(false);
            overlayPane2.toBack();
        });
        selectBox.getChildren().add(cancelBtn);

        // Limpa e mostra o overlayPane
        overlayPane2.getChildren().clear();
        overlayPane2.getChildren().add(selectBox);
        overlayPane2.setVisible(true);
        overlayPane2.toFront();
    }

    public void PokemonPopup(Pokemon pokemon) {
        if (pokemon == null)
            return;
        currentPopupPokemon = pokemon;

        popupImage.setFitHeight(150);
        popupImage.setPreserveRatio(true);
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

        popupXP.setText("XP: " + pokemon.getXp() + "/100.0");
        popupAbility.setText("Ability: " + getAbilityNames(pokemon));

        popupXpPrice.setText("XP Price: $" + String.format("%.2f", pokemon.getXp() + 1));
        overlayPane.setVisible(true);
        overlayPane.toFront();
        double progress = (double) pokemon.getXp() / 100;
        popupXPBar.setProgress(progress);
        popupXPBar.setStyle("-fx-accent: TEAL;");

    }

    @FXML
    private void buyXp(ActionEvent event) {
        if (currentPopupPokemon == null)
            return;
        User user = UserManager.getUser(SceneSwitcher.getCurrentUsername());
        try {
            boolean evolved = PCManager.buyXp(pc, currentPopupPokemon, user);
            PokemonPopup(currentPopupPokemon);
            if (evolved) {
                evolveButton.setStyle("-fx-background-color: white; -fx-text-fill: teal; -fx-font-weight: bold;");
            }
        } catch (Exception e) {
            System.out.println("Could not buy XP: " + e.getMessage());
            moneyerrorxp.setText("Not enough money to buy XP");
        }
    }

    private void evolvePokemon(Pokemon pokemon) {
        if (currentPopupPokemon.getXp() < 100) {
            maxLvl.setText("This pokemon cannot evolve yet");
            maxLvl.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        } else {
            switch (currentPopupPokemon.getImagePath()) {
                case "/img/basic/charmander.gif":
                    currentPopupPokemon.setImagePath("/img/evolutions/charizard.gif");
                    break;
                case "/img/basic/squirtle.gif":
                    currentPopupPokemon.setImagePath("/img/evolutions/blastoise.gif");
                    break;
                case "/img/basic/bulbasaur.gif":
                    currentPopupPokemon.setImagePath("/img/evolutions/venusaur.gif");
                    break;
                case "/img/basic/cyndaquil.gif":
                    currentPopupPokemon.setImagePath("/img/evolutions/typhlosion.gif");
                    break;
                case "/img/basic/piplup.gif":
                    currentPopupPokemon.setImagePath("/img/evolutions/empoleon.gif");
                    break;
                case "/img/basic/turtwig.gif":
                    currentPopupPokemon.setImagePath("/img/evolutions/torterra.gif");
                    break;
                default:
                    break;
            }
            maxLvl.setText("");
        }

        PokemonManager.updatePokemon(currentPopupPokemon);
    }

    private void refreshPokemonGrid() {
        Pokemon[] updatedPokemons = pc.getPokemons().toArray(new Pokemon[0]);
        GridPane newGrid = createPokemonGrid(updatedPokemons);
        pcPokemonSP.setContent(newGrid);
    }

    @FXML
    private void evolveButton() {
        evolvePokemon(currentPopupPokemon);
        try {
            URL imageUrl = getClass().getResource(currentPopupPokemon.getImagePath());
            if (imageUrl != null) {
                popupImage.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                popupImage.setImage(null);
            }
        } catch (Exception e) {
            popupImage.setImage(null);
        }
        refreshPokemonGrid();
        String message = !currentPopupPokemon.getImagePath().contains("evolutions")
                ? ""
                : currentPopupPokemon.getName() + " evolved!";
        evolveLabel.setText(message);
    }

    @FXML
    private void showAbilitiesPopup(ActionEvent event) {
        if (currentPopupPokemon == null)
            return;
        abilitydisplaybox.getChildren().clear();
        for (Ability ability : currentPopupPokemon.getAbilities()) {
            abilitydisplaybox.getChildren().add(createAbilityCard(ability));
        }
        overlayPane.setVisible(false);
        overlayPane.toBack();
        abilityPane.setVisible(true);
        abilityPane.toFront();
    }

    @FXML
    private void returnToPokemonPopup(ActionEvent event) {
        String username = SceneSwitcher.getCurrentUsername();
        pc = PCManager.getPC(UserManager.getUser(username), Optional.empty(), Optional.empty());
        List<Pokemon> userPokemons = pc.getPokemons();

        currentPopupPokemon = userPokemons.stream()
                .filter(p -> p.getName().equals(currentPopupPokemon.getName()))
                .findFirst()
                .orElse(currentPopupPokemon);

        PokemonPopup(currentPopupPokemon);

        abilityPane.setVisible(false);
        abilityPane.toBack();
        overlayPane.setVisible(true);
        overlayPane.toFront();
    }

    @FXML
    private void closePopup(ActionEvent event) {
        String username = SceneSwitcher.getCurrentUsername();
        pc = PCManager.getPC(UserManager.getUser(username), Optional.empty(), Optional.empty());
        List<Pokemon> userPokemons = pc.getPokemons();
        currentPopupPokemon = userPokemons.stream()
                .filter(p -> p.getName().equals(currentPopupPokemon.getName()))
                .findFirst()
                .orElse(currentPopupPokemon);

        pcPokemonSP.setContent(createPokemonGrid(pc.getPokemons().toArray(new Pokemon[0])));

        overlayPane.setVisible(false);
        overlayPane.toBack();
        moneyerrorxp.setText("");
        maxLvl.setText("");
        evolveLabel.setText("");
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

    private void setAbilityTypeFlow(String type) {
        abilityTypeFlow.getChildren().clear();

        Text label = new Text("Type: ");
        Text typeText = new Text(type);

        switch (type.toUpperCase()) {
            case "FIRE" -> typeText.setFill(Color.RED);
            case "WATER" -> typeText.setFill(Color.BLUE);
            case "GRASS" -> typeText.setFill(Color.GREEN);
            default -> typeText.setFill(Color.BLACK);
        }

        typeText.setStyle("-fx-font-weight: bold;");
        abilityTypeFlow.getChildren().addAll(label, typeText);
    }

    @FXML
    private void applyFilter(ActionEvent event) {

        pc = PCManager.getPC(UserManager.getUser(SceneSwitcher.getCurrentUsername()), Optional.empty(),
                Optional.empty());

        List<Pokemon> filtered = pc.getPokemons().stream()
                .filter(p -> {
                    if (filterTypeCombo.getValue() != null && !filterTypeCombo.getValue().isEmpty()) {
                        if (!p.getType().equalsIgnoreCase(filterTypeCombo.getValue()))
                            return false;
                    }
                    if (filterNameField.getText() != null && !filterNameField.getText().isEmpty()) {
                        if (!p.getName().toLowerCase().contains(filterNameField.getText().toLowerCase()))
                            return false;
                    }
                    if (filterLevelCombo.getValue() != null && !filterLevelCombo.getValue().isEmpty()) {
                        if (!p.getLevel().name().equalsIgnoreCase(filterLevelCombo.getValue()))
                            return false;
                    }
                    return true;
                })
                .toList();

        pcPokemonSP.setContent(createPokemonGrid(filtered.toArray(new Pokemon[0])));
        for (Pokemon p : filtered) {
            System.out.println("Pokemon: " + p.getName() + ", type: " + p.getType());
        }
    }

    @FXML
    private void clearFilter(ActionEvent event) {
        filterTypeCombo.setValue(null);
        filterNameField.clear();
        filterLevelCombo.setValue(null);

        PokemonFilter filter = new PokemonFilter();
        filter.setUser(SceneSwitcher.getCurrentUsername());
        filter.setAvailable(false);
        List<Pokemon> allPokemons = PokemonManager.getAllPokemons(Optional.of(filter));
        pcPokemonSP.setContent(createPokemonGrid(allPokemons.toArray(new Pokemon[0])));
    }
}