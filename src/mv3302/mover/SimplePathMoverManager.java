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
    
    private Mover mover;
    
    private boolean startOnRun;
    
    private List<Point2D> path;
    
    protected int next;
    
    public SimplePathMoverManager() { }
    
    public SimplePathMoverManager(Mover myMover, List<Point2D> path, boolean startOnRun) {
        setMover(myMover);
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
     * @return the mover
     */
    public Mover getMover() {
        return mover;
    }

    /**
     * @param mover the mover to set
     */
    public void setMover(Mover mover) {
        if (this.mover != null) {
            this.removeSimEventListener(this.mover);
            this.mover.removeSimEventListener(mover);
        }
        this.mover = mover;
        this.mover.addSimEventListener(this);
        this.addSimEventListener(mover);
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
