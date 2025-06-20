package pokeclicker.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;

public class InitialController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView snorlaxViewer;
    @FXML
    private ImageView cursorViewer;
    @FXML
    Label titleLabel;
    @FXML
    Label exceptionLabel;
    @FXML
    private TextField inputField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font minecraftFont = null;
        try {
            URL fontUrl = getClass().getResource("/fonts/C&C Red Alert [INET].ttf"); // nova fonte de exemplo, podemos
                                                                                     // trocar
            InputStream fontStream = fontUrl.openStream();
            minecraftFont = Font.loadFont(fontStream, 40);
            fontStream.close();
        } catch (Exception e) { // Catch more general Exception for IO issues
            e.printStackTrace();
        }

        inputField.setPromptText("Insert your name here");

        titleLabel.setText("Welcome, trainer!\nTo begin your journey, enter your name and press \"Start\"");
        exceptionLabel.setText("");
        Image logo1 = new Image(getClass().getResource("/img/logo.png").toExternalForm());
        logo.setImage(logo1);
        logo.setFitHeight(350);
        logo.setFitWidth(350);
        logo.setLayoutX(130);
        logo.setLayoutY(-100);

        Image snorlaxImage = new Image(getClass().getResource("/img/snorlax.gif").toExternalForm());
        snorlaxViewer.setImage(snorlaxImage);
        snorlaxViewer.setFitHeight(125);
        snorlaxViewer.setFitWidth(125);
        snorlaxViewer.setRotate(15);

        Image cursorImage = new Image(getClass().getResource("/img/linkcursor.gif").toExternalForm());
        cursorViewer.setImage(cursorImage);
        cursorViewer.setFitHeight(30);
        cursorViewer.setFitWidth(30);

        inputField.setAlignment(Pos.CENTER);

    }

    public void getUsername(ProfileController profileController) throws UnexpectedException {
        String username = inputField.getText().trim();
        if (username.isEmpty()) {
            exceptionLabel.setText("Empty username!");
            throw new UnexpectedException("Empty username!");

        }
        try {
            User user = UserManager.createUser(username); // Create and set current user
            profileController.displayName(username); // Optionally update UI
            profileController.setCurrentUser(user);

        } catch (IllegalArgumentException e) {
            exceptionLabel.setText(e.getMessage());
            throw new UnexpectedException(e.getMessage());
        }

    }

    public void switchToProfile(ActionEvent event) throws IOException {
        String username = inputField.getText().trim();

        if (username.isEmpty()) {
            exceptionLabel.setText("Empty username!");
            return; // Não troca de tela, deixa tentar de novo
        }

        User user;
        try {
            user = UserManager.createUser(username); // Tenta criar novo usuário
        } catch (IllegalArgumentException e) {
            // Se já existe, faz login normalmente
            if ("Username already exists".equals(e.getMessage())) {
                user = UserManager.getUser(username);
            } else {
                exceptionLabel.setText(e.getMessage());
                return; // Não troca de tela, deixa tentar de novo
            }
        }

        // Troca de tela normalmente, passando o usuário (novo ou existente)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pokeclicker/views/profileScene.fxml"));
        Parent root = loader.load();
        ProfileController profileController = loader.getController();
        profileController.displayName(username);
        profileController.setCurrentUser(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String cssPath = this.getClass().getResource("/css/profileScene.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        stage.setScene(scene);
        stage.show();
    }

    public void setFavoritePokemon() {

    }

}
