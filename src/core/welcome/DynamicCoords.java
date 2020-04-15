package core.welcome;

public class DynamicCoords {
    // DYNAMIC COORDINATES ARE TO BE USED ONLY WITH PATH TRANSITIONS!!!!!!!!!!!!!!!!!!!!!!
    // Also, they are shape centers.

    private double x = 0, y = 0, prevX, prevY;

    public DynamicCoords(double x, double y) {
        prevX = this.x;
        prevY = this.y;
        this.x = x;
        this.y = y;
    }

    public DynamicCoords() {
        this(0, 0);
    }

    public void set(double x, double y) {
        prevX = this.x;
        prevY = this.y;
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        prevX = this.x;
        this.x = x;
    }

    public void setY(double y) {
        prevY = this.y;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public DynamicCoords getPrev() {
        return new DynamicCoords(prevX, prevY);
    }

    public double getPrevX() {
        return prevX;
    }

    public double getPrevY() {
        return prevY;
    }

    public double getDeltaX() {
        return x - prevX;
    }

    public double getDeltaXSquared() {
        return (x - prevX) * (x - prevX);
    }

    public double getDeltaY() {
        return y - prevY;
    }

    public double getDeltaYSquared() {
        return (y - prevY) * (y - prevY);
    }

}
