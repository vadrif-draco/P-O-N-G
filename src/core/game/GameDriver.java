package core.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class GameDriver {

    private Ball ball1 ;
    private Player p1;
    private Player p2;
    private Pane pane;
    private boolean started = false;
    private Label clickLabel;

    public GameDriver(Ball ball1, Player p1, Player p2, Pane pane) {
        this.ball1 = ball1;
        this.p1 = p1;
        this.p2 = p2;
        this.pane = pane;
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        KeyFrame kf = new KeyFrame(
                Duration.seconds(1/GameDefaults.FRAME_RATE),
                new FrameHandler() );

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();


        clickLabel= new Label("Mouse Click");
        clickLabel.setLayoutX(GameDefaults.SCREEN_WIDTH/2-120);
        clickLabel.setLayoutY(GameDefaults.SCREEN_HEIGHT/4);
        clickLabel.setFont(Font.font("Fira Code", 48));
        clickLabel.setTextFill(Color.LIGHTSKYBLUE);
        pane.getChildren().add(clickLabel);

        //Start Pause on Mouse Click
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent keyEvent) {
                    started = !started;
            }
        });
    }


    //Main Game LOOOOOOP
    // TODO Levels ands other stuff
    private class FrameHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

            //Move the ball according to its velocity
            if(started)
            {

                //TODO Random initial ball direction

                ball1.setTranslateX(ball1.getTranslateX()+ball1.dx);
                ball1.setTranslateY(ball1.getTranslateY()+ball1.dy);

                clickLabel.setVisible(false);
            }
            else
            {
                clickLabel.setVisible(true);

            }


            //Screen Bounds Collision
            if(ball1.getTranslateY()<=0 || ball1.getTranslateY()>=GameDefaults.SCREEN_HEIGHT)
                ball1.dy *= -1;


            //Player 1 Missed
            if(ball1.getTranslateX()>GameDefaults.SCREEN_WIDTH +ball1.getRadius())
            {
                started = false;
                p2.setScore(p2.getScore()+1);
                reset();
            }

            //Player 2 Missed
            if(ball1.getTranslateX() <-ball1.getRadius() )
            {
                started = false;
                p1.setScore(p1.getScore()+1);
                reset();
            }



            // Bar Collision COOL RIGHT !!!
            if(p1.getBar().getBoundsInParent().intersects(ball1.getBoundsInParent())) {
                ball1.setTranslateX(p1.getBar().getTranslateX()-ball1.getRadius());
                ball1.dx *= -1;
            }
            if(p2.getBar().getBoundsInParent().intersects(ball1.getBoundsInParent())) {
                ball1.setTranslateX(p2.getBar().getTranslateX()+p2.getBar().getWidth()+ball1.getRadius());
                ball1.dx *= -1;
            }

            //TODO Ball bounce direction depends on where it hits the bar


        }
    }



    private void reset()
    {
        ball1.setTranslateX(GameDefaults.SCREEN_WIDTH/2);
        ball1.setTranslateY(GameDefaults.SCREEN_HEIGHT/2);
    }

}
