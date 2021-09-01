package bounce.views;

import bounce.Shape;
import bounce.ShapeModel;
import bounce.ShapeModelEvent;
import bounce.ShapeModelListener;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.List;

public class Task2 extends Task1 implements ShapeModelListener {

    public Task2(ShapeModel model) {
        super(model);
    }

    @Override
    public void update(ShapeModelEvent event) {
        //childIndices always have a length of 1
        int[] childIndices = new int[1];

        //children always have a length of 1
        Object[] children = new Object[1];

        childIndices[0] = event.index();
        children[0] = event.operand();
        ShapeModel source = event.source();
        ShapeModelEvent.EventType type = event.eventType();
        Object[] path;

        if (event.parent() == null) {
            path = null;
        } else {
            path = event.parent().path().toArray();
        }

        // create the TreeModel event
        TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);

        if (type.equals(ShapeModelEvent.EventType.ShapeAdded)) {
            for (TreeModelListener listener : _treeModelListeners) {
                listener.treeNodesInserted(e);
            }
        } else if (type.equals(ShapeModelEvent.EventType.ShapeRemoved)) {
            for (TreeModelListener listener : _treeModelListeners) {
                listener.treeNodesRemoved(e);
            }
        }
    }
}
