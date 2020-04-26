package core.welcome;

import core.Soundtrack;
import core.UI.Parallax;
import core.game.GameDriver;
import core.game.GamePlayStage;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static core.DebugSystem.*;
import static core.welcome.WelcomeScreenDefaults.screenHeight;
import static core.welcome.WelcomeScreenDefaults.screenWidth;

public class WelcomeScreen extends Stage implements Parallax{

    public WelcomeScreen(boolean globalDebugging) {
        Bar l = new Bar("left");
        Bar r = new Bar("right");
        Ball b = new Ball();
        WelcomeScreenSimulation backgroundSimulation = new WelcomeScreenSimulation(l, r, b);

        EventHandler<MouseEvent> startGame = MouseEvent -> {
            backgroundSimulation.resetAll();
            new GamePlayStage(this, globalDebugging);
        };

        GridPane buttonsPane = new GridPane();
        buttonsPane.setVgap(10);

        Label startGameButton = new WelcomeScreenButton("Play", startGame);
        buttonsPane.addRow(0, startGameButton);
        // TODO should turn into a screen that shows different game modes
        // starts gameplay screen directly, for now.

        Label skinsAndBGsButton = new WelcomeScreenButton("Skins & Backgrounds", null);
        buttonsPane.addRow(1, skinsAndBGsButton);
        // TODO should turn into a screen that shows bar skins, ball skins, backgrounds

        Label achievementsButton = new WelcomeScreenButton("Achievements", null);
        buttonsPane.addRow(2, achievementsButton);
        // TODO should turn into a screen that shows achievements... lol

        Label settingsButton = new WelcomeScreenButton("Settings", null);
        buttonsPane.addRow(3, settingsButton);
        // TODO do I have to type this out

        Label exitButton = new WelcomeScreenButton("Exit", e -> {
            new Soundtrack(this);
        });
        buttonsPane.addRow(4, exitButton);

        buttonsPane.minWidthProperty().bind(screenWidth);
        buttonsPane.minHeightProperty().bind(screenHeight);
        buttonsPane.setAlignment(Pos.CENTER);

        Label lbl = new Label("Press SPACE to reset simulation\n" +
                "(Debug mode only) Hold SHIFT to slow ball down in gameplay");
        lbl.setStyle("-fx-alignment: center; -fx-text-fill: #FF00FF; -fx-font-size: 32;");

        Pane frontPane = new Pane();
        frontPane.getChildren().addAll(lbl, buttonsPane);

        Pane bgSimulationPane = new Pane();
        bgSimulationPane.getChildren().addAll(l, r, b);
        bgSimulationPane.setMinSize(screenWidth(), screenHeight());
        bgSimulationPane.setStyle("-fx-background-size: stretch; -fx-background-color: #404040;");
        bgSimulationPane.setCache(true);
        bgSimulationPane.setCacheHint(CacheHint.QUALITY);

        Pane rootPane = new Pane();
        rootPane.getChildren().addAll(bgSimulationPane, frontPane);
        rootPane.setStyle("-fx-background-size: stretch; -fx-background-color: #404040;");

        Scene welcomeScene = new Scene(rootPane, screenWidth(), screenHeight());
        welcomeScene.setFill(Color.BLACK);

        setScene(welcomeScene);
        initStyle(StageStyle.UNDECORATED);
        //setFullScreen(true);
        setMaximized(true);

        if (!backgroundSimulation.isRunning()) backgroundSimulation.start();
        else {
            refreshConsole();
            backgroundSimulation.resetAll();
            backgroundSimulation.start();
        }

        bgSimulationPane.requestFocus();
        bgSimulationPane.setOnKeyPressed(eh -> {
            if (eh.getCode() == KeyCode.SPACE) {
                backgroundSimulation.resetAll();
                backgroundSimulation.start();
            }
        });

        if (globalDebugging) {
            this.setOpacity(0.75);
            bgSimulationPane.getChildren().add(pseudoConsole);
            startDebugging();
        } else {

            Timeline bgSimulationFadein = new Timeline();
            // Background simulation fade in mechanism start

            // INITIAL STATE
            ColorAdjust adj = new ColorAdjust(0, -1, -1, 1);// Hue, Saturation, Brightness, Contrast
            GaussianBlur blur = new GaussianBlur(32);
            adj.setInput(blur);
            bgSimulationPane.setEffect(adj);
            // END OF INITIAL STATE

            // NEXT STATES (in keyframed values)
            KeyFrame bgSimK1 = new KeyFrame(Duration.millis(2000),
                    new KeyValue(blur.radiusProperty(), 8),
                    new KeyValue(adj.saturationProperty(), -0.8),
                    new KeyValue(adj.brightnessProperty(), -0.95),
                    new KeyValue(adj.contrastProperty(), 0.75)
            );

            KeyFrame bgSimK2 = new KeyFrame(Duration.millis(3500),
                    new KeyValue(blur.radiusProperty(), 2),
                    new KeyValue(adj.saturationProperty(), 0),
                    new KeyValue(adj.brightnessProperty(), -0.5),
                    new KeyValue(adj.contrastProperty(), 0.5)
            );

            KeyFrame bgSimK3 = new KeyFrame(Duration.millis(4000),
                    new KeyValue(blur.radiusProperty(), 2),
                    new KeyValue(adj.saturationProperty(), 0),
                    new KeyValue(adj.brightnessProperty(), -0.5),
                    new KeyValue(adj.contrastProperty(), 0.5)
            );

            KeyFrame bgSimK4 = new KeyFrame(Duration.millis(5000),
                    new KeyValue(blur.radiusProperty(), 32, Interpolator.EASE_OUT),
                    new KeyValue(adj.saturationProperty(), 0, Interpolator.EASE_OUT),
                    new KeyValue(adj.brightnessProperty(), -0.25, Interpolator.EASE_OUT),
                    new KeyValue(adj.contrastProperty(), 0.1, Interpolator.EASE_OUT)
            );

            bgSimulationPane.setCacheHint(CacheHint.SPEED);
            bgSimulationFadein.getKeyFrames().addAll(bgSimK1, bgSimK2, bgSimK3, bgSimK4);

            bgSimulationFadein.play(); // makes pane change effective periodically

            // Background simulation fade in mechanism end

            Timeline buttonsFadein = new Timeline();
            // Buttons fade in mechanism start

            KeyFrame buttonsK1 = new KeyFrame(Duration.millis(0),
                    new KeyValue(buttonsPane.scaleXProperty(), 0.01),
                    new KeyValue(buttonsPane.scaleYProperty(), 0.01),
                    new KeyValue(buttonsPane.opacityProperty(), 0)
            );

            KeyFrame buttonsK2 = new KeyFrame(Duration.millis(4200), //blazeit
                    new KeyValue(buttonsPane.scaleXProperty(), 0.01, Interpolator.EASE_BOTH),
                    new KeyValue(buttonsPane.scaleYProperty(), 0.01, Interpolator.EASE_BOTH),
                    new KeyValue(buttonsPane.opacityProperty(), 0.1)
            );

            KeyFrame buttonsK3 = new KeyFrame(Duration.millis(4500),
                    new KeyValue(buttonsPane.scaleXProperty(), 1.25, Interpolator.EASE_BOTH),
                    new KeyValue(buttonsPane.scaleYProperty(), 1.25, Interpolator.EASE_BOTH),
                    new KeyValue(buttonsPane.opacityProperty(), 0.1)
            );

            KeyFrame buttonsK4 = new KeyFrame(Duration.millis(5000),
                    new KeyValue(buttonsPane.scaleXProperty(), 1, Interpolator.EASE_IN),
                    new KeyValue(buttonsPane.scaleYProperty(), 1, Interpolator.EASE_IN),
                    new KeyValue(buttonsPane.opacityProperty(), 1)
            );

            buttonsFadein.getKeyFrames().addAll(buttonsK1, buttonsK2, buttonsK3, buttonsK4);
            buttonsFadein.play();
        }

        frontPane.setOnMouseMoved(e -> {
            parallax(e.getX(), e.getY(), buttonsPane);
        });
        show();
    }
}