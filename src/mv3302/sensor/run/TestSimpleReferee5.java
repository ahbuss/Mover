package mv3302.sensor.run;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashSet;
import mv3302.mover.SimpleMover;
import mv3302.mover.SimplePatrolMoverManager;
import mv3302.mover.SimpleRandomMoverManager;
import mv3302.sensor.SimpleConstantRateMediator;
import mv3302.sensor.SimpleConstantRateSensor;
import mv3302.sensor.SimpleConstantTimeMediator;
import mv3302.sensor.SimpleConstantTimeSensor;
import mv3302.sensor.SimpleCookieCutterMediator;
import mv3302.sensor.SimpleCookieCutterSensor;
import mv3302.sensor.SimpleMediator;
import mv3302.sensor.SimpleReferee;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.smd.Mover;
import simkit.smd.Sensor;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestSimpleReferee5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mover[] movers = new Mover[] {
            new SimpleMover(new Point2D.Double(0.0, 0.0), 30.0),
            new SimpleMover(new Point2D.Double(100.0, 0.0), 25.0),
            new SimpleMover(new Point2D.Double(-10.0, 35.0), 40.0)
        };
        
        Sensor[] sensors = new Sensor[movers.length];
        sensors[0] = new SimpleCookieCutterSensor(movers[0], 10.0);
        sensors[1] = new SimpleConstantTimeSensor(movers[1], 15.0, 0.2);
        sensors[2] = new SimpleConstantRateSensor(movers[1], 15.0, 0.25);
        
        Point2D[] path1 = new Point2D[] {
            new Point2D.Double(150.0, 0.0),
            new Point2D.Double(-100, 0.0)
        };
        SimplePatrolMoverManager moverManager1 = 
                new SimplePatrolMoverManager(movers[0], Arrays.asList(path1), true);
        SimpleMediator[] mediators = new SimpleMediator[] {new SimpleCookieCutterMediator(),
            new SimpleConstantTimeMediator(), new SimpleConstantRateMediator()};
        
        Point2D[] path2 = new Point2D[] {
            new Point2D.Double(-70, 0.0),
            new Point2D.Double(100.0, 0.0)
        };
        SimplePatrolMoverManager moverManager2 = 
                new SimplePatrolMoverManager(movers[1], Arrays.asList(path2), true);
        
        RandomVariate[] rv = new RandomVariate[] {
            RandomVariateFactory.getInstance("Uniform", -100.0, 100.0),
            RandomVariateFactory.getInstance("Uniform", 0.0, 150.0)
        };
        
        SimpleRandomMoverManager moverManager3 =
                new SimpleRandomMoverManager(movers[2], rv, true);
        
        SimpleReferee simpleReferee = new SimpleReferee();
        simpleReferee.setTargets(new HashSet<>(Arrays.asList(movers)));
        simpleReferee.setSensors(new HashSet<>(Arrays.asList(sensors)));
        simpleReferee.setMediators(new HashSet<>(Arrays.asList(mediators)));
        
        System.out.println(simpleReferee);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        for (Mover mover: movers) {
            mover.addPropertyChangeListener(simplePropertyDumper);
        }
        for (Sensor sensor: sensors) {
            sensor.addPropertyChangeListener(simplePropertyDumper);
        }
        
       moverManager1.addPropertyChangeListener(simplePropertyDumper);
       moverManager2.addPropertyChangeListener(simplePropertyDumper);
       
       Schedule.setVerbose(true);
       Schedule.stopOnEvent(10, "Detection", SimpleMover.class);
       
       Schedule.reset();
       Schedule.startSimulation();
       
    }
    
}
