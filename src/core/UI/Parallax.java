package core.UI;

import javafx.scene.layout.GridPane;

import static core.welcome.WelcomeScreenDefaults.screenHeight;
import static core.welcome.WelcomeScreenDefaults.screenWidth;

public interface Parallax {
    default void parallax(double x, double y, GridPane gp){
        double mouseDistX = (x - screenWidth() / 2);
        double mouseDistY = (y - screenHeight() / 2);

        gp.setLayoutX(mouseDistX / 15);
        gp.setLayoutY(mouseDistY / 10);
    }
}
