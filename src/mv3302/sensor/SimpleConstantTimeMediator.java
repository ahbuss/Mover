package mv3302.sensor;

import simkit.SimEntityBase;
import simkit.smd.Mover;

/**
 *
 * @author ahbuss
 */
public class SimpleConstantTimeMediator extends SimEntityBase implements SimpleMediator {
    
    public void doEnterRange(Mover target, SimpleConstantTimeSensor sensor) {
        sensor.waitDelay("Detection", sensor.getTimeToDetect(), target);
    }

    public void doExitRange(Mover target, SimpleConstantTimeSensor sensor) {
        if (sensor.getContacts().contains(target)) {
            sensor.waitDelay("Undetection", 0.0, target);
        } else {
            sensor.interrupt("Detection", target);
        }
        
    }
}
