
package pokeclicker.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import pokeclicker.manager.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pokeclicker.manager.UserManager;
import pokeclicker.model.Ability;
import pokeclicker.model.User;
import pokeclicker.model.common.PokeType;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import javafx.scene.Node;
import pokeclicker.manager.pokemon.PokemonManager;

public class RegistrationController implements Initializable {

    @FXML
    private ImageView pokedexbg, selectedimage;
    @FXML
    private TextField inputField;
    @FXML
    private Label namelabel, healthlabel, pricelabel, startmessage, healtherrorlabel, imageErrorlabel, nameErrorlabel;
    @FXML
    private Button imageselectbutton;
    @FXML
    private Label fixedname, fixedhealth, fixedprice, inputErrorlabel, createpokemonlabel, priceErrorlabel;
    @FXML
    private Line nameline, priceline, healthline;
    @FXML
    private MenuButton typebutton;
    private Label[] fields;
    private int currentIndex = 0;
    private javafx.beans.value.ChangeListener<String> listener;
    private String pokemonname, imagepath;
    private int totalHealth;
    private double price;
    private PokeType type;
    private String stringtype;
    private String username;
    private boolean labelcontrol = true;
    private MenuItem fire, water, grass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image pokedexbgImage = new Image(getClass().getResource("/img/pokedexbg.png").toExternalForm());
        pokedexbg.setImage(pokedexbgImage);
        pokedexbg.setPreserveRatio(true);

        fixedname.setText("Name:");
        fixedhealth.setText("Health:");
        fixedprice.setText("Price:");

        fields = new Label[] { namelabel, healthlabel, pricelabel };
        nameline.toFront();

    }

    @FXML
    private void inputbutton() {
        inputField.toFront();

    }

    @FXML

    private void okbutton(ActionEvent event) {
        String input = inputField.getText();
        switch (currentIndex) {

            case 0:
                if (input != null && !input.isEmpty()) {
                    namelabel.setText(input);
                    pokemonname = input;
                    System.out.println(currentIndex);
                    nameErrorlabel.toBack();
                    nameline.toBack();
                    healthline.toFront();
                } else {
                    nameErrorlabel.toFront();
                    currentIndex--;
                }
                break;
            case 1:

                if (input != null && !input.isEmpty() && Integer.valueOf(input) != 0) {
                    inputField.clear();
                    healthlabel.setText(input);
                    totalHealth = Integer.parseInt(input);
                    System.out.println(currentIndex);
                    System.out.println("Total Health: " + totalHealth);
                    healtherrorlabel.toBack();
                    priceline.toFront();
                    healthline.toBack();
                } else {
                    healtherrorlabel.toFront();

                    currentIndex--;
                }
                break;
            case 2:

                inputField.clear();
                if (input != null && !input.isEmpty() && Integer.valueOf(input) > 0) {
                    pricelabel.setText(input);
                    price = Double.parseDouble(input);
                } else {
                    currentIndex--;
                    priceErrorlabel.toFront();
                }
                System.out.println(currentIndex);

                break;

            case 3:

                System.out.println(currentIndex);
                if (pokemonname != null && totalHealth > 0 && price > 0 && type != null && imagepath != null) {
                    List<Ability> abilities = getTestAbilities(); // Create test ability
                    Pokemon pokemon = PokemonManager.createPokemon(pokemonname, totalHealth, abilities, price, type,
                            imagepath);
                    inputErrorlabel.toBack();

                    System.out.println("Pokémon created: " + pokemon.getName());
                    SceneSwitcher.switchToHome(event, username);
                } else {
                    inputErrorlabel.toFront();
                    createpokemonlabel.toBack();
                }
                break;
            default:

                return;

        }

        currentIndex++;

        inputField.clear();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void cancelbutton(ActionEvent event) {
        SceneSwitcher.switchToHome(event, username);

    }

    @FXML
    private void fire() {
        type = PokeType.FIRE;
        typebutton.setText("Fire");
        typebutton.setTextFill(Color.RED);

    }

    @FXML
    private void water() {
        type = PokeType.WATER;
        typebutton.setText("Water");
        typebutton.setTextFill(Color.BLUE);
        System.out.println(type);
    }

    @FXML
    private void grass() {
        type = PokeType.GRASS;
        typebutton.setText("Grass");
        typebutton.setTextFill(Color.GREEN);
    }

    public String getimageType() {
        stringtype = String.valueOf(type);
        return stringtype;
    }

    @FXML
    private void imageselect(ActionEvent event) {
        if (pokemonname != null && totalHealth > 0 && price > 0 && type != null) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = SceneSwitcher.switchToSceneWithController(stage,
                        "/pokeclicker/views/imageselection.fxml");

                // Now pass the existing values to ImageSelectController
                ImageSelectController controller = loader.getController();
                controller.initData(pokemonname, totalHealth, price, type);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imageErrorlabel.toFront();
        }
    }

    public void setImagePath(String path) {
        this.imagepath = path;
        Image img = new Image(getClass().getResource(path).toExternalForm());

        Image img2 = new Image(getClass().getResource(path).toExternalForm());
        selectedimage.setImage(img);

    }

    public void setPokemonData(String name, int health, double price, PokeType type, String imagePath) {
        this.pokemonname = name;
        this.totalHealth = health;
        this.price = price;
        this.type = type;
        this.imagepath = imagePath;

        namelabel.setText(name);
        healthlabel.setText(String.valueOf(health));
        pricelabel.setText(String.valueOf(price));
        typebutton.setText(type.toString());
        stringtype = String.valueOf(type);

        Image iv = new Image(getClass().getResource(imagePath).toExternalForm());
        selectedimage.setImage(iv);
        selectedimage.setFitWidth(100);
        selectedimage.setPreserveRatio(true);

    }

    private List<Ability> getTestAbilities() {
        List<Ability> abilities = new ArrayList<>();
        Ability testAbility = new Ability("Tackle", "A basic physical attack", type, 20, 0);
        abilities.add(testAbility);
        return abilities;
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

}