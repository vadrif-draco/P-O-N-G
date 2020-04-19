package core.game;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    DoubleProperty dx = new SimpleDoubleProperty(GameDefaults.BALL_SPEED.getValue());
    DoubleProperty dy = new SimpleDoubleProperty(GameDefaults.BALL_SPEED.getValue());


    Ball() {
        setRadius(GameDefaults.BALL_RADIUS);
        setStyle("-fx-fill: image-pattern(\"./media/wsball.png\"); -fx-smooth: true;");

        GameDefaults.BALL_SPEED.addListener(l -> {
            dx.setValue(dx.getValue() > 0 ?
                    GameDefaults.BALL_SPEED.getValue() :
                    -1 * GameDefaults.BALL_SPEED.getValue());
        });

        GameDefaults.BALL_SPEED.addListener(l -> {
            dy.setValue(dy.getValue() > 0 ?
                    GameDefaults.BALL_SPEED.getValue() :
                    -1 * GameDefaults.BALL_SPEED.getValue());
        });
    }
}
