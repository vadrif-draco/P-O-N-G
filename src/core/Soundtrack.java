package core;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Soundtrack {
    private MediaPlayer a;

    public Soundtrack(String s) {
        this(s, 1, 1);
    }

    public Soundtrack(String s, int numOfLoops) {
        this(s, numOfLoops, 1);
    }

    public Soundtrack(String s, double playRate) {
        this(s, 1, playRate);
    }

    public Soundtrack(String s, int numOfLoops, double playRate) {
        a = new MediaPlayer(new Media(this.getClass().getResource("/media/" + s).toString()));
        a.setCycleCount(numOfLoops);
        a.setRate(playRate);
        a.play();
    }

    public Soundtrack(Stage stage) {
        a = new MediaPlayer(new Media(this.getClass().getResource("/media/rr.mp3").toString()));
        a.setAutoPlay(true);
        MediaView mv = new MediaView(a);
        Pane pane = new Pane(mv);
        stage.setScene(new Scene(pane));
    }

    private AnimationTimer fadeOut = new AnimationTimer() {

        @Override
        public void handle(long l) {
            if (a.getVolume() > 0) {
                a.setVolume(a.getVolume() - 0.01);
            } else {
                a.stop();
                fadeOut.stop();
            }
        }
    };


    public void stop() {
        if (a.getStatus() == MediaPlayer.Status.PLAYING) fadeOut.start();
    }
}
