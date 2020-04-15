package core.game;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class Player {
    private Bar bar ;
    private Scene scene;
    private int score;

    public Player(Bar bar, Scene scene) {
        this.bar = bar;
        this.scene = scene;
    }

    public Bar getBar() {
        return bar;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
