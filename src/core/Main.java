package core;

import core.game.GamePlayStage;
import core.welcome.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage = new WelcomeScreen(true); // Launches welcome screen
        //stage = new GamePlayStage();
    }
    // Such a clean empty class, heh.
}