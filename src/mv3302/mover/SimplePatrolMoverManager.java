package mv3302.mover;

import java.awt.geom.Point2D;
import java.util.List;
import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimplePatrolMoverManager extends SimplePathMoverManager {
    
    public SimplePatrolMoverManager() { 
        super();
    }
    
    public SimplePatrolMoverManager(Mover mover, List<Point2D> path, boolean startOnRun) {
        this();
        setMover(mover);
        setPath(path);
        setStartOnRun(startOnRun);
    }
    
    @Override
    public void doEndMove(Mover mover) {
        int oldNext = getNext();
        next = (next + 1) % getPath().size();
        firePropertyChange("next", oldNext, getNext());
        
        waitDelay("MoveTo", 0.0, getPath().get(next));
    }
    
    
}
