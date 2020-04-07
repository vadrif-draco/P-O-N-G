package core;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class debugSystem implements WelcomeScreenDefaults {
    static Label pseudoConsole = new Label();

    static void startDebugging() {
        refreshConsole();
        pseudoConsole.setFont(Font.font("Fira Code", 16));
        pseudoConsole.setTextFill(Color.web("#ffffff"));
        pseudoConsole.setText("Debugging enabled\n\n");
        update();
    }
    static void refreshConsole() {
        pseudoConsole.setText("");
    }
    static void println() {
        pseudoConsole.setText(pseudoConsole.getText());
        update();
    }
    static void print(double d) {
        pseudoConsole.setText(pseudoConsole.getText() + d);
        update();
    }
    static void println(double d) {
        pseudoConsole.setText(pseudoConsole.getText() + d + "\n");
        update();
    }
    static void print(String s) {
        pseudoConsole.setText(pseudoConsole.getText() + s);
        update();
    }
    static void println(String s) {
        pseudoConsole.setText(pseudoConsole.getText() + s + "\n");
        update();
    }
    private static void update() {
        pseudoConsole.setLayoutX((screenWidth - pseudoConsole.getWidth())/2);
    }
}
