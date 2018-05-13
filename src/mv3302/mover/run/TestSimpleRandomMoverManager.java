package mv3302.mover.run;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import mv3302.mover.SimpleMover;
import mv3302.mover.SimplePathMoverManager;
import mv3302.mover.SimpleRandomMoverManager;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestSimpleRandomMoverManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point2D initialLocation = new Point2D.Double(1.2, 3.4);
        double maxSpeed = 30.0;
        SimpleMover simpleMover = new SimpleMover(initialLocation, maxSpeed);
        
        RandomVariate[] coordinateGenerator = new RandomVariate[2];
        coordinateGenerator[0] = RandomVariateFactory.getInstance("Uniform", -100.0, 100.0);
        coordinateGenerator[1] = RandomVariateFactory.getInstance("Uniform", 0.0, 250.0);
        
        SimpleRandomMoverManager simpleRandomMoverManager =
                new SimpleRandomMoverManager(simpleMover, coordinateGenerator, true);
        
        simpleMover.addSimEventListener(simpleRandomMoverManager);
        simpleRandomMoverManager.addSimEventListener(simpleMover);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        simpleMover.addPropertyChangeListener(simplePropertyDumper);
        simpleRandomMoverManager.addPropertyChangeListener(simplePropertyDumper);
        
        System.out.println(simpleMover);
        System.out.println(simpleRandomMoverManager);
        
        Schedule.setVerbose(true);
        
        Schedule.reset();
        simpleRandomMoverManager.waitDelay("Stop", 50.0);
        Schedule.startSimulation();
    }
    
}
