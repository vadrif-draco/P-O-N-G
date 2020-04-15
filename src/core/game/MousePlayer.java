package core.game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class MousePlayer extends Player {

    public MousePlayer(Bar bar, Scene scene) {
        super(bar, scene);


        //Tracking Mouse Code
        EventHandler<MouseEvent> mouseEvent = e -> {
            getBar().moveTo(e.getY());
        };
        scene.setOnMouseMoved(mouseEvent);
    }

}
