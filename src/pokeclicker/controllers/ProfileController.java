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
public class ProfileController implements Initializable {

    private double x = 0;
    private double y = 0;

    private ImageView selectedImage; // Currently selected image

    @FXML private ImageView pokeballimg;
    @FXML private ImageView homeimg;
    @FXML private ImageView profileimg;
    @FXML private ImageView shopimg;
    @FXML private Rectangle lowerrectangle;
    @FXML private Label textid;
    @FXML private Label trainerid;
    private boolean ispressed = false;
    @FXML private Button PC;
    @FXML private Button shop;
    @FXML private Button home;
    @FXML private Button profile;
    @FXML private Line longline;
    @FXML private Line shortline1;
    @FXML private Line shortline2;
    @FXML private Line shortline3;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ProfileController initialized");

        Image image1 = new Image(getClass().getResource("/img/pokeballicon.png").toExternalForm(), true);
        pokeballimg.setImage(image1);
        pokeballimg.setPreserveRatio(true);
        pokeballimg.setFitWidth(60);

        Image image2 = new Image(getClass().getResource("/img/houseicon.png").toExternalForm());
        homeimg.setImage(image2);
        homeimg.setPreserveRatio(true);
        homeimg.setFitWidth(65);
        Image image3 = new Image(getClass().getResource("/img/profileicon.png").toExternalForm());
        profileimg.setImage(image3);
        profileimg.setPreserveRatio(true);
        profileimg.setFitWidth(80);
        Image image4 = new Image(getClass().getResource("/img/shopicon.png").toExternalForm());
        shopimg.setImage(image4);
        shopimg.setPreserveRatio(true);
        shopimg.setFitWidth(95);
        // Optionally select default image
        selectImage(pokeballimg);
        trainerid.setPrefWidth(300);
        trainerid.setText("Trainer ID: 000001"); // Example trainer ID
        
    }

    public void displayName(String username) {
        if (textid != null) {
            textid.setText("Welcome, " + username);
        }
    }

  

    // SCENE NAVIGATION METHODS (INCOMPLETE)
    @FXML
    private void PC(ActionEvent event) {
        System.out.println("PC clicked");
        selectImage(pokeballimg);
        ispressed = false;
    }

    @FXML
    private void shop(ActionEvent event) {
        System.out.println("Shop clicked");
        selectImage(shopimg);
        ispressed = false;
    }

    @FXML
    private void home(ActionEvent event) {
        System.out.println("Home clicked");
        selectImage(homeimg);
        ispressed = false;
    }

    @FXML
    private void profile(ActionEvent event) {
        System.out.println("Profile clicked");
        selectImage(profileimg);
        ispressed = false;
    }

    private void selectImage(ImageView imageView) {
        this.selectedImage = imageView;
        this.x = imageView.getLayoutX();
        this.y = imageView.getLayoutY();
        System.out.println("Selected image. x: " + x + ", y: " + y);
    }

    // IMAGE POSITION ADJUSTMENT METHODS
    public void up(ActionEvent e) {
        if (ispressed == true) {
        longline.setLayoutY(longline.getLayoutY() - 5);
        shortline1.setStartY(shortline1.getStartY() - 5);
        shortline2.setStartY(shortline2.getStartY() - 5);
        shortline3.setStartY(shortline3.getStartY() - 5);
    lowerrectangle.setLayoutY(lowerrectangle.getLayoutY() - 5);
        System.out.println("longline layout y: " + longline.getLayoutY());
        System.out.println("shortline layout y: " + shortline1.getStartY());
        System.out.println("shortline2 layout y: " + shortline2.getStartY());
        System.out.println("shortline3 layout y: " + shortline3.getStartY());
    } else {
        
        
        if (selectedImage == null) return;
        y -= 5;
        selectedImage.setLayoutY(y);
        System.out.println("layout y: " + selectedImage.getLayoutY());
    }
    }
public void down(ActionEvent e) {
    if (ispressed == true) {
        longline.setLayoutY(longline.getLayoutY() + 5);
        shortline1.setStartY(shortline1.getStartY() + 5);
        shortline2.setStartY(shortline2.getStartY() + 5);
        shortline3.setStartY(shortline3.getStartY() + 5);
        lowerrectangle.setLayoutY(lowerrectangle.getLayoutY() + 5);
        System.out.println("longline layout y: " + longline.getLayoutY());
        System.out.println("shortline layout y: " + shortline1.getStartY());
        System.out.println("shortline2 layout y: " + shortline2.getStartY());
        System.out.println("shortline3 layout y: " + shortline3.getStartY());
        System.out.println("lowerrectangle layout y: " + lowerrectangle.getLayoutY());
    } else {
        if (selectedImage == null) return;
        y += 5;
        selectedImage.setLayoutY(y);
        System.out.println("layout y: " + selectedImage.getLayoutY());
    }
}

    public void left(ActionEvent e) {
        if (selectedImage == null) return;
        x -= 5;
        selectedImage.setLayoutX(x);
        System.out.println("layout x: " + selectedImage.getLayoutX());
    }

    public void right(ActionEvent e) {
        if (selectedImage == null) return;
        x += 5;
        selectedImage.setLayoutX(x);
        System.out.println("layout x: " + selectedImage.getLayoutX());
    }
     
    public void lines(ActionEvent e) {
       ispressed = true;
    }
}




