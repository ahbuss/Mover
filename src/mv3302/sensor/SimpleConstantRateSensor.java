package mv3302.sensor;

import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimpleConstantRateSensor extends AbstractSimpleSensor {
    
    private double meanTimeToDetect;
    
    public SimpleConstantRateSensor() {
        super();
    }
    public SimpleConstantRateSensor(Mover mover, double maxRange, double meanTimeToDetect) {
        this();
        setMover(mover);
        setMaxRange(maxRange);
        setMeanTimeToDetect(meanTimeToDetect);
    }

    /**
     * @return the meanTimeToDetect
     */
    public double getMeanTimeToDetect() {
        return meanTimeToDetect;
    }

    /**
     * @param meanTimeToDetect the meanTimeToDetect to set
     */
    public void setMeanTimeToDetect(double meanTimeToDetect) {
        this.meanTimeToDetect = meanTimeToDetect;
    }
    
}
