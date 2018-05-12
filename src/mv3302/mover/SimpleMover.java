package mv3302.mover;

import java.awt.geom.Point2D;
import static java.lang.Double.NaN;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimpleMover extends SimEntityBase implements Mover {

    private Point2D initialLocation;
    private double maxSpeed;

    private Point2D lastStopLocation;

    private Point2D velocity;

    private Point2D destination;

    private double startMoveTime;

    public SimpleMover() {
        this.lastStopLocation = new Point2D.Double();
        this.velocity = new Point2D.Double();
        this.destination = new Point2D.Double();
    }

    public SimpleMover(Point2D initialLocation, double maxSpeed) {
        this();
        this.setInitialLocation(initialLocation);
        this.setMaxSpeed(maxSpeed);
    }

    @Override
    public void reset() {
        super.reset();
        this.lastStopLocation.setLocation(initialLocation);
        this.velocity.setLocation(0.0, 0.0);
        this.destination.setLocation(NaN, NaN);
        this.startMoveTime = Schedule.getSimTime();
    }

    public void doRun() {
        firePropertyChange("lastStopLocation", getLastStopLocation());
        firePropertyChange("velocity", getVelocity());
        firePropertyChange("destination", getDestination());
        firePropertyChange("startMoveTime", getStartMoveTime());
    }

    public void doMoveTo(Point2D destination) {
        Point2D oldDestination = getDestination();
        this.destination.setLocation(destination);
        firePropertyChange("destination", oldDestination, getDestination());

        waitDelay("StartMove", 0.0, this);
    }

    @Override
    public Point2D getCurrentLocation() {
        return new Point2D.Double(
                lastStopLocation.getX() + velocity.getX() * (Schedule.getSimTime() - startMoveTime),
                lastStopLocation.getY() + velocity.getY() * (Schedule.getSimTime() - startMoveTime)
        );
    }

    @Override
    public Point2D getVelocity() {
        return new Point2D.Double(this.velocity.getX(), this.velocity.getY());
    }

    @Override
    public void doStartMove(Mover mover) {
        double oldStartMoveTime = getStartMoveTime();
        startMoveTime = Schedule.getSimTime();
        firePropertyChange("startMoveTime", oldStartMoveTime, getStartMoveTime());

        Point2D oldVelocity = getVelocity();

        double distance = lastStopLocation.distance(destination);
        this.velocity.setLocation(
                (destination.getX() - lastStopLocation.getX()) / distance * maxSpeed,
                (destination.getY() - lastStopLocation.getY()) / distance * maxSpeed);
        firePropertyChange("velocity", oldVelocity, getVelocity());

        waitDelay("EndMove", distance / maxSpeed, this);
    }

    public void doEndMove(Mover mover) {
        Point2D oldLastStopLocation = getLastStopLocation();
        lastStopLocation.setLocation(destination);
        firePropertyChange("lastStopLocation", oldLastStopLocation, getLastStopLocation());
        
        Point2D oldVelocity = getVelocity();
        velocity.setLocation(0.0, 0.0);
        firePropertyChange("velocity", oldVelocity, getVelocity());
        
        Point2D oldDestination = getDestination();
        destination.setLocation(NaN, NaN);
        firePropertyChange("destination", oldDestination, getDestination());
        
        double oldStartMoveTime = getStartMoveTime();
        startMoveTime = Schedule.getSimTime();
        firePropertyChange("startMoveTime", oldStartMoveTime, getStartMoveTime());
    }

    @Override
    public void doStop(Mover mover) {
        Point2D oldLastStopLocation = getLastStopLocation();
        this.lastStopLocation.setLocation(getCurrentLocation());
        firePropertyChange("lastStopLocation", oldLastStopLocation, getLastStopLocation());
        
        Point2D oldVelocity = getVelocity();
        velocity.setLocation(0.0, 0.0);
        firePropertyChange("velocity", oldVelocity, getVelocity());
        
        double oldStartMoveTime = getStartMoveTime();
        startMoveTime = Schedule.getSimTime();
        firePropertyChange("startMoveTime", oldStartMoveTime, getStartMoveTime());
        
        interrupt("EndMove", this);
        
    }

    @Override
    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * @return the initialLocation
     */
    public Point2D getInitialLocation() {
        return new Point2D.Double(initialLocation.getX(), initialLocation.getY());
    }

    /**
     * @param initialLocation the initialLocation to set
     */
    public void setInitialLocation(Point2D initialLocation) {
        this.initialLocation = new Point2D.Double(initialLocation.getX(), initialLocation.getY());
    }

    /**
     * @param maxSpeed the maxSpeed to set
     */
    public void setMaxSpeed(double maxSpeed) {
        if (maxSpeed < 0.0) {
            throw new IllegalArgumentException("Max speed must be \u2265 0.0: " + maxSpeed);
        }
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return the lastStopLocation
     */
    public Point2D getLastStopLocation() {
        return new Point2D.Double(lastStopLocation.getX(), lastStopLocation.getY());
    }

    /**
     * @return the destination
     */
    public Point2D getDestination() {
        return new Point2D.Double(destination.getX(), destination.getY());
    }

    /**
     * @return the startMoveTime
     */
    public double getStartMoveTime() {
        return startMoveTime;
    }
    
    @Override
    public String toString() {
        Point2D currentLocation = getCurrentLocation();
        return String.format("%s [%.3f, %.3f] [%.3f, %.3f]",
                getName(), currentLocation.getX(), currentLocation.getY(),
                velocity.getX(), velocity.getY());
    }

}
