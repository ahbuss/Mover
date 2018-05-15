package mv3302.sensor;

import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimpleConstantTimeSensor extends AbstractSimpleSensor {

    private double timeToDetect;

    public SimpleConstantTimeSensor() {
        super();
    }

    public SimpleConstantTimeSensor(Mover mover, double maxRange, double timeToDetect) {
        this();
        setMover(mover);
        setMaxRange(maxRange);
        setTimeToDetect(timeToDetect);
    }

    /**
     * @return the timeToDetect
     */
    public double getTimeToDetect() {
        return timeToDetect;
    }

    /**
     * @param timeToDetect the timeToDetect to set
     */
    public void setTimeToDetect(double timeToDetect) {
        if (timeToDetect <= 0.0) {
            throw new IllegalArgumentException("timeToDetect must be \u2265 0.0: " +
                    timeToDetect);
        }
        this.timeToDetect = timeToDetect;
    }

}
