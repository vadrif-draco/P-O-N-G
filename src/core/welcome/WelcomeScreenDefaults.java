package core.welcome;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Screen;

public final class WelcomeScreenDefaults {

    public static DoubleProperty screenWidth = new SimpleDoubleProperty(Screen.getPrimary().getBounds().getWidth());
    public static DoubleProperty screenHeight = new SimpleDoubleProperty(Screen.getPrimary().getBounds().getHeight());

    public static DoubleProperty barXEnd = new SimpleDoubleProperty(screenWidth() / 4 / 1.618 / 1.618 / 1.618);
    public static DoubleProperty barXStart = new SimpleDoubleProperty(barXEnd() / 1.618);
    public static DoubleProperty barWidth = new SimpleDoubleProperty(barXEnd() - barXStart());
    public static DoubleProperty barHeight = new SimpleDoubleProperty(screenHeight() / 6);
    public static DoubleProperty barYStart = new SimpleDoubleProperty(screenHeight() / 2 - barHeight() / 2);

    private WelcomeScreenDefaults() {
    }

    public static double screenWidth() {
        return screenWidth.getValue();
    }

    public static double screenHeight() {
        return screenHeight.getValue();
    }

    public static double barXEnd() {
        return barXEnd.getValue();
    }

    public static double barXStart() {
        return barXStart.getValue();
    }

    public static double barWidth() {
        return barWidth.getValue();
    }

    public static double barHeight() {
        return barHeight.getValue();
    }

    static double barYStart() {
        return barYStart.getValue();
    }
}
