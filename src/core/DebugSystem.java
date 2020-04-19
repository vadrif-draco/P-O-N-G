package core;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static core.welcome.WelcomeScreenDefaults.screenHeight;
import static core.welcome.WelcomeScreenDefaults.screenWidth;

public abstract class DebugSystem {
    public static Label pseudoConsole = new Label();

    public static void startDebugging() {
        refreshConsole();
        pseudoConsole.layoutXProperty().bind((screenWidth.subtract(pseudoConsole.getWidth())).divide(2));
        // TODO make it properly centered ;-;
        pseudoConsole.setFont(Font.font("Monospace", 16));
        pseudoConsole.setTextFill(Color.web("#ffffff"));
        pseudoConsole.setText("Debugging enabled\n\n");
        update();
    }

    public static void refreshConsole() {
        pseudoConsole.setText("");
    }

    public static void print(double d) {
        pseudoConsole.setText(pseudoConsole.getText() + d);
        System.out.print(d);
        update();
    }

    public static void print(String s) {
        pseudoConsole.setText(pseudoConsole.getText() + s);
        System.out.print(s);
        update();
    }

    public static void println() {
        print("\n");
    }

    public static void println(double d) {
        print(d + "\n");
    }

    public static void println(String s) {
        print(s + "\n");
    }

    private static void checkHeight() {
        if (pseudoConsole.getHeight() > screenHeight()) refreshConsole();
    }

    private static void update() {
        checkHeight();
    }
}
