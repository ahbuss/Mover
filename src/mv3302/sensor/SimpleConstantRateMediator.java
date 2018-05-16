package mv3302.sensor;

import mv3302.mover.SimpleMover;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class SimpleConstantRateMediator extends SimEntityBase implements SimpleMediator {
    
    private RandomVariate exponentialGenerator;
    
    public SimpleConstantRateMediator() {
        exponentialGenerator = RandomVariateFactory.getInstance("Exponential", 1.0);
    }
    
    public void doEnterRange(SimpleMover target, SimpleConstantRateSensor sensor) {
        sensor.waitDelay("Detection", sensor.getMeanTimeToDetect() * exponentialGenerator.generate(), target);
    }
    
    public void doExitRange(SimpleMover target, SimpleConstantRateSensor sensor) {
        if (sensor.getContacts().contains(target)) {
            sensor.waitDelay("Undetection", 0.0, target);
        } else {
            sensor.interrupt("Detection", target);
        }
    }
    
}
