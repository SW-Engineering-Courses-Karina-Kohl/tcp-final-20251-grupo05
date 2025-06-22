package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HomeController implements Initializable {
    private ImageView selectedImage;

    private double x = 0;
    private double y = 0;
    @FXML
    private ImageView pokeballimg, homebackground;
    @FXML
    private ImageView homeimg;
    @FXML
    private ImageView profileimg;
    @FXML
    private ImageView shopimg;
    @FXML
    private ImageView upperdesignimage;
    @FXML
    private ImageView lowerdesignimage;
    @FXML
    private ImageView abilityimage;
    @FXML
    private ImageView itemimage;

    @FXML
    private Rectangle PCrectangle;
    @FXML
    private Rectangle shoprectangle;
    @FXML
    private Rectangle homerectangle;
    @FXML
    private Rectangle profilerectangle;
    @FXML
    private Line longline;
    @FXML
    private Line shortline1;
    @FXML
    private Line shortline2;
    @FXML
    private Line shortline3;
    @FXML
    private Button PC;
    @FXML
    private Button shop;
    @FXML
    private Button home;
    @FXML
    private Button profile;
    @FXML
    private Label cadastropokemon;
    @FXML
    private Label clickerscene;
    @FXML
    private Label itemscene;
    @FXML
    private Label abilityscene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneIconUtil.setupSelectionBarImages(pokeballimg, homeimg, profileimg, shopimg);
        Image upperdesignimage1 = new Image(getClass().getResource("/img/upperdesignimage.png").toExternalForm());

        upperdesignimage.setImage(upperdesignimage1);
        upperdesignimage.setPreserveRatio(true);
        upperdesignimage.setFitWidth(420);
        Image lowerdesignimage1 = new Image(getClass().getResource("/img/lowerdesignimage.png").toExternalForm());
        lowerdesignimage.setImage(lowerdesignimage1);
        lowerdesignimage.setPreserveRatio(true);
        lowerdesignimage.setFitWidth(360);
        cadastropokemon.setText("Pokémon Registration");
        clickerscene.setText("Clicker");
        Image abilityImage = new Image(getClass().getResource("/img/abilityimage.png").toExternalForm());
        abilityimage.setImage(abilityImage);
        abilityimage.setFitWidth(240);
        abilityimage.setFitHeight(190);
        Image itemImage = new Image(getClass().getResource("/img/itemimage.png").toExternalForm());
        itemimage.setImage(itemImage);
        itemimage.setPreserveRatio(true);
        itemimage.setFitHeight(420);
    }

    @FXML
    // IMAGE POSITION ADJUSTMENT METHODS
    private void selectImage(ImageView imageView) {
        this.selectedImage = imageView;
        this.x = imageView.getLayoutX();
        this.y = imageView.getLayoutY();
        System.out.println("Selected image. x: " + x + ", y: " + y);
    }

    public void up(ActionEvent e) {

        if (selectedImage == null)
            return;
        y -= 5;
        selectedImage.setLayoutY(y);
        System.out.println("layout y: " + selectedImage.getLayoutY());
    }

    public void down(ActionEvent e) {

        if (selectedImage == null)
            return;
        y += 5;
        selectedImage.setLayoutY(y);
        System.out.println("layout y: " + selectedImage.getLayoutY());
    }

    public void left(ActionEvent e) {
        if (selectedImage == null)
            return;
        x -= 5;
        selectedImage.setLayoutX(x);
        System.out.println("layout x: " + selectedImage.getLayoutX());
    }

    public void right(ActionEvent e) {
        if (selectedImage == null)
            return;
        x += 5;
        selectedImage.setLayoutX(x);
        System.out.println("layout x: " + selectedImage.getLayoutX());
    }

    @FXML
    private void PC(ActionEvent event) {
        System.out.println("PC clicked");
        selectImage(pokeballimg);
        PCrectangle.setFill(javafx.scene.paint.Color.RED);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);

        SceneSwitcher.switchToPC(event, SceneSwitcher.getCurrentUsername());

    }

    @FXML
    private void shop(ActionEvent event) {
        System.out.println("Shop clicked");
        selectImage(shopimg);
        shoprectangle.setFill(javafx.scene.paint.Color.GREEN);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
        SceneSwitcher.switchToShop(event, username);
    }

    @FXML
    private void home(ActionEvent event) {
        System.out.println("Home clicked");
        selectImage(homeimg);
        homerectangle.setFill(javafx.scene.paint.Color.web("#39d1d1"));
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);

    }

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML

    private void profile(ActionEvent event) {
        System.out.println("Profile clicked");
        selectImage(profileimg);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.PURPLE);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);

        SceneSwitcher.switchToProfile(event, SceneSwitcher.getCurrentUsername());

    }

    @FXML
    private void registrationswitch(ActionEvent event) {

        pokeclicker.util.SceneSwitcher.switchToPokemonRegistration(event);

    }

    @FXML
    public void abilitybutton(ActionEvent event) {

        pokeclicker.util.SceneSwitcher.switchToAbilityRegistration(event);
    }

    @FXML
    public void itembutton(ActionEvent event) {

        pokeclicker.util.SceneSwitcher.switchToItemRegistration(event);

    }

    @FXML
    public void clickerButton(ActionEvent event) {

        pokeclicker.util.SceneSwitcher.switchToClicker(event);

    }

}
