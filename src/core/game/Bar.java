package core.game;

import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle {
    Bar() {
        setWidth(GameDefaults.BAR_WIDTH);
        setHeight(GameDefaults.BAR_HEIGHT);
        setStyle("-fx-fill: #efefef; -fx-smooth: true;");

    }

    /**
     * Moves the Bar to a specific Y-axis position taking screen bounds in consideration
     *
     * @param barMiddle
     */
    public void moveTo(double barMiddle) {

        if (barMiddle <= getHeight() / 2)
            setTranslateY(0);
        else if (barMiddle >= GameDefaults.SCREEN_HEIGHT - getHeight() / 2)
            setTranslateY(GameDefaults.SCREEN_HEIGHT - getHeight());
        else
            setTranslateY(barMiddle - getHeight() / 2);
    }


    /**
     * @return The middle Y-axis Bar position
     */
    public double getPosition() {
        return getTranslateY() + getHeight() / 2;
    }
}
