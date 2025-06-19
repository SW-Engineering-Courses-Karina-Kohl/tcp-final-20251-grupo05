package pokeclicker.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import pokeclicker.manager.UserManager;
import pokeclicker.manager.item.ItemManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
import pokeclicker.model.item.Item;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import javafx.scene.Node;
import pokeclicker.manager.pokemon.PokemonManager;
import pokeclicker.manager.item.ItemManager;
import pokeclicker.model.item.ItemType;

public class ItemRegistrationController implements Initializable {
    @FXML
    private ImageView pokedexbg, selectedimage;
    @FXML
    private TextField inputField;
    @FXML
    private Label namelabel, pricelabel, descriptionlabel, startmessage, descriptionErrorLabel, imageErrorlabel,
            nameErrorlabel, damageormultiplierlabel, modifierErrorlabel;
    @FXML
    private Button imageselectbutton;
    @FXML
    private Label fixedname, fixedhealth, fixedprice, inputErrorlabel, createitemlabel, priceErrorlabel, typelabel;
    @FXML
    private Line nameline, priceline, descriptionline, typeline;
    @FXML
    private MenuButton typebutton;
    private Label[] fields;
    private int currentIndex = 0;
    private javafx.beans.value.ChangeListener<String> listener;
    private String itemname, imagepath;
    private String description;
    private double price;
    private double damageOrMultiplier;
    private ItemType type;
    private String stringtype;
    private String username;
    private boolean labelcontrol = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image pokedexbgImage = new Image(getClass().getResource("/img/pokedexbg.png").toExternalForm());
        pokedexbg.setImage(pokedexbgImage);
        pokedexbg.setPreserveRatio(true);

        fields = new Label[] { namelabel, pricelabel, descriptionlabel };
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
                    itemname = input;
                    System.out.println(currentIndex);
                    nameErrorlabel.toBack();
                    nameline.toBack();
                    priceline.toFront();
                } else {
                    nameErrorlabel.toFront();
                    currentIndex--;
                }
                break;
            case 1:

                if (input != null && !input.isEmpty() && Double.valueOf(input) > 0) {
                    inputField.clear();
                    pricelabel.setText(input);
                    price = Double.parseDouble(input);
                    System.out.println(currentIndex);
                    System.out.println("Total Price: " + price);
                    priceErrorlabel.toBack();
                    descriptionline.toFront();
                    priceline.toBack();
                } else {
                    priceErrorlabel.toFront();
                    currentIndex--;
                }
                break;
            case 2:

                inputField.clear();
                if (input != null && !input.isEmpty()) {
                    descriptionlabel.setText(input);
                    description = input;
                    descriptionline.toBack();
                } else {
                    descriptionErrorLabel.toFront();
                    currentIndex--;

                }
                System.out.println(currentIndex);
                break;

            case 3:

                if (type != null) {

                    if (input != null && Integer.valueOf(input) > 0) {

                        damageOrMultiplier = Double.valueOf(input);
                        damageormultiplierlabel.setText(input);
                        typeline.toBack();
                        createitemlabel.toFront();
                        modifierErrorlabel.toBack();
                    } else {
                        currentIndex--;
                        modifierErrorlabel.toFront();
                    }
                }
                break;

            case 4:
                System.out.println(currentIndex);
                if (itemname != null && !itemname.isEmpty() && price > 0 && type != null && description != null
                        && !description.isEmpty()) {
                    try {
                        Item item = ItemManager.createItem(itemname, price, description, type, damageOrMultiplier);
                        inputErrorlabel.toBack();
                        System.out.println("Item created: " + item.getName());
                        SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());
                    } catch (IllegalArgumentException e) {
                        inputErrorlabel.setText(e.getMessage());
                        inputErrorlabel.toFront();
                        createitemlabel.toBack();
                    }
                } else {
                    inputErrorlabel.setText("Preencha todos os campos corretamente!");
                    inputErrorlabel.toFront();
                    createitemlabel.toBack();
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
    private void money_multiplier() {
        type = ItemType.MONEY_MULTIPLIER;
        typebutton.setText("Money Multiplier");
        typelabel.setText("Multiplier:");
        descriptionline.toBack();
        typeline.toFront();
    }

    @FXML
    private void pokemon() {
        type = ItemType.POKEMON;
        typebutton.setText("Pokemon");
        typelabel.setText("Damage:");
        descriptionline.toBack();
        typeline.toFront();
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }
}
