package core.game;

import core.SFX;
import core.Soundtrack;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static core.DebugSystem.println;
import static core.welcome.WelcomeScreenDefaults.screenHeight;
import static core.welcome.WelcomeScreenDefaults.screenWidth;


public class GameDriver {

    private Ball ball1;
    private Player p1;
    private Player p2;
    private Pane pane;
    private boolean started = false;
    private Label clickLabel;
    private Label leftScore;
    private Label rightScore;
    private static Label timer;
    private double currentTime = 5;

    // Debugging variables
    private boolean shiftHeld = false;
    private final double initSpeed = GameDefaults.BALL_SPEED.getValue();
    // End of debugging variables

    public GameDriver(Ball ball1, Player p1, Player p2, Pane pane) {
        this.ball1 = ball1;
        this.p1 = p1;
        this.p2 = p2;
        this.pane = pane;
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(1 / GameDefaults.FRAME_RATE),
                new FrameHandler());

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        clickLabel = new Label("CLICK TO START");
        clickLabel.setLayoutX(GameDefaults.SCREEN_WIDTH / 2 - 175); //TODO FIX ORIENTATION
        clickLabel.setLayoutY(GameDefaults.SCREEN_HEIGHT / 2);
        clickLabel.setFont(Font.font("Fira Code", 48));
        clickLabel.setTextFill(Color.WHITE);
        clickLabel.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(clickLabel);

        int LS = p1.getScore();
        leftScore = new Label(String.valueOf(LS));
        leftScore.setStyle("-fx-font-size: 150; -fx-text-fill: WHITE;");
        leftScore.setLayoutX(screenWidth() * 0.2);
        leftScore.setLayoutY(screenHeight() / 12);

        int RS = p2.getScore();
        rightScore = new Label(String.valueOf(RS));
        rightScore.setStyle("-fx-font-size: 150; -fx-text-fill: WHITE");
        rightScore.setLayoutX(screenWidth() * 0.8);
        rightScore.setLayoutY(screenHeight() / 12);

        //TODO FIX THE ORIENTATION

        pane.getChildren().add(leftScore);
        pane.getChildren().add(rightScore);

        timer = new Label(String.valueOf(Math.floor(currentTime)));
        timer.setStyle("-fx-font-size: 150; -fx-text-fill: WHITE");
        timer.setLayoutX(screenWidth() * 0.5 - 100);
        timer.setLayoutY(screenHeight() / 12);

        pane.getChildren().add(timer);

        //Start/Pause on Mouse Click
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent keyEvent) {
                started = !started;
            }
        });
    }


    // Debugging partition
    private Soundtrack timeIsNoMore;
    private EventHandler<KeyEvent> handleKeyPress = e -> {
        if (e.getCode() == KeyCode.SHIFT) {
            shiftHeld = true;
            new SFX("zawarudo_start.mp3", 1); // can't live without this :^)
            timeIsNoMore = new Soundtrack("awakening.mp3", -1);
        }
    };

    private EventHandler<KeyEvent> handleKeyRelease = e -> {
        if (e.getCode() == KeyCode.SHIFT) {
            shiftHeld = false;
            new SFX("zawarudo_end.mp3", 1); // can't live without this :^)
            timeIsNoMore.stop();
        }
    };
    // End of debugging partition

    //Main Game LOOOOOOP
    // TODO Levels ands other stuff
    private class FrameHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

            //Move the ball according to its velocity
            if (started) {

                //TODO Random initial ball direction

                ball1.setTranslateX(ball1.getTranslateX() + ball1.dx.getValue());
                ball1.setTranslateY(ball1.getTranslateY() + ball1.dy.getValue());

                clickLabel.setVisible(false);
            } else {
                clickLabel.setVisible(true);
            }

            //Screen Bounds Collision
            if (ball1.getTranslateY() <= 0 || ball1.getTranslateY() >= GameDefaults.SCREEN_HEIGHT)
                ball1.dy.setValue(ball1.dy.multiply(-1).getValue());


            //Player 1 Missed
            if (ball1.getTranslateX() > GameDefaults.SCREEN_WIDTH + ball1.getRadius()) {
                started = false;
                p2.setScore(p2.getScore() + 1);
                leftScore.setText(String.valueOf(p2.getScore()));
                reset();
            }

            //Player 2 Missed
            if (ball1.getTranslateX() < -ball1.getRadius()) {
                started = false;
                p1.setScore(p1.getScore() + 1);
                rightScore.setText(String.valueOf(p1.getScore()));
                reset();
            }


            // Bar Collision COOL RIGHT !!!
            if (p1.getBar().getBoundsInParent().intersects(ball1.getBoundsInParent())) {
                ball1.setTranslateX(p1.getBar().getTranslateX() - ball1.getRadius());
                ball1.dx.setValue(ball1.dx.multiply(-1).getValue());
            }
            if (p2.getBar().getBoundsInParent().intersects(ball1.getBoundsInParent())) {
                ball1.setTranslateX(p2.getBar().getTranslateX() + p2.getBar().getWidth() + ball1.getRadius());
                ball1.dx.setValue(ball1.dx.multiply(-1).getValue());
            }

            //TODO Ball bounce direction depends on where it hits the bar

            if (started) {
                currentTime -= 1.0 / 60;
                timer.setText(String.valueOf(Math.floor(currentTime) + 1));
                if (currentTime <= 0) {
                    started = !started;
                    reset();
                }
            }
            // Debugging Partition

            // Slow ball down if SHIFT key held (for debugging purposes, for now.
            // Can be reused later for twists/powerups)
            if (GamePlayStage.debugging) {
                AnimationTimer ballSpeedAnim = new AnimationTimer() {

                    @Override
                    public void handle(long l) {
                        if (shiftHeld && GameDefaults.BALL_SPEED.getValue() > 1) {
                            GameDefaults.BALL_SPEED.set(GameDefaults.BALL_SPEED.getValue()
                                    - (initSpeed - 1) / GameDefaults.FRAME_RATE / 2000);
                            // so that it takes two seconds to slow down to "1" ball speed, regardless of fps
                        } else if (!shiftHeld && GameDefaults.BALL_SPEED.getValue() < initSpeed) {
                            GameDefaults.BALL_SPEED.set(GameDefaults.BALL_SPEED.getValue()
                                    + (initSpeed - 1) / GameDefaults.FRAME_RATE / 1000);
                        }
                    }
                };
                ballSpeedAnim.start();
                pane.requestFocus(); // Required to track key presses
                pane.setOnKeyPressed(handleKeyPress);
                pane.setOnKeyReleased(handleKeyRelease);
            }
            // End of debugging partition
        }
    }


    private void reset() {
        ball1.setTranslateX(GameDefaults.SCREEN_WIDTH / 2);
        ball1.setTranslateY(GameDefaults.SCREEN_HEIGHT / 2);
        currentTime = 5;
    }

}
