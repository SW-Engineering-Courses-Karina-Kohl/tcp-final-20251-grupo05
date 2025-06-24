
package pokeclicker.controllers;

import pokeclicker.manager.pokemon.PokemonManager;
import pokeclicker.model.pokemon.Pokemon;
import pokeclicker.manager.pokemon.PokemonFilter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import pokeclicker.manager.ability.AbilityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
        updatePokemonMenuButton();
    }

    @FXML
    private void inputbutton() {
        inputField.toFront();

    }

    @FXML
    private MenuButton pokemonMenuButton;
    private String selectedPokemonName;

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
                System.out.println(currentIndex);
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
                System.out.println(currentIndex);
                inputField.clear();
                try {
                    if (input != null && !input.isEmpty() && Double.parseDouble(input) > 0) {
                        damagelabel.setText(input);
                        damage = Double.parseDouble(input);
                        cureline.toFront();
                        damageline.toBack();
                        descriptionErrorlabel.toBack();
                    } else {
                        currentIndex--;
                        descriptionErrorlabel.setText("Damage must be a positive number!");
                        descriptionErrorlabel.toFront();
                    }
                } catch (NumberFormatException e) {
                    currentIndex--;
                    descriptionErrorlabel.setText("Invalid value.");
                    descriptionErrorlabel.toFront();
                }
                System.out.println(currentIndex);
                break;
            case 3:
                System.out.println(currentIndex);
                inputField.clear();
                try {
                    if (input != null && !input.isEmpty() && Double.parseDouble(input) > 0) {
                        curelabel.setText(input);
                        cure = Double.parseDouble(input);
                        cureErrorlabel.toBack();
                    } else {
                        currentIndex--;
                        cureErrorlabel.setText("Cure must be a positive number!");
                        cureErrorlabel.toFront();
                    }
                } catch (NumberFormatException e) {
                    currentIndex--;
                    cureErrorlabel.setText("Invalid value.");
                    cureErrorlabel.toFront();
                }
                System.out.println(currentIndex);
                break;

            case 4:
                if (abilityname != null && damage > 0 && cure > 0 && type != null && description != null) {
                    Ability ability = AbilityManager.createAbility(abilityname, description, type, damage, cure);
                    inputErrorlabel.toBack();

                    // Add the ability to the selected Pokémon
                    if (selectedPokemonName != null) {
                        PokemonManager.addAbilityToPokemon(selectedPokemonName, abilityname);
                    }

                    System.out.println("Ability created: " + ability.getName());
                    SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());
                } else {
                    inputErrorlabel.toFront();
                    createpokemonlabel.toBack();
                    currentIndex--;
                }
                break;
            default:

                return;

        }

        currentIndex++;

        inputField.clear();

    }

    private void updatePokemonMenuButton() {
        pokemonMenuButton.getItems().clear();
        if (type == null || username == null) {
            MenuItem infoItem = new MenuItem("Please set a type");
            infoItem.setDisable(true);
            pokemonMenuButton.getItems().add(infoItem);
            pokemonMenuButton.setText("Select Pokémon");
            selectedPokemonName = null;
            return;
        }

        PokemonFilter filter = new PokemonFilter();
        filter.setUser(username);

        List<Pokemon> pokemons = PokemonManager.getAllPokemons(java.util.Optional.of(filter))
                .stream()
                .filter(p -> {
                    try {
                        return PokeType.valueOf(p.getType().toUpperCase()) == type;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());

        if (pokemons.isEmpty()) {
            MenuItem infoItem = new MenuItem("No Pokémon of this type");
            infoItem.setDisable(true);
            pokemonMenuButton.getItems().add(infoItem);
            pokemonMenuButton.setText("Select Pokémon");
            selectedPokemonName = null;
            return;
        }

        for (Pokemon p : pokemons) {
            MenuItem item = new MenuItem(p.getName());
            item.setOnAction(e -> {
                pokemonMenuButton.setText(p.getName());
                selectedPokemonName = p.getName();
            });
            pokemonMenuButton.getItems().add(item);
        }
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
        updatePokemonMenuButton();
    }

    @FXML
    private void water() {
        type = PokeType.WATER;
        typebutton.setText("Water");
        typebutton.setTextFill(Color.BLUE);
        System.out.println(type);
        updatePokemonMenuButton();
    }

    @FXML
    private void grass() {
        type = PokeType.GRASS;
        typebutton.setText("Grass");
        typebutton.setTextFill(Color.GREEN);
        updatePokemonMenuButton();
    }

    public String getimageType() {
        stringtype = String.valueOf(type);
        return stringtype;
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

}