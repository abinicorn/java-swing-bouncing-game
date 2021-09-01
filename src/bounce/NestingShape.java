package bounce;

/**
 * Solution class.
 */

import java.util.ArrayList;
import java.util.List;

public class NestingShape extends Shape {

    private List<Shape> _shapes;

    public NestingShape(int x, int y, int deltaX, int deltaY, int width,
                        int height) {
        super(x, y, deltaX, deltaY, width, height, null);
        _shapes = new ArrayList<Shape>();
    }

    public NestingShape(int x, int y, int deltaX, int deltaY, int width,
                        int height, String text) {
        super(x, y, deltaX, deltaY, width, height, text);
        _shapes = new ArrayList<Shape>();
    }

    protected void doPaint(Painter painter) {
        painter.drawRect(_x, _y, _width, _height);

        // Cause painting of shapes to be relative to this shape.
        painter.translate(_x, _y);

        for (Shape shape : _shapes) {
            shape.paint(painter);
        }

        // Restore graphics origin.
        painter.translate(-_x, -_y);
    }

    @Override
    public void move(int width, int height) {
        // Move this nesting shape.
        super.move(width, height);

        // Move contained shapes.
        for (Shape shape : _shapes) {
            shape.move(_width, _height);
        }
    }

    void add(Shape shape) throws IllegalArgumentException {
        if (shape.parent() != null || outOfBounds(shape)) {
            throw new IllegalArgumentException();
        }


        _shapes.add(shape);
        shape.setParent(this);
    }

    void remove(Shape shape) {
        _shapes.remove(shape);
        shape.setParent(null);
    }

    // Throws IndexOutOfBounds - unchecked exception.
    public Shape shapeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= _shapes.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return _shapes.get(index);
        }
    }

    public int shapeCount() {
        return _shapes.size();
    }

    // Returns -1 or the index.
    public int indexOf(Shape shape) {

        if (!this._shapes.contains(shape)) {
            return -1;
        }

        return this._shapes.indexOf(shape);
    }

    public boolean contains(Shape shape) {
        return _shapes.contains(shape);
    }

    private boolean outOfBounds(Shape s) {
        boolean result = false;

        if ((s.x() + s.width() > this._width)
                || (s.y() + s.height() > this._height)) {
            result = true;
        }
        return result;
    }
}

