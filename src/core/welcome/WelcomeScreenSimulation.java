package core.welcome;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Line;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static core.DebugSystem.print;
import static core.DebugSystem.println;
import static core.welcome.WelcomeScreenDefaults.*;
import static javafx.util.Duration.millis;

class WelcomeScreenSimulation {

    // Objects and paths that will be controlled.
    private Bar l, r;
    private Ball ball;
    private PathTransition ballCollisionPT = new PathTransition();
    private PathTransition lBarPT = new PathTransition();
    private PathTransition rBarPT = new PathTransition();

    // *Some* control parameters
    private double initialVelocity = 0.5; // velocity in pixels per millisecond
    private double maxVelocity = 5, velocityRateOfChange = 0.025, changingVelocity = initialVelocity;
    private boolean running = false;
    private double m, c; // [Ball Motion Parameters] Slope and constant

    // RNGESUS BE GLOFIRIED
    private Random rng = new Random();

    WelcomeScreenSimulation(Bar l, Bar r, Ball ball) {
        this.l = l;
        this.r = r;
        this.ball = ball;

        ballCollisionPT.setNode(ball);
        ballCollisionPT.setInterpolator(Interpolator.LINEAR);
        ballCollisionPT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        println("Ball Path initialized");

        lBarPT.setNode(l);
        lBarPT.setInterpolator(Interpolator.EASE_BOTH);
        println("Left Bar Path initialized");

        rBarPT.setNode(r);
        rBarPT.setInterpolator(Interpolator.EASE_BOTH);
        println("Right Bar Path initialized");

        running = true;
        println("Simulation running...");
    }

    void start() {
        moveBall(-1); // INITIALIZATION
    }

    boolean isRunning() {
        return running;
    }

    void resetAll() {
        changingVelocity = initialVelocity;

        ballCollisionPT.stop();
        ball.resetPosition();

        moveBar(l, screenHeight() / 2, 1, lBarPT);
        l.resetPosition();
        lBarPT.stop();

        moveBar(r, screenHeight() / 2, 1, rBarPT);
        r.resetPosition();
        rBarPT.stop();
    }

    // General motion method 
    private void moveFromTo(double x1, double y1, double x2, double y2, double v,
                            EventHandler<ActionEvent> actionWhenDone, @NotNull PathTransition path) {

        path.setPath(new Line(x1, y1, x2, y2));

        double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        path.setDuration(millis(d / v));
        path.setOnFinished(actionWhenDone);
        if (path.getStatus() != Animation.Status.RUNNING) path.playFromStart();

        println("From (" + x1 + ", " + y1 + ")\n" +
                "To   (" + x2 + ", " + y2 + ")\n" +
                "@" + v + " pixels/ms");
    }

    // BALL MOTION MECHANICS SECTION
    private double generateFirstSlope() {
        // Generate random point
        double x = Math.abs(100000 * rng.nextDouble()) % screenWidth();
        // Next line is to avoid "x" values that would lead to near-vertical starting slopes
        while (Math.abs(x - screenWidth() / 2) < screenWidth() / 8)
            x = Math.abs(100000 * rng.nextDouble()) % screenWidth();

        double y = Math.abs(100000 * rng.nextDouble()) % screenHeight();
        // TODO avoid near-horizontal starting slopes too.

        println("Randomly generated initial point: (" + x + ", " + y + ")");
        return (y - ball.getCenterY()) / (x - ball.getCenterX());
    }

    private void moveBall(int choice) {
        if (choice == -1) { // INITIATION
            m = generateFirstSlope();
            c = ball.getCenterY() - m * ball.getCenterX();

            // Using "y = mx + c" ...
            // Or "Y - Yo = m(X - Xo)" ...
            // There are four cases for determining next point (and thus moving to it)
            // The following cases depend on fixing one value and varying the other,
            // where the coordinates take into account the ball's radius.
            boolean[] possibilities = new boolean[4];

            // 0. LEFT SIDE: x = bar + radius, checking if y is within boundaries
            if (m * (barXEnd() + ball.r()) + c >= ball.r() &&
                    m * (barXEnd() + ball.r()) + c <= screenHeight()) possibilities[0] = true;

            // 1. TOP SIDE: y = radius, checking if x is within boundaries
            if ((ball.r() - c) / m >= ball.r() + barXEnd() &&
                    (ball.r() - c) / m <= screenWidth() - (ball.r() + barXEnd())) possibilities[1] = true;

            // 2. RIGHT SIDE: x = max - (bar + radius), checking if y is within boundaries
            if (m * (screenWidth() - (barXEnd() + ball.r())) + c >= ball.r() &&
                    m * (screenWidth() - (barXEnd() + ball.r())) + c <= screenHeight()) possibilities[2] = true;

            // 3. BOTTOM SIDE: y = max - radius, checking if x is within boundaries
            if ((screenHeight() - ball.r() - c) / m >= ball.r() + barXEnd() &&
                    (screenHeight() - ball.r() - c) / m <= screenWidth() - (ball.r() + barXEnd()))
                possibilities[3] = true;

            // Randomly choosing one of the valid cases
            do {
                choice = Math.abs(rng.nextInt() % 4);
            } while (!possibilities[choice]);

            for (int i = 0; i < 4; i++) print(possibilities[i] + " ");
            println("\nCase: " + choice + "\nEquation: y = " + m + "x + " + c);

        } else {

//            x1 = x2;
//            y1 = y2;
            m = -1 * m;
            c = ball.dCoords.getY() - m * ball.dCoords.getX();

            // 0. LEFT SIDE: x = bar + radius, checking if y is within boundaries
            // Assume LEFT hit, then rebound possibilities are TOP or RIGHT or BOTTOM, discard LEFT.
            // Same logic for all of them.
            if (choice != 0 && m * (barXEnd() + ball.r()) + c >= ball.r() &&
                    m * (barXEnd() + ball.r()) + c <= screenHeight()) choice = 0;

                // 1. TOP SIDE: y = radius, checking if x is within boundaries
            else if (choice != 1 && (ball.r() - c) / m >= ball.r() + barXEnd() &&
                    (ball.r() - c) / m <= screenWidth() - (ball.r() + barXEnd())) choice = 1;

                // 2. RIGHT SIDE: x = max - (bar + radius), checking if y is within boundaries
            else if (choice != 2 && m * (screenWidth() - (barXEnd() + ball.r())) + c >= ball.r() &&
                    m * (screenWidth() - (barXEnd() + ball.r())) + c <= screenHeight()) choice = 2;

                // 3. BOTTOM SIDE: y = max - radius, checking if x is within boundaries
            else if (choice != 3 && (screenHeight() - ball.r() - c) / m >= ball.r() + barXEnd() &&
                    (screenHeight() - ball.r() - c) / m <= screenWidth() - (ball.r() + barXEnd())) choice = 3;
        }

        switch (choice) {
            case 0: // LEFT SIDE: x = radius + bar
                ball.dCoords.setX(ball.r() + barXEnd());
//                ball.dCoords.setY(m * (ball.r() + barXEnd()) + c - ball.r());
                ball.dCoords.setY(m * (ball.r() + barXEnd()) + c);
                println("Case 0 done\n");
                break;
            case 1: // TOP SIDE: y = radius
                ball.dCoords.setX((ball.r() - c) / m);
                ball.dCoords.setY(ball.r());
                println("Case 1 done\n");
                break;
            case 2: // RIGHT SIDE: x = max - (radius + bar)
                ball.dCoords.setX(screenWidth() - (ball.r() + barXEnd()));
                ball.dCoords.setY(m * (screenWidth() - (barXEnd() + ball.r())) + c);
                println("Case 2 done\n");
                break;
            case 3: // BOTTOM SIDE: y = max - radius
                ball.dCoords.setX((screenHeight() - ball.r() - c) / m);
                ball.dCoords.setY(screenHeight() - ball.r());
                println("Case 3 done\n");
        }

        // TODO: find some better way to deal with this stupid sh
        /* double durationForBar = 256 *
                Math.sqrt(ball.dCoords.getDeltaXSquared() + ball.dCoords.getDeltaYSquared())
                / changingVelocity / Math.abs((ball.dCoords.getDeltaY())); */

        double durationForBar = Math.sqrt(ball.dCoords.getDeltaXSquared() + ball.dCoords.getDeltaYSquared())
                / changingVelocity / 2.424;

        if (ball.dCoords.getX() > screenWidth() / 2) {
            moveBar(r, ball.dCoords.getY(), durationForBar, rBarPT);
        } else if (ball.dCoords.getX() < screenWidth() / 2) {
            moveBar(l, ball.dCoords.getY(), durationForBar, lBarPT);
        } else {
            moveBar(r, ball.dCoords.getY(), durationForBar, rBarPT);
            moveBar(l, ball.dCoords.getY(), durationForBar, lBarPT);
        }

        moveFromTo(ball.dCoords.getPrevX(), ball.dCoords.getPrevY(),
                ball.dCoords.getX(), ball.dCoords.getY(),
                changingVelocity, collideWithBar, ballCollisionPT);
    }

    private EventHandler<ActionEvent> collideWithBar = t -> {
        // K so at this point we want to know which quadrant got hit. We can determine that from the constants for
        // each case above. Based on that, we can transform the slope to take us to the next point...

        // Also, I want to make the bars move a lil bit to the inside upon collision, which will be some method
        // for the bar and we can know if it's collision w/bars or walls through the cases comparison again.
        // The method and how strong the hit is will be based on velocity, obviously, and the ball will go inside
        // the same amount that the bar goes, then they both bounce back that distance then ball continues.

        changingVelocity += (changingVelocity < maxVelocity) ? changingVelocity * velocityRateOfChange : 0.0000001;
        velocityRateOfChange -= velocityRateOfChange * 0.01;

        if (ball.dCoords.getX() == ball.r() + barXEnd()) {
            moveBall(0);
        } else if (ball.dCoords.getY() == ball.r()) {
            moveBall(1);
        } else if (ball.dCoords.getX() == screenWidth() - (ball.r() + barXEnd())) {
            moveBall(2);
        } else if (ball.dCoords.getY() == screenHeight() - ball.r()) {
            moveBall(3);
        }
    };
    // END OF BALL MOTION MECHANICS SECTION

    // BAR MECHANICS SECTION
    private void moveBar(@NotNull Bar bar, double y2, double duration, @NotNull PathTransition path) {
        path.setDuration(millis(duration));
        path.setPath(new Line(bar.dCoords.getX(), bar.dCoords.getY(), bar.dCoords.getX(), y2));
        path.setOnFinished(t -> bar.dCoords.setY(y2));

        if (path.getStatus() == Animation.Status.RUNNING) path.stop();
        path.playFromStart();
    }

    // private void moveFromTo(double x1, double y1, double ballNewX, double ballNewY, double v,
    //                            EventHandler<ActionEvent> actionWhenDone, @NotNull PathTransition path)
}
