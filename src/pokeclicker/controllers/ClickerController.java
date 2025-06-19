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
import pokeclicker.game.clicker.Clicker;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;
import pokeclicker.util.SceneSwitcher;

public class ClickerController implements Initializable{
    @FXML
    private Button clickButton;
    @FXML
    private Button backButton;
    @FXML
    private ImageView gholdengoViewer;
    @FXML
    private ImageView ourPokemonViewer;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label clickLabel;

    private Clicker clicker;

    private User user;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = UserManager.getUser(SceneSwitcher.getCurrentUsername());
        clicker = new Clicker(user);


        moneyLabel.setText("Money: " + user.getMoney());
        clickLabel.setText("Clicks: " + clicker.getTotalClicks());

        backButton.setText("Back");
        clickButton.setText("Click!");

        Image gholdengo = new Image(getClass().getResource("/img/gholdengo.gif").toExternalForm());
        Image cyndaquil = new Image(getClass().getResource("/img/cyndaquilBack.gif").toExternalForm());

        gholdengoViewer.setImage(gholdengo);
        gholdengoViewer.setFitWidth(240);
        gholdengoViewer.setFitHeight(190);

        ourPokemonViewer.setImage(cyndaquil);
        ourPokemonViewer.setFitWidth(200);
        ourPokemonViewer.setFitHeight(100);
    }

    @FXML
    public void clickButton(ActionEvent event) {
        Image gholdengoMoney = new Image(getClass().getResource("/img/gholdengoMoney.gif").toExternalForm());
        
        gholdengoViewer.setImage(gholdengoMoney);
        gholdengoViewer.setFitWidth(240);
        gholdengoViewer.setFitHeight(190);
        clicker.registerClick();
        
        moneyLabel.setText("Money: " + user.getMoney());
        clickLabel.setText("Clicks: " + clicker.getTotalClicks());
        System.out.println("Money of the man: " + user.getMoney());
    }

    @FXML
    public void backButton(ActionEvent event) {

        UserManager.updateUser(user);
        SceneSwitcher.switchToHome(event, SceneSwitcher.getCurrentUsername());
    }

}
