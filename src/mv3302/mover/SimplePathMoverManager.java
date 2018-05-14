package mv3302.mover;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import static simkit.Priority.HIGH;
import simkit.SimEntityBase;
import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimplePathMoverManager extends SimEntityBase {
    
    private Mover myMover;
    
    private boolean startOnRun;
    
    private List<Point2D> path;
    
    protected int next;
    
    public SimplePathMoverManager() { }
    
    public SimplePathMoverManager(Mover myMover, List<Point2D> path, boolean startOnRun) {
        setMyMover(myMover);
        setPath(path);
        setStartOnRun(startOnRun);
    }
    
    @Override
    public void reset() {
        super.reset();
        next = 0;
    }
    
    public void doRun() {
        firePropertyChange("next", getNext());
        
        if (isStartOnRun()) {
            waitDelay("Start", 0.0, HIGH);
        }
    }

    public void doStart() {
        if (next < path.size()) {
            waitDelay("MoveTo", 0.0, path.get(next));
        }
    }
    
    public void doEndMove(Mover mover) {
        int oldNext = getNext();
        next += 1;
        firePropertyChange("next", oldNext, getNext());
        if (next < path.size()) {
            waitDelay("MoveTo", 0.0, path.get(next));
        }
        
        if (next >= path.size()) {
            waitDelay("Stop", 0.0);
        } else {
        }
    }
    
    public void doStop() {
            waitDelay("OrderStop", 0.0);
    }
    
    /**
     * @return the myMover
     */
    public Mover getMyMover() {
        return myMover;
    }

    /**
     * @param myMover the myMover to set
     */
    public void setMyMover(Mover myMover) {
        if (this.myMover != null) {
            this.removeSimEventListener(this.myMover);
            this.myMover.removeSimEventListener(myMover);
        }
        this.myMover = myMover;
        this.myMover.addSimEventListener(this);
        this.addSimEventListener(myMover);
    }

    /**
     * @return the startOnRun
     */
    public boolean isStartOnRun() {
        return startOnRun;
    }

    /**
     * @param startOnRun the startOnRun to set
     */
    public void setStartOnRun(boolean startOnRun) {
        this.startOnRun = startOnRun;
    }

    /**
     * @return the path
     */
    public List<Point2D> getPath() {
        return new ArrayList<>(path);
    }

    /**
     * @param path the path to set
     */
    public void setPath(List<Point2D> path) {
        this.path = new ArrayList<>(path);
    }

    /**
     * @return the next
     */
    public int getNext() {
        return next;
    }
    
}
