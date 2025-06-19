package pokeclicker.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pokeclicker.model.common.PokeType;
import pokeclicker.util.SceneSwitcher;
import javafx.scene.Node;

public class ImageSelectController implements Initializable {
    @FXML
    private ImageView pokedexbg, image1, image2;
    @FXML
    private TextField inputField;
    @FXML
    private Label namelabel, healthlabel, pricelabel, startmessage;
    @FXML
    private Button imageselectbutton;
    @FXML
    private Label fixedname, fixedhealth, fixedprice;
    @FXML
    private MenuButton typebutton;
    private int totalHealth;
    private double price;
    private PokeType type;
    private String SelectedImagePath;
    private String imagePath1 = "", imagePath2 = "";
    private String pokemonName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image pokedexbgImage = new Image(getClass().getResource("/img/pokedexbg.png").toExternalForm());

        pokedexbg.setImage(pokedexbgImage);
        pokedexbg.setPreserveRatio(true);

    }

    public void initData(String name, int health, double price, PokeType type) {
        this.pokemonName = name;
        this.totalHealth = health;
        this.price = price;
        this.type = type;
        displayImageForType(type);
    }

    private void displayImageForType(PokeType type) {

        switch (type) {
            case FIRE:
                imagePath1 = "/img/charmander.png";
                imagePath2 = "/img/cyndaquil (1).png";
                break;
            case WATER:
                imagePath1 = "/img/squirtle.png";
                imagePath2 = "/img/piplup.png";
                break;
            case GRASS:
                imagePath1 = "/img/bulbasaur.png";
                imagePath2 = "/img/turtwig (1).png";
                break;
        }

        Image imageL = new Image(getClass().getResource(imagePath1).toExternalForm());
        image1.setImage(imageL);
        Image imageR = new Image(getClass().getResource(imagePath2).toExternalForm());
        image2.setImage(imageR);
        image2.setPreserveRatio(true);
        image2.setFitWidth(150);

    }

    @FXML
    private void leftbutton(ActionEvent event) {
        SelectedImagePath = imagePath1;
        passImageBackAndReturn(event);

    }

    @FXML
    private void rightbutton(ActionEvent event) {
        SelectedImagePath = imagePath2;
        passImageBackAndReturn(event);

    }

    private void passImageBackAndReturn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = SceneSwitcher.switchToSceneWithController(stage,
                    "/pokeclicker/views/pokemonregistration.fxml");

            RegistrationController controller = loader.getController();
            controller.setPokemonData(pokemonName, totalHealth, price, type, SelectedImagePath);
            controller.setCurrentIndex(3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}