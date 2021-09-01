package bounce.views;

import bounce.NestingShape;
import bounce.Shape;
import bounce.ShapeModel;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class Task1 implements TreeModel {

    protected ShapeModel _adaptee;
    protected List<TreeModelListener> _treeModelListeners;

    public Task1(ShapeModel model) {
        _adaptee = model;
        _treeModelListeners = new ArrayList<>();

    }

    @Override
    public Object getRoot() {
        return _adaptee.root();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Object result = null;
        try {
            if (parent instanceof NestingShape) {
                NestingShape nst = (NestingShape) parent;
                result = nst.shapeAt(index);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
        return result;
    }

    @Override
    public int getChildCount(Object parent) {
        int result = 0;
        Shape shape = (Shape) parent;

        if (shape instanceof NestingShape) {
            NestingShape nst = (NestingShape) shape;
            result = nst.shapeCount();
        }
        return result;
    }

    @Override
    public boolean isLeaf(Object node) {

        return !(node instanceof NestingShape);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int indexOfChild = -1;
        if (parent instanceof NestingShape) {
            NestingShape nst = (NestingShape) parent;
            Shape shape = (Shape) child;
            indexOfChild = nst.indexOf(shape);

        }

        return indexOfChild;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

        _treeModelListeners.add(l);

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        _treeModelListeners.remove(l);

    }
}
