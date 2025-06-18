package pokeclicker.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pokeclicker.controllers.ProfileController;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;

public class InitialController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Label titleLabel;
    @FXML
    Label exceptionLabel;
    @FXML
    private TextField inputField;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        titleLabel.setText("Welcome, trainer!\n\nTo begin your journey, enter your name and press \"Start\"");
        exceptionLabel.setText("");
    }

    public void getUsername(ProfileController profileController) throws UnexpectedException
    {
        String username = inputField.getText().trim();
        if (username == "")
        {
            exceptionLabel.setText("Empty username!");
            throw new UnexpectedException("Empty username!");
        }
try {
        User user = UserManager.createUser(username); // Create and set current user
        profileController.displayName(username);      // Optionally update UI
        profileController.setCurrentUser(user);
    } catch (IllegalArgumentException e) {
        exceptionLabel.setText(e.getMessage());
        throw new UnexpectedException(e.getMessage());
    }
           
       
         
     
       }
    

    public void switchToProfile(ActionEvent event) throws IOException
    {
        String username = inputField.getText().trim();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pokeclicker/views/profileScene.fxml"));
        Parent root = loader.load();
        ProfileController profileController = loader.getController();
        
    
        getUsername(profileController);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        String cssPath = this.getClass().getResource("/css/profileScene.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        stage.setScene(scene);
        stage.show();
    }

}
