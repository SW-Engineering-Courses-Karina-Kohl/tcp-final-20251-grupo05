
package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import pokeclicker.manager.ability.AbilityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pokeclicker.model.Ability;
import pokeclicker.model.common.PokeType;
import pokeclicker.util.SceneSwitcher;

public class AbilityController implements Initializable {

    @FXML
    private ImageView pokedexbg, selectedimage;
    @FXML
    private TextField inputField;
    @FXML
    private Label namelabel, descriptionlabel, damagelabel, startmessage, healtherrorlabel, imageErrorlabel,
            nameErrorlabel,
            curelabel, descriptionErrorlabel;
    @FXML
    private Button imageselectbutton;
    @FXML
    private Label fixedname, fixedhealth, fixedprice, inputErrorlabel, createpokemonlabel, damageErrorlabel,
            cureErrorlabel;
    @FXML
    private Line nameline, damageline, descriptionline, cureline;
    @FXML
    private MenuButton typebutton;
    private int currentIndex = 0;
    private PokeType type;
    private String stringtype;
    private String username;
    private double damage;
    private double cure;
    private String description;
    private String abilityname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image pokedexbgImage = new Image(getClass().getResource("/img/pokedexbg.png").toExternalForm());
        pokedexbg.setImage(pokedexbgImage);
        pokedexbg.setPreserveRatio(true);

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
                    abilityname = input;
                    System.out.println(currentIndex);
                    nameErrorlabel.toBack();
                    nameline.toBack();
                    descriptionline.toFront();
                } else {
                    nameErrorlabel.toFront();
                    currentIndex--;
                }
                break;
            case 1:

                if (input != null && !input.isEmpty()) {
                    inputField.clear();
                    descriptionlabel.setText(input);
                    description = input;
                    System.out.println(currentIndex);
                    System.out.println("Description " + description);
                    descriptionErrorlabel.toBack();
                    damageline.toFront();
                    descriptionline.toBack();
                } else {
                    descriptionErrorlabel.toFront();
                    currentIndex--;
                }
                break;
            case 2:

                inputField.clear();
                if (input != null && !input.isEmpty() && Integer.valueOf(input) > 0) {
                    damagelabel.setText(input);
                    damage = Double.parseDouble(input);
                    cureline.toFront();
                    damageline.toBack();
                } else {
                    currentIndex--;
                    damageErrorlabel.toFront();

                }
                System.out.println(currentIndex);
                break;
            case 3:

                inputField.clear();
                if (input != null && !input.isEmpty() && Integer.valueOf(input) > 0) {
                    curelabel.setText(input);
                    cure = Double.parseDouble(input);
                } else {
                    currentIndex--;
                    cureErrorlabel.toFront();

                }
                System.out.println(currentIndex);
                break;

            case 4:

                System.out.println(currentIndex);
                if (abilityname != null && damage > 0 && cure > 0 && type != null && description != null) {
                    Ability ability = AbilityManager.createAbility(abilityname, description, type, damage, cure);
                    inputErrorlabel.toBack();

                    System.out.println("Ability created: " + ability.getName());
                    SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());
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
        SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());

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

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

}