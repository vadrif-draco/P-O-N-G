package core.welcome;

import javafx.scene.shape.Rectangle;

import static core.welcome.WelcomeScreenDefaults.*;

class Bar extends Rectangle implements MovableSprite {
    DynamicCoords dCoords = new DynamicCoords();

    private String type;

    Bar(String type) {
        super(barXStart(), barYStart(), barWidth(), barHeight());
        this.type = type;
        if (type.equals("right")) this.setX(screenWidth() - barXEnd());

        this.dCoords.set(getX() + barWidth() / 2, getY() + barHeight() / 2);

        setStyle("-fx-fill: #efefef; -fx-smooth: true;");
        setArcHeight(barWidth() / 1.618);
        setArcWidth(barWidth() / 1.618);
    }

    @Override
    public void resetPosition() {
        setX(type.equals("left") ? barXStart() : screenWidth() - barXEnd()); // TERNARY CONDITIONS FOR THE WIN YOOOO
        System.out.println(type);
        setY(barYStart());
        this.dCoords.set(getX() + barWidth() / 2, getY() + barHeight() / 2);
    }
}
