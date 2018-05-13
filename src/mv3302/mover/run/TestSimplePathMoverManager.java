package mv3302.mover.run;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import mv3302.mover.SimpleMover;
import mv3302.mover.SimplePathMoverManager;
import simkit.Schedule;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestSimplePathMoverManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point2D initialLocation = new Point2D.Double(1.2, 3.4);
        double maxSpeed = 30.0;
        SimpleMover simpleMover = new SimpleMover(initialLocation, maxSpeed);
        
        List<Point2D> path = new ArrayList<>();
        path.add(new Point2D.Double(-25.0, 32.0));
        path.add(new Point2D.Double(5.0, -45.0));
        path.add(new Point2D.Double(0.0, 10.0));
        
        SimplePathMoverManager simplePathMoverManager =
                new SimplePathMoverManager(simpleMover, path, true);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        simpleMover.addPropertyChangeListener(simplePropertyDumper);
        simplePathMoverManager.addPropertyChangeListener(simplePropertyDumper);
        
        System.out.println(simpleMover);
        simplePathMoverManager.waitDelay("Stop", 4.5);
        System.out.println(simplePathMoverManager);
        
        Schedule.setVerbose(true);
        
        Schedule.reset();
        Schedule.startSimulation();
    }
    
}
