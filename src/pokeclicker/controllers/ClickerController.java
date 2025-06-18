package pokeclicker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ClickerController implements Initializable{
    @FXML
    private Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Entrei no Clicker");
        button.setText("Clicker!");
    }
}
