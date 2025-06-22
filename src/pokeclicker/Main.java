package pokeclicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pokeclicker.database.UserDB;
import pokeclicker.database.AbilityDB;
import pokeclicker.database.PokemonDB;
import pokeclicker.database.ItemDB;
import pokeclicker.database.PokemonAbilityDB;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/pokeclicker/views/initialScene.fxml"));
            Scene scene = new Scene(root);

            String cssPath = this.getClass().getResource("/css/initialScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set window icon
            Image windowIcon = new Image(getClass().getResource("/img/windowIcon.png").toExternalForm());

            stage.getIcons().add(windowIcon);
            scene.setFill(Color.BLUE);
            // Set title
            stage.setTitle("PokeClicker - Click and evolve your Pokemons!");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw e;
        }

    }

    public static void main(String[] args) {
        UserDB.createUserTable();
        AbilityDB.createAbilityTable();
        PokemonDB.createPokemonTable();
        PokemonAbilityDB.createPokemonAbilityTable();
        ItemDB.createItemTable();

        launch(args);
    }
}