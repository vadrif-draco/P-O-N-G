package core.game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class KeyboardPlayer extends Player {

    private boolean isUp=false;
    private boolean isDown=false;

    public KeyboardPlayer(Bar bar, Scene scene) {
        super(bar, scene);


        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double dy= 0.0;
                if(isUp)
                    dy -= GameDefaults.KEYBOARD_PITCH;
                if(isDown)
                    dy += GameDefaults.KEYBOARD_PITCH;

                getBar().moveTo(getBar().getPosition() + dy);
            }
        };

        animationTimer.start();
        EventHandler<KeyEvent> pressedHandler = e -> {
            switch (e.getCode()) {
                case UP:
                    isUp = true;
                    break;
                case DOWN:
                    isDown = true;
                    break;
            }
        };


        scene.setOnKeyPressed(pressedHandler);
        EventHandler<KeyEvent> releasedHandler = e -> {
            switch (e.getCode()) {
                case UP:
                    isUp = false;
                    break;
                case DOWN:
                    isDown = false;
                    break;
            }
        };
        scene.setOnKeyReleased(releasedHandler);

    }


}
