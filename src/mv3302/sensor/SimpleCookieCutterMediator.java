package mv3302.sensor;

import mv3302.mover.SimpleMover;
import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class SimpleCookieCutterMediator extends SimEntityBase implements SimpleMediator {

    public void doEnterRange(SimpleMover target, SimpleCookieCutterSensor sensor ) {
        sensor.waitDelay("Detection", 0.0, target);
    }

    public void doExitRange(SimpleMover target, SimpleCookieCutterSensor sensor ) {
        sensor.waitDelay("Undetection", 0.0, target);
    }
    
}
