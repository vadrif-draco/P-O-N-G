package core.game;


import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static core.DebugSystem.println;
import static core.welcome.WelcomeScreenDefaults.screenHeight;
import static core.welcome.WelcomeScreenDefaults.screenWidth;


public class GamePlayStage extends Stage {

    static boolean debugging = false;

    private Pane pane = new Pane();

    private Ball ball1 = new Ball();
    private Bar leftBar = new Bar();
    private Bar rightBar = new Bar();

    //takes the previous Stage to be able to navigate between them
    public GamePlayStage(Stage callerStage, boolean globalDebugging) {
        this();
        debugging = globalDebugging;
        if (debugging) this.setOpacity(0.75);
        if (callerStage != null)
            callerStage.hide();
    }

    public GamePlayStage() {
        Scene scene = new Scene(pane,
                Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());

        setScene(scene);
        initBackground();
        createGameSprites();
        show();

        Player player1 = new MousePlayer(rightBar, scene);
        Player player2 = new KeyboardPlayer(leftBar, scene);

        GameDriver gd = new GameDriver(ball1, player1, player2, pane);
        scene.setCursor(Cursor.NONE);

    }


    private void initBackground() {

        pane.setStyle("-fx-background-size: stretch; -fx-background-color: #000000;");
        setMaximized(true);
        initStyle(StageStyle.UNDECORATED);   // Fullscreen Property

//        Circle circle = new Circle();
//        circle.setRadius(GameDefaults.SCREEN_HEIGHT / 2 / 1.618);
//        circle.setCenterX(GameDefaults.SCREEN_WIDTH / 2);
//        circle.setCenterY(GameDefaults.SCREEN_HEIGHT / 2);
//        circle.setStroke(Color.WHITE);
//        circle.setStrokeWidth(3);
//        circle.setFill(Color.valueOf("#404040"));

        Line line = new Line();
        line.setStartX(screenWidth() / 2);
        line.setEndX(screenWidth() / 2);
        line.setStartY(0);
        line.setEndY(GameDefaults.SCREEN_HEIGHT);
        line.setFill(Color.WHITE);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(5);
        line.setOpacity(0.75);


//        pane.getChildren().add(circle);
        pane.getChildren().add(line);
//        pane.getChildren().add(leftScore);
    }

    /**
     * Creates Playable objects on the screen
     */
    private void createGameSprites() {

        ball1.setTranslateX(GameDefaults.SCREEN_WIDTH / 2);
        ball1.setTranslateY(GameDefaults.SCREEN_HEIGHT / 2);

        leftBar.setTranslateX(200);
        leftBar.setTranslateY(GameDefaults.SCREEN_HEIGHT / 2 - leftBar.getHeight() / 2);

        rightBar.setTranslateX(GameDefaults.SCREEN_WIDTH - GameDefaults.BAR_WIDTH - 200);
        rightBar.setTranslateY(GameDefaults.SCREEN_HEIGHT / 2 - rightBar.getHeight() / 2);

        pane.getChildren().addAll(ball1, leftBar, rightBar);
    }
}
