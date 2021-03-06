package mv3302.mover;

import java.awt.geom.Point2D;
import static java.lang.Double.NaN;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;
import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimpleRandomMoverManager extends SimEntityBase {

    private boolean startOnRun;

    private RandomVariate[] coordinateGenerator;

    private Mover mover;

    protected Point2D nextDestination;

    public SimpleRandomMoverManager() {
        this.nextDestination = new Point2D.Double(NaN, NaN);
    }

    public SimpleRandomMoverManager(Mover mover, RandomVariate[] coordinateGenerator,
            boolean startOnRun) {
        this();
        setMover(mover);
        setCoordinateGenerator(coordinateGenerator);
        setStartOnRun(startOnRun);
    }

    @Override
    public void reset() {
        super.reset();
        this.nextDestination.setLocation(NaN, NaN);
    }

    public void doRun() {
        firePropertyChange("nextDestination", getNextDestination());
        if (isStartOnRun()) {
            waitDelay("Start", 0.0);
        }
    }

    public void doStart() {
        Point2D oldNextDestination = getNextDestination();
        nextDestination.setLocation(
                coordinateGenerator[0].generate(),
                coordinateGenerator[1].generate());
        firePropertyChange("nextDestination", oldNextDestination, getNextDestination());
        
        waitDelay("MoveTo", 0.0, nextDestination);
    }
    
    public void doEndMove(Mover mover) {
        Point2D oldNextDestination = getNextDestination();
        nextDestination.setLocation(
                coordinateGenerator[0].generate(),
                coordinateGenerator[1].generate());
        firePropertyChange("nextDestination", oldNextDestination, getNextDestination());
        
        waitDelay("MoveTo", 0.0, nextDestination);
    }
    
    public void doStop() {
            waitDelay("OrderStop", 0.0);
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
     * @return the coordinateGenerator
     */
    public RandomVariate[] getCoordinateGenerator() {
        return coordinateGenerator.clone();
    }

    /**
     * @param coordinateGenerator the coordinateGenerator to set
     */
    public void setCoordinateGenerator(RandomVariate[] coordinateGenerator) {
        this.coordinateGenerator = coordinateGenerator.clone();
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
            this.removeSimEventListener(mover);
            this.mover.removeSimEventListener(this);
        }
        this.mover = mover;
        this.addSimEventListener(this.mover);
        this.mover.addSimEventListener(this);
    }

    /**
     * @return the nextDestination
     */
    public Point2D getNextDestination() {
        Point2D copy = new Point2D.Double();
        copy.setLocation(nextDestination);
        return copy;
    }

}
