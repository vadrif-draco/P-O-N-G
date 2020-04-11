package core;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static core.DebugSystem.*;

class WelcomeScreen extends Stage implements WelcomeScreenDefaults {


    WelcomeScreen(boolean debugging) {
        Bar l = new Bar("left");
        Bar r = new Bar("right");
        Ball b = new Ball();
        WelcomeScreenSimulation backgroundSimulation = new WelcomeScreenSimulation(l, r, b);

        Button startGame = new Button("Oh dear, oh dear. Gorgeous.");

        Pane pane = new Pane();

        pane.getChildren().addAll(l, r, b, startGame);
        if (debugging) {
            pane.getChildren().add(pseudoConsole);
            startDebugging();
        }
        pane.setStyle("-fx-background-size: stretch; -fx-background-color: #404040;");

        setScene(new Scene(pane,
                Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
        initStyle(StageStyle.UNDECORATED);
//        setFullScreen(true);
        setMaximized(true);
        show();
        startGame.setOnAction(e -> {
            new SFX("zawarudo", 1, 1.27);
            if (!backgroundSimulation.isRunning()) backgroundSimulation.start();
            else {
                refreshConsole();
                System.out.println("Help me God");
                backgroundSimulation.resetAll();
                backgroundSimulation.start();
            }
        });
    }
}