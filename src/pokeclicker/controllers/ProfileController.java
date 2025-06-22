package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import pokeclicker.model.User;
import pokeclicker.util.SceneIconUtil;
import pokeclicker.util.SceneSwitcher;

public class ProfileController implements Initializable {

    private double x = 0;
    private double y = 0;
    private ImageView selectedImage; // Currently selected image
    private boolean ispressed = false;
    @FXML
    private ImageView pokeballimg;
    @FXML
    private ImageView homeimg;
    @FXML
    private ImageView profileimg;
    @FXML
    private ImageView shopimg;
    @FXML
    private ImageView favPokemonimg;
    @FXML
    private ImageView moneydisplay;
    @FXML
    private Rectangle lowerrectangle;
    @FXML
    private Label textid;
    @FXML
    private Label trainerid;
    @FXML
    private Label moneydisplaylabel;
    @FXML
    private Label favpokemonlabel;
    @FXML
    private Button PC;
    @FXML
    private Button shop;
    @FXML
    private Button home;
    @FXML
    private Button profile;
    @FXML
    private Line longline;
    @FXML
    private Line shortline1;
    @FXML
    private Line shortline2;
    @FXML
    private Line shortline3;
    @FXML
    private Rectangle PCrectangle;
    @FXML
    private Rectangle shoprectangle;
    @FXML
    private Rectangle homerectangle;
    @FXML
    private Rectangle profilerectangle;

    public void setUsername(String username) {
        this.username = username;
        if (textid != null) {
            textid.setText("Welcome, " + username);
            favpokemonlabel.setText("Favorite pokemon:");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ProfileController initialized");
        SceneIconUtil.setupSelectionBarImages(pokeballimg, homeimg, profileimg, shopimg);

        Image image6 = new Image(getClass().getResource("/img/money.png").toExternalForm());
        moneydisplay.setImage(image6);
        moneydisplay.setPreserveRatio(true);
        moneydisplay.setFitWidth(40);

        // Optionally select default image

        loadUserData();
        selectImage(pokeballimg);
        trainerid.setPrefWidth(300);
        trainerid.setText("Trainer ID: 000001"); // Example trainer ID
        // Example money display

    }

    private User currentUser;

    // Method to set the current user and load their data
    public void setCurrentUser(User user) {
        this.currentUser = user;
        loadUserData();
    }

    private void loadUserData() {
        if (currentUser != null) {
            moneydisplaylabel.setText(": " + currentUser.getMoney());

            if (currentUser.getFavoritePokemon() != null) {
                Image favpokemonimage = new Image(currentUser.getFavoritePokemon().getImagePath());
                favPokemonimg.setImage(favpokemonimage);
            }
        }
    }

    public void displayName(String username) {
        this.username = username;
        if (textid != null) {
            textid.setText("Welcome, " + username);
            favpokemonlabel.setText("Favorite pokemon:");
        }
    }

    @FXML
    private void moneydisplaybutton(ActionEvent event) {
        System.out.println("PC clicked");
        selectImage(moneydisplay);
        ispressed = false;
    }

    // SCENE NAVIGATION METHODS (INCOMPLETE)
    @FXML
    private void PC(ActionEvent event) {
        System.out.println("PC clicked");
        selectImage(pokeballimg);
        ispressed = false;
        PCrectangle.setFill(javafx.scene.paint.Color.RED);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);

        SceneSwitcher.switchToPC(event, username);
    }

    @FXML
    private void shop(ActionEvent event) {
        System.out.println("Shop clicked");
        selectImage(shopimg);
        ispressed = false;
        shoprectangle.setFill(javafx.scene.paint.Color.GREEN);
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
        SceneSwitcher.switchToShop(event, username);
    }

    private String username;

    @FXML
    private void home(ActionEvent event) {
        System.out.println("Home clicked");
        selectImage(homeimg);
        ispressed = false;
        homerectangle.setFill(javafx.scene.paint.Color.BLUE);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.TEAL);
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);
        SceneSwitcher.switchToHome(event, username);
    }

    @FXML
    private void profile(ActionEvent event) {
        System.out.println("Profile clicked");
        selectImage(profileimg);
        ispressed = false;
        homerectangle.setFill(javafx.scene.paint.Color.TEAL);
        PCrectangle.setFill(javafx.scene.paint.Color.TEAL);
        profilerectangle.setFill(javafx.scene.paint.Color.web("#7d5a94"));
        shoprectangle.setFill(javafx.scene.paint.Color.TEAL);

    }

    @FXML
    private void favpokemon(ActionEvent event) {
        selectImage(favPokemonimg);
    }

    private void selectImage(ImageView imageView) {
        this.selectedImage = imageView;
        this.x = imageView.getLayoutX();
        this.y = imageView.getLayoutY();
        System.out.println("Selected image. x: " + x + ", y: " + y);
    }
}