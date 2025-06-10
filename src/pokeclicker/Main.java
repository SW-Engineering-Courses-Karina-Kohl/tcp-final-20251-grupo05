package pokeclicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("views/initialScene.fxml"));
            Scene scene = new Scene(root);

            String cssPath = this.getClass().getResource("/css/initialScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set window icon
            Image windowIcon = new Image("/img/windowIcon.png");
            stage.getIcons().add(windowIcon);

            // Set title
            stage.setTitle("PokeClicker - Click and evolve your Pokemons!");

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw e;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}