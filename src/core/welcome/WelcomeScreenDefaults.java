package core.welcome;

import javafx.stage.Screen;

public interface WelcomeScreenDefaults {
    double screenWidth = Screen.getPrimary().getBounds().getWidth();
    double screenHeight = Screen.getPrimary().getBounds().getHeight();

    double barXEnd = screenWidth / 4 / 1.618 / 1.618 / 1.618;
    double barXStart = barXEnd / 1.618;
    double barWidth = barXEnd - barXStart;
    double barHeight = screenHeight / 6;
    double barYStart = screenHeight / 2 - barHeight / 2;
}
