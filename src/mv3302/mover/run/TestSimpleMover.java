package mv3302.mover.run;

import java.awt.geom.Point2D;
import mv3302.mover.SimpleMover;
import simkit.Schedule;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestSimpleMover {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point2D initialLocation = new Point2D.Double(1.2, 3.4);
        double maxSpeed = 30.0;
        
        SimpleMover simpleMover = new SimpleMover(initialLocation, maxSpeed);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        simpleMover.addPropertyChangeListener(simplePropertyDumper);
        
        System.out.println(simpleMover);
        
        Schedule.setVerbose(true);
        
        Schedule.reset();
        simpleMover.waitDelay("MoveTo", 1.0, new Point2D.Double(10.0, 20.0));
        simpleMover.waitDelay("Stop", 1.2, simpleMover);
        Schedule.startSimulation();
    }
    
}
