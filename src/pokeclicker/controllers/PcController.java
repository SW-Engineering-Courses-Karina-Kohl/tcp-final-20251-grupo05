package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import pokeclicker.game.PC;

public class PcController implements Initializable{
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

    @FXML TabPane pcTabPane;

    @FXML
    private Line longline;
    @FXML
    private Line shortline1;
    @FXML
    private Line shortline2;
    @FXML
    private Line shortline3;

    @FXML
    private ScrollPane pcScrollPane;
    @FXML
    private Button PC;
    @FXML
    private Button shop;
    @FXML
    private Button home;
    @FXML
    private Button profile;

    private PC pc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneIconUtil.setupSelectionBarImages(pokeballimg, homeimg, profileimg, shopimg);
        PCrectangle.setFill(javafx.scene.paint.Color.RED);

        pc = PCManager.getPC(UserManager.getUser(SceneSwitcher.getCurrentUsername()));

    pcScrollPane.setContent(createPokemonGrid(pc.getPokemons().toArray(new Pokemon[0])));
    }

    private HBox createPokemonCard(Pokemon pokemon) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-background-color: #f0f0f0; -fx-border-radius: 8; -fx-font-size: 16px;");
        card.setPrefSize(176, 120);

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

        HBox.setHgrow(info, Priority.ALWAYS);
        info.getChildren().addAll(nameLabel, typeLabelFlow, healthLabel, abilityLabel);

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

        for(int row = 0; row < numRows; row++)
        {
            for(int col=0; col < 3; col++)
            {   
                if(pokemonArray.length == (row * 3) + col)
                {
                    break;
                }
                else
                {
                    Node pokemonCard = createPokemonCard(pokemonArray[(row * 3)+col]);
                    gridPane.add(pokemonCard, col, row);
                }
                
            }
        }

        return gridPane;
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