package pokeclicker.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import pokeclicker.controllers.ProfileController;
import pokeclicker.controllers.ShopController;
import pokeclicker.controllers.AbilityController;
import pokeclicker.controllers.HomeController;
import pokeclicker.manager.UserManager;
import pokeclicker.model.User;

import java.io.IOException;

public class SceneSwitcher {

    private static String currentUsername;

    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    private User user;

    public static void switchToProfile(ActionEvent event, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/pokeclicker/views/profileScene.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            // Pass the username to ProfileController
            if (username != null) {
                profileController.setUsername(username);

            }
            profileController.setCurrentUser(UserManager.getUser(username));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/profileScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToHome(ActionEvent event, String username) {
        setCurrentUsername(username);

        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/pokeclicker/views/homeScene.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();

            // Pass the username to HomeController
            if (username != null) {
                homeController.setUsername(username);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/homeScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);

            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToShop(ActionEvent event, String username) {
        setCurrentUsername(username);

        try {

            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/pokeclicker/views/shopScene.fxml"));
            Parent root = loader.load();
            ShopController shopController = loader.getController();
            username = getCurrentUsername();
            if (username == null) {
                System.out.println("❗ Username is null in switchToShop");
            }
            User user = UserManager.getUser(username);
            shopController.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/shopScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToPokemonRegistration(MouseEvent event) {
        try {
            Parent root = FXMLLoader
                    .load(SceneSwitcher.class.getResource("/pokeclicker/views/pokemonregistration.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/pokemonregistration.css").toExternalForm();

            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static FXMLLoader switchToSceneWithController(Stage stage, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
        return loader;
    }

    public static void switchToAbilityRegistration(MouseEvent event, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneSwitcher.class.getResource("/pokeclicker/views/abilityregistration.fxml"));
            Parent root = loader.load();
            AbilityController controller = loader.getController();
            controller.setUsername(username); // <-- set the username here

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/abilityregistration.css").toExternalForm();

            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToItemRegistration(MouseEvent event) {
        try {
            Parent root = FXMLLoader
                    .load(SceneSwitcher.class.getResource("/pokeclicker/views/itemregistration.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/itemregistration.css").toExternalForm();

            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToClicker(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource("/pokeclicker/views/clickerScene.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/clickerScene.css").toExternalForm();

            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void switchToPC(ActionEvent event, String username) {
        setCurrentUsername(username);

        try {

            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/pokeclicker/views/pcScene.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/pcScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void switchToInitial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/pokeclicker/views/initialScene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String cssPath = SceneSwitcher.class.getResource("/css/initialScene.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}