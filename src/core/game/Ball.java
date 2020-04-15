package core.game;

import javafx.scene.shape.Circle;

public class Ball extends Circle {
    double dx = GameDefaults.BALL_SPEED;
    double dy = GameDefaults.BALL_SPEED;

    Ball()
    {
        setRadius(GameDefaults.BALL_RADIUS);
        setStyle("-fx-fill: image-pattern(\"./media/wsball.png\"); -fx-smooth: true;");
    }
}
