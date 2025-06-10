package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {

    @FXML
    Label nameLable;

    @Override // This method is called by FXMLLoader after @FXML fields are injected
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ProfileController initialized. nameLable: " + nameLable);
        if (nameLable != null) {
            nameLable.setText("Debug: Initialized"); // Set a default text to confirm it's working
        }
        
    }
    public void displayName(String username)
    {
        nameLable.setText("Welcome, " + username);
    }
    
}
