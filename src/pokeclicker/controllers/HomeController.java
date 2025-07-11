package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private ImageView itemregistrationimg, abilityregistrationimg, pokemonregistrationimg, clickerimg, logoImgView;
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

        Image itemregistration = new Image(getClass().getResource("../../img/itemregistration3.png").toExternalForm());
        Image abilityregistration = new Image(getClass().getResource("../../img/abilityregistration2.png").toExternalForm());
        Image pokemonregistration = new Image(getClass().getResource("../../img/pokemonregistration4.png").toExternalForm());
        Image clicker = new Image(getClass().getResource("../../img/clicker2.png").toExternalForm());
        Image logoImage = new Image(getClass().getResource("../../img/logo.png").toExternalForm());
        
        itemregistrationimg.setImage(itemregistration);
        abilityregistrationimg.setImage(abilityregistration);
        pokemonregistrationimg.setImage(pokemonregistration);
        clickerimg.setImage(clicker);
        logoImgView.setImage(logoImage);
    }

    @FXML
    // IMAGE POSITION ADJUSTMENT METHODS
    private void selectImage(ImageView imageView) {
        this.selectedImage = imageView;
        this.x = imageView.getLayoutX();
        this.y = imageView.getLayoutY();
        System.out.println("Selected image. x: " + x + ", y: " + y);
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
    private void registrationswitch(MouseEvent event) {

        pokeclicker.util.SceneSwitcher.switchToPokemonRegistration(event);

    }

    @FXML
    public void abilitybutton(MouseEvent event) {

        pokeclicker.util.SceneSwitcher.switchToAbilityRegistration(event, SceneSwitcher.getCurrentUsername());
    }

    @FXML
    public void itembutton(MouseEvent event) {

        pokeclicker.util.SceneSwitcher.switchToItemRegistration(event);

    }

    @FXML
    public void clickerButton(MouseEvent event) {

        pokeclicker.util.SceneSwitcher.switchToClicker(event);

    }

@FXML
private void handleGlow(MouseEvent event) {
    ImageView img = (ImageView) event.getSource();
    DropShadow glow = new DropShadow();
    glow.setColor(Color.web("#39d1d1"));
    glow.setRadius(30);
    glow.setSpread(0.5);
    img.setEffect(glow);
}
@FXML
private void handleGlowExit(MouseEvent event) {
    ImageView img = (ImageView) event.getSource();
    img.setEffect(null);
}

}
