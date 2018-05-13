package mv3302.sensor;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import simkit.SimEntityBase;
import simkit.smd.Mover;
import simkit.smd.Sensor;

/**
 *
 * @author ahbuss
 */
public abstract class AbstractSimpleSensor extends SimEntityBase implements Sensor {

    private Mover mover;
    
    private double maxRange;
    
    protected Set<Mover> contacts;
    
    public AbstractSimpleSensor() {
        this.contacts = new HashSet<>();
    }
    
    public AbstractSimpleSensor(Mover mover, double maxRange) {
        this();
        setMover(mover);
        setMaxRange(maxRange);
    }
    
    public void doDetection(Mover target) {
        Set<Mover> oldContacts = getContacts();
        contacts.add(target);
        firePropertyChange("contacts", oldContacts, getContacts());
    }
    
    public void doUndetection(Mover target) {
        Set<Mover> oldContacts = getContacts();
        contacts.remove(target);
        firePropertyChange("contacts", oldContacts, getContacts());
    }
    
    @Override
    public Point2D getCurrentLocation() {
        return mover.getCurrentLocation();
    }

    @Override
    public Point2D getVelocity() {
        return mover.getVelocity();
    }

    @Override
    public double getMaxRange() {
        return maxRange;
    }

    public void doStartMove(Mover mover) {
        waitDelay("StartMove", 0.0, this);
    }
    
    @Override
    public void doStartMove(Sensor sensor) {
    }

    public void doStop(Mover mover) {
        waitDelay("Stop", 0.0, this);
    }
    
    @Override
    public void doStop(Sensor sensor) {
    }

    @Override
    public Mover getMover() {
        return mover;
    }

    @Override
    public Set<Mover> getContacts() {
        return new HashSet<>(contacts);
    }

    /**
     * @param mover the mover to set
     */
    public void setMover(Mover mover) {
        if (this.mover != null) {
            this.mover.removeSimEventListener(this);
        }
        this.mover = mover;
        this.mover.addSimEventListener(this);
    }

    /**
     * @param maxRange the maxRange to set
     * @throws IllegalArgumentException if maxRange \u2264 0.0
     */
    public void setMaxRange(double maxRange) {
        if (maxRange <= 0.0) {
            throw new IllegalArgumentException(
                "maxRange must be > 0.0: " + maxRange);
        }
        this.maxRange = maxRange;
    }
    
    @Override
    public String toString() {
        return String.format("%s %.3f", getName(), getMaxRange());
    }
    
}
