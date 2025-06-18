package pokeclicker.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SceneIconUtil {
    public static void setupSelectionBarImages(ImageView pokeballimg, ImageView homeimg, ImageView profileimg, ImageView shopimg) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        Image image1 = new Image(cl.getResource("img/pokeballicon.png").toExternalForm(), true);
        pokeballimg.setImage(image1);
        pokeballimg.setPreserveRatio(true);
        pokeballimg.setFitWidth(60);

        Image image2 = new Image(cl.getResource("img/houseicon.png").toExternalForm());
        homeimg.setImage(image2);
        homeimg.setPreserveRatio(true);
        homeimg.setFitWidth(65);

        Image image3 = new Image(cl.getResource("img/profileicon.png").toExternalForm());
        profileimg.setImage(image3);
        profileimg.setPreserveRatio(true);
        profileimg.setFitWidth(80);

        Image image4 = new Image(cl.getResource("img/shopicon.png").toExternalForm());
        shopimg.setImage(image4);
        shopimg.setPreserveRatio(true);
        shopimg.setFitWidth(95);
    }
}