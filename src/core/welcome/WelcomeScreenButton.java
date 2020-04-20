package core.welcome;

import core.SFX;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class WelcomeScreenButton extends Label {

    WelcomeScreenButton(String s, EventHandler<MouseEvent> handler) {

        Font font = new Font(64);
        font = Font.loadFont(getClass().getResourceAsStream("/media/changa.ttf"), 32);

        setFont(font);
        setText(s);
        setOnMouseClicked(handler);
        setPadding(Insets.EMPTY);
        setTextFill(Color.LIGHTSLATEGRAY);
        GridPane.setHalignment(this, HPos.CENTER);

        addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> {
                    setCursor(Cursor.HAND);
                    setEffect(new DropShadow(8, Color.BLUE));
                    new SFX("wsBtnSound.wav");
                    // TODO animate glow
                });

        addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> {
                    setCursor(Cursor.DEFAULT);
                    setEffect(null);
                });

    }
}
