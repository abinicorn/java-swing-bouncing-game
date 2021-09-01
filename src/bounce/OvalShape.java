package bounce;

/**
 * Solution class.
 */
public class OvalShape extends Shape {
    public OvalShape(int x, int y, int deltaX, int deltaY) {
        super(x, y, deltaX, deltaY);
    }

    public OvalShape(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x, y, deltaX, deltaY, width, height);
    }

    public OvalShape(int x, int y, int deltaX, int deltaY, String text) {
        super(x, y, deltaX, deltaY, text);
    }

    public OvalShape(int x, int y, int deltaX, int deltaY, int width, int height, String text) {
        super(x, y, deltaX, deltaY, width, height, text);
    }

    @Override
    public void doPaint(Painter painter) {
        painter.drawOval(_x, _y, _width, _height);
    }
}
