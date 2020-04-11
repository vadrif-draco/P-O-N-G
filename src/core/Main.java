package core;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage = new WelcomeScreen(true); // Launches welcome screen
    }
    // Such a clean empty class, heh.
}