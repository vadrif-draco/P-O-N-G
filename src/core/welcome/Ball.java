package core.welcome;

import javafx.scene.shape.Circle;

import static core.welcome.WelcomeScreenDefaults.*;

class Ball extends Circle implements MovableSprite {
    DynamicCoords dCoords = new DynamicCoords();

    Ball() {
        setRadius(barHeight() / 8);
        setCenterX(screenWidth() / 2);
        setCenterY(screenHeight() / 2);
        this.dCoords.set(getCenterX(), getCenterY());

        setStyle("-fx-fill: image-pattern(\"./media/wsball.png\"); -fx-smooth: true;");
    }

    double r() {
        return this.getRadius();
    } // just to make it shorter lol

    @Override
    public void resetPosition() {
        setRadius(barHeight() / 8);
        setCenterX(screenWidth() / 2);
        setCenterY(screenHeight() / 2);
        this.dCoords.set(getCenterX(), getCenterY());
    }
}
