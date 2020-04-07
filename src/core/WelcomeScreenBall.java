package core;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.Random;

import static core.debugSystem.*;

class WelcomeScreenBall extends Circle implements WelcomeScreenDefaults {
    // RNGESUS BE GLOFIRIED
    private Random rng = new Random();
    private double velocity = 0.5; // velocity in pixels per millisecond
    private double velocityRateOfChange = 0.1;
    private double m; // Slope
    private double c; // Constant
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private WelcomeScreenBar l, r;

    // should leave a "polyline"-like trail after it
    // I think it'd be better as particles that are generated wherever the circle is (like poop) and fade away after
    // a constant time.
    WelcomeScreenBall() {
        setRadius(barHeight / 8);
        setCenterX(screenWidth / 2);
        setCenterY(screenHeight / 2);
        x1 = getCenterX();
        y1 = getCenterY();

        setStyle("-fx-fill: image-pattern(\"./media/wsball.png\"); -fx-smooth: true;");
    }

    private void reset() {
        setRadius(barHeight / 8);
        setCenterX(screenWidth / 2);
        setCenterY(screenHeight / 2);
    }

    private double r() {
        return this.getRadius();
    }

    private void moveBallFromTo(double x1, double y1, double x2, double y2, double v,
                                EventHandler<javafx.event.ActionEvent> actionWhenDone) {
        println("From (" + x1 + ", " + y1 + ")\n" +
                "To   (" + x2 + ", " + y2 + ")\n" +
                "@" + v + " pixels/ms");
        double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        PathTransition collisionCourse = new PathTransition(Duration.millis(d / v),
                new Line(x1, y1, x2, y2), this);
        collisionCourse.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        collisionCourse.setInterpolator(Interpolator.LINEAR);
        collisionCourse.setOnFinished(actionWhenDone);
        collisionCourse.playFromStart();
    }

    private double generateFirstSlope() {
        // Generate random point
        double x = Math.abs(100000 * rng.nextDouble()) % screenWidth;
        // Next line is to avoid near-vertical starting slopes
        while (Math.abs(x - screenWidth / 2) < screenWidth / 8)
            x = Math.abs(100000 * rng.nextDouble()) % screenWidth;
        double y = Math.abs(100000 * rng.nextDouble()) % screenHeight;
        println("Randomly generated initial point: (" + x + ", " + y + ")");
        return (y - this.getCenterY()) / (x - this.getCenterX());
    }

    void moveBall(WelcomeScreenBar l, WelcomeScreenBar r) {
        this.l = l;
        this.r = r;
        moveBall(-1); // -1 means initiate simulation
    }

    private void moveBall(int choice) {
        if (choice == -1) { // INITIATION
            reset();
            m = generateFirstSlope();
            c = this.getCenterY() - m * this.getCenterX();

            // Using "y = mx + c" ...
            // Or "Y - Yo = m(X - Xo)" ...
            // There are four cases for determining next point (and thus moving to it)
            // The following cases depend on fixing one value and varying the other,
            // where the coordinates take into account the ball's radius.
            boolean[] possibilities = new boolean[]{false, false, false, false};

            // 0. LEFT SIDE: x = bar + radius, checking if y is within boundaries
            if (m * (barXEnd + r()) + c >= r() &&
                    m * (barXEnd + r()) + c <= screenHeight) possibilities[0] = true;

            // 1. TOP SIDE: y = radius, checking if x is within boundaries
            if ((r() - c) / m >= r() + barXEnd &&
                    (r() - c) / m <= screenWidth - (r() + barXEnd)) possibilities[1] = true;

            // 2. RIGHT SIDE: x = max - (bar + radius), checking if y is within boundaries
            if (m * (screenWidth - (barXEnd + r())) + c >= r() &&
                    m * (screenWidth - (barXEnd + r())) + c <= screenHeight) possibilities[2] = true;

            // 3. BOTTOM SIDE: y = max - radius, checking if x is within boundaries
            if ((screenHeight - r() - c) / m >= r() + barXEnd &&
                    (screenHeight - r() - c) / m <= screenWidth - (r() + barXEnd)) possibilities[3] = true;

            // Randomly choosing one of the valid cases
            do {
                choice = Math.abs(rng.nextInt() % 4);
            } while (!possibilities[choice]);

            for (int i = 0; i < 4; i++) print(possibilities[i] + " ");
            println("\nCase: " + choice + "\nEquation: y = " + m + "x + " + c);

        } else if (choice == 0) { // If LEFT hit, then rebound possibilities are TOP or RIGHT or BOTTOM

            x1 = x2;
            y1 = y2;
            m = -1 * m;
            c = y1 - m * x1;

            // 1. TOP SIDE: y = radius, checking if x is within boundaries
            if ((r() - c) / m >= r() + barXEnd &&
                    (r() - c) / m <= screenWidth - (r() + barXEnd)) choice = 1;

            // 2. RIGHT SIDE: x = max - (bar + radius), checking if y is within boundaries
            if (m * (screenWidth - (barXEnd + r())) + c >= r() &&
                    m * (screenWidth - (barXEnd + r())) + c <= screenHeight) choice = 2;

            // 3. BOTTOM SIDE: y = max - radius, checking if x is within boundaries
            if ((screenHeight - r() - c) / m >= r() + barXEnd &&
                    (screenHeight - r() - c) / m <= screenWidth - (r() + barXEnd)) choice = 3;

        } else if (choice == 1) {

            x1 = x2;
            y1 = y2;
            m = -1 * m;
            c = y1 - m * x1;

            // 0. LEFT SIDE: x = bar + radius, checking if y is within boundaries
            if (m * (barXEnd + r()) + c >= r() &&
                    m * (barXEnd + r()) + c <= screenHeight) choice = 0;

            // 2. RIGHT SIDE: x = max - (bar + radius), checking if y is within boundaries
            if (m * (screenWidth - (barXEnd + r())) + c >= r() &&
                    m * (screenWidth - (barXEnd + r())) + c <= screenHeight) choice = 2;

            // 3. BOTTOM SIDE: y = max - radius, checking if x is within boundaries
            if ((screenHeight - r() - c) / m >= r() + barXEnd &&
                    (screenHeight - r() - c) / m <= screenWidth - (r() + barXEnd)) choice = 3;

        } else if (choice == 2) {

            x1 = x2;
            y1 = y2;
            m = -1 * m;
            c = y1 - m * x1;

            // 0. LEFT SIDE: x = bar + radius, checking if y is within boundaries
            if (m * (barXEnd + r()) + c >= r() &&
                    m * (barXEnd + r()) + c <= screenHeight) choice = 0;

            // 1. TOP SIDE: y = radius, checking if x is within boundaries
            if ((r() - c) / m >= r() + barXEnd &&
                    (r() - c) / m <= screenWidth - (r() + barXEnd)) choice = 1;

            // 3. BOTTOM SIDE: y = max - radius, checking if x is within boundaries
            if ((screenHeight - r() - c) / m >= r() + barXEnd &&
                    (screenHeight - r() - c) / m <= screenWidth - (r() + barXEnd)) choice = 3;

        } else if (choice == 3) {

            x1 = x2;
            y1 = y2;
            m = -1 * m;
            c = y1 - m * x1;

            // 0. LEFT SIDE: x = bar + radius, checking if y is within boundaries
            if (m * (barXEnd + r()) + c >= r() &&
                    m * (barXEnd + r()) + c <= screenHeight) choice = 0;

            // 1. TOP SIDE: y = radius, checking if x is within boundaries
            if ((r() - c) / m >= r() + barXEnd &&
                    (r() - c) / m <= screenWidth - (r() + barXEnd)) choice = 1;

            // 2. RIGHT SIDE: x = max - (bar + radius), checking if y is within boundaries
            if (m * (screenWidth - (barXEnd + r())) + c >= r() &&
                    m * (screenWidth - (barXEnd + r())) + c <= screenHeight) choice = 2;

        }

        switch (choice) {
            case 0: // LEFT SIDE: x = bar + radius
                x2 = r() + barXEnd;
                y2 = m * (barXEnd + r()) + c - r();
                break;
            case 1: // TOP SIDE: y = radius
                x2 = (r() - c) / m;
                y2 = r();
                break;
            case 2: // RIGHT SIDE: x = max - (bar + radius)
                x2 = screenWidth - (r() + barXEnd);
                y2 = m * (screenWidth - (barXEnd + r())) + c;
                break;
            case 3: // BOTTOM SIDE: y = max - radius
                x2 = (screenHeight - r() - c) / m;
                y2 = screenHeight - r();
        }
        moveBallFromTo(x1, y1, x2, y2, velocity, Collider);

        double durationForBar = 256 * Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) /
                velocity / Math.abs((y2 - y1));

        if (x2 > screenWidth / 2) r.moveBar1To(y2, durationForBar);
        else if (x2 < screenWidth / 2) l.moveBar2To(y2, durationForBar);
    }

    private EventHandler<ActionEvent> Collider = t -> {
        // K so at this point we want to know which quadrant got hit. We can determine that from the constants for
        // each case above. Based on that, we can transform the slope to take us to the next point...

        // Also, I want to make the bars move a lil bit to the inside upon collision, which will be some method
        // for the bar and we can know if it's collision w/bars or walls through the cases comparison again.
        // The method and how strong the hit is will be based on velocity, obviously, and the ball will go inside
        // the same amount that the bar goes, then they both bounce back that distance then ball continues.

        velocity += velocity * velocityRateOfChange;
        velocityRateOfChange -= velocityRateOfChange * 0.01;

        if (x2 == r() + barXEnd) {
            moveBall(0);
        } else if (y2 == r()) {
            moveBall(1);
        } else if (x2 == screenWidth - (r() + barXEnd)) {
            moveBall(2);
        } else if (y2 == screenHeight - r()) {
            moveBall(3);
        }
    };
}
