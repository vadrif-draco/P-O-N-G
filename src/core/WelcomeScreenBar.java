package core;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static core.debugSystem.println;

class WelcomeScreenBar extends Rectangle implements WelcomeScreenDefaults {

    double y1 = barYStart + barHeight/2;

    WelcomeScreenBar(String type) {
        super(barXStart, barYStart, barWidth, barHeight);
        if (type.equals("right")) this.setX(screenWidth - barXEnd);
        setStyle("-fx-fill: #efefef; -fx-smooth: true;");
        setArcHeight(barWidth / 1.618);
        setArcWidth(barWidth / 1.618);
    }

    double getCenterX() {
        return getX() + getWidth() / 2;
    }

    double getCenterY() {
        return getY() + barHeight / 2;
    }

    void moveBar1To(double y2, double duration) {
        PathTransition barPath = new PathTransition(Duration.millis(duration),
                new Line(getCenterX(), y1, getCenterX(), y2), this);
        if (barPath.getStatus() == Animation.Status.RUNNING) barPath.stop();
        barPath.setInterpolator(Interpolator.EASE_BOTH);

        barPath.setOnFinished(t -> y1 = y2);
        barPath.playFromStart();
    }

    void moveBar2To(double y2, double duration) {
        println("\n\n" + duration);
        PathTransition barPath = new PathTransition(Duration.millis(duration),
                new Line(getCenterX(), y1, getCenterX(), y2), this);
        if (barPath.getStatus() == Animation.Status.RUNNING) barPath.stop();
        barPath.setInterpolator(Interpolator.EASE_BOTH);

        barPath.setOnFinished(t -> y1 = y2);
        barPath.playFromStart();
    }

    // Why two repeated codes for moving bars?
    // Well, using just one to move two bars simultaneously doesn't work (the animations interfere) so the only way my
    // genius mind managed to find is to make a method for each bar.
}
