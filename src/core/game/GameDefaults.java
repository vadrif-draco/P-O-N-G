package core.game;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Screen;

public final class GameDefaults {
    private GameDefaults(){}

    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double BALL_RADIUS = 20;
    public static final double BAR_WIDTH = 15;
    public static final double BAR_HEIGHT = 150;
    public static final double KEYBOARD_PITCH = 30;
    public static final DoubleProperty BALL_SPEED = new SimpleDoubleProperty(5);
    public static final double FRAME_RATE = 60;

    public static double initTime = 25;
}
