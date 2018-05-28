package mv3302.sensor;

import java.util.LinkedHashSet;
import java.util.Set;
import mv3302.mover.SimpleRandomMoverManager;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.smd.Mover;
import simkit.smd.Sensor;

/**
 *
 * @author ahbuss
 */
public class Seeker extends SimEntityBase {

    private Sensor sensor;

    private int numberToFind;

    private SimpleRandomMoverManager moverManager;

    private double criterion;

    protected Set<Mover> foundTargets;

    public Seeker() {
        this.foundTargets = new LinkedHashSet<>();
    }

    public Seeker(Sensor sensor, SimpleRandomMoverManager moverManager, int numberToFind,
            double criterion) {
        this();
        setSensor(sensor);
        setNumberToFind(numberToFind);
        this.setMoverManager(moverManager);
        this.setCriterion(criterion);
    }

    @Override
    public void reset() {
        super.reset();
        this.foundTargets.clear();
        this.moverManager.setMover(sensor.getMover());
        this.moverManager.setStartOnRun(true);
    }

    public void doRun() {
        firePropertyChange("foundTargets", getFoundTargets());
    }

    public void doDetection(Mover target) {
        Set<Mover> oldFoundTargets = getFoundTargets();
        this.foundTargets.add(target);
        firePropertyChange("foundTargets", oldFoundTargets, getFoundTargets());

        if (foundTargets.size() == numberToFind) {
            waitDelay("Stop", 0.0);
        }
    }

    public void doStop() {
        firePropertyChange("timeToFindAll", Schedule.getSimTime());
    }

    /**
     * @return the sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
        this.sensor.addSimEventListener(this);
    }

    /**
     * @return the numberToFind
     */
    public int getNumberToFind() {
        return numberToFind;
    }

    /**
     * @param numberToFind the numberToFind to set
     */
    public void setNumberToFind(int numberToFind) {
        this.numberToFind = numberToFind;
    }

    /**
     * @return the moverManager
     */
    public SimpleRandomMoverManager getMoverManager() {
        return moverManager;
    }

    /**
     * @param moverManager the moverManager to set
     */
    public void setMoverManager(SimpleRandomMoverManager moverManager) {
        this.moverManager = moverManager;
        this.addSimEventListener(this.moverManager);
    }

    /**
     * @return the foundTargets
     */
    public Set<Mover> getFoundTargets() {
        return new LinkedHashSet<>(foundTargets);
    }

    /**
     * @return the criterion
     */
    public double getCriterion() {
        return criterion;
    }

    /**
     * @param criterion the criterion to set
     */
    public void setCriterion(double criterion) {
        this.criterion = criterion;
    }

}
