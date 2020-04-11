package core;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class DebugSystem implements WelcomeScreenDefaults {
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
        System.out.println();
        update();
    }
    static void print(double d) {
        pseudoConsole.setText(pseudoConsole.getText() + d);
        System.out.print(d);
        update();
    }
    static void println(double d) {
        pseudoConsole.setText(pseudoConsole.getText() + d + "\n");
        System.out.println(d);
        update();
    }
    static void print(String s) {
        pseudoConsole.setText(pseudoConsole.getText() + s);
        System.out.print(s);
        update();
    }
    static void println(String s) {
        pseudoConsole.setText(pseudoConsole.getText() + s + "\n");
        System.out.println(s);
        update();
    }
    private static void update() {
        pseudoConsole.setLayoutX((screenWidth - pseudoConsole.getWidth())/2);
    }
}
