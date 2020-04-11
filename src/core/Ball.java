package core;

import javafx.scene.shape.Circle;

class Ball extends Circle implements WelcomeScreenDefaults, MovableSprite {
    DynamicCoords dCoords = new DynamicCoords();

    Ball() {
        setRadius(barHeight / 8);
        setCenterX(screenWidth / 2);
        setCenterY(screenHeight / 2);
        this.dCoords.set(getCenterX(), getCenterY());

        setStyle("-fx-fill: image-pattern(\"./media/wsball.png\"); -fx-smooth: true;");
    }

    double r() {
        return this.getRadius();
    } // just to make it shorter lol

    @Override
    public void resetPosition() {
        setRadius(barHeight / 8);
        setCenterX(screenWidth / 2);
        setCenterY(screenHeight / 2);
        this.dCoords.set(getCenterX(), getCenterY());
    }
}
