package core;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static core.debugSystem.*;

class WelcomeScreen extends Stage implements WelcomeScreenDefaults {


    WelcomeScreen(boolean debugging) {
        WelcomeScreenBar leftBar = new WelcomeScreenBar("left");
        WelcomeScreenBar rightBar = new WelcomeScreenBar("right");
        WelcomeScreenBall ball = new WelcomeScreenBall();

        Button startGame = new Button("Oh dear, oh dear. Gorgeous.");

        Pane pane = new Pane();

        pane.getChildren().addAll(leftBar, rightBar, ball, startGame);
        if (debugging) {
            pane.getChildren().add(pseudoConsole);
            startDebugging();
        }
        pane.setStyle("-fx-background-size: stretch; -fx-background-color: #404040;");


        setScene(new Scene(pane,
                Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
        initStyle(StageStyle.UNDECORATED);
        setFullScreen(true);
        setMaximized(true);
        show();
        startGame.setOnAction(e -> {
            println("");
            refreshConsole();
            new SFX("zawarudo", 1, 1.27);
            ball.moveBall(leftBar, rightBar);
        });
    }
}
