package mv3302.sensor;

import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimpleCookieCutterSensor extends AbstractSimpleSensor {
    
    public SimpleCookieCutterSensor() {
        super();
    }
    
    public SimpleCookieCutterSensor(Mover mover, double maxRange) {
        this();
        setMover(mover);
        setMaxRange(maxRange);
    }
    
}
