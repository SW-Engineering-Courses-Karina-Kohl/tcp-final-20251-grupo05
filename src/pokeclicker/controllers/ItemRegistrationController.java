package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import pokeclicker.manager.item.ItemManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import pokeclicker.model.item.Item;
import pokeclicker.util.SceneSwitcher;
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
    private String itemname;
    private String description;
    private double price;
    private double damageOrMultiplier;
    private ItemType type;
    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image pokedexbgImage = new Image(getClass().getResource("/img/pokedexbg.png").toExternalForm());
        pokedexbg.setImage(pokedexbgImage);
        pokedexbg.setPreserveRatio(true);

        fields = new Label[] { namelabel, pricelabel, descriptionlabel };
        nameline.toFront();
        username = SceneSwitcher.getCurrentUsername();
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
                    descriptionErrorLabel.toBack();
                    descriptionline.toBack();
                    System.out.println(type);
                    if (type != null) {
                        typeline.toFront();
                    }
                } else {
                    descriptionErrorLabel.toFront();
                    currentIndex--;

                }
                System.out.println(currentIndex);
                break;

            case 3:
                System.out.println(currentIndex);

                if (type != null) {
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            double value = Double.parseDouble(input.trim());
                            if (value > 0) {
                                damageOrMultiplier = value;
                                damageormultiplierlabel.setText(input);
                                typeline.toBack();
                                createitemlabel.toFront();
                                descriptionErrorLabel.toBack();
                                System.out.println(currentIndex);
                                System.out.println("Damage/Multiplier: " + damageOrMultiplier);
                            } else {
                                currentIndex--;
                                descriptionErrorLabel.setText("Modifier/Damage must be greater than 0");
                                descriptionErrorLabel.toFront();
                            }
                        } catch (NumberFormatException e) {
                            currentIndex--;
                            descriptionErrorLabel.setText("Please enter a valid number");
                            descriptionErrorLabel.toFront();
                        }
                    } else {
                        currentIndex--;
                        descriptionErrorLabel.setText("Modifier/Damage field cannot be empty");
                        descriptionErrorLabel.toFront();
                    }
                } else {
                    currentIndex--;
                    descriptionErrorLabel.setText("Please select a type first");
                    descriptionErrorLabel.toFront();
                    typeline.toBack();
                }
                break;

            case 4:
                System.out.println(currentIndex);
                if (itemname != null && !itemname.isEmpty() && price > 0 && type != null && description != null
                        && !description.isEmpty()) {
                    try {
                        Item item = ItemManager.createItem(itemname, price, description, type, damageOrMultiplier,
                                username);
                        inputErrorlabel.toBack();
                        System.out.println("Item created: " + item.getName());
                        SceneSwitcher.switchToShop(event, username);
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

        if (description != null) {
            descriptionline.toBack();
            typeline.toFront();
        }
    }

    @FXML
    private void pokemon() {
        type = ItemType.POKEMON;
        typebutton.setText("Pokemon");
        typelabel.setText("Damage:");
        if (description != null) {
            descriptionline.toBack();
            typeline.toFront();
        }
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }
}
