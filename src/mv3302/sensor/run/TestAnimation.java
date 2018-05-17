package mv3302.sensor.run;

import java.awt.Color;
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
import simkit.animate.SandboxFrame;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.smd.Mover;
import simkit.smd.Sensor;
import simkit.util.PropertyChangeFrame;

/**
 *
 * @author ahbuss
 */
public class TestAnimation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mover[] movers = new Mover[]{
            new SimpleMover(new Point2D.Double(0.0, 0.0), 30.0),
            new SimpleMover(new Point2D.Double(100.0, 0.0), 25.0),
            new SimpleMover(new Point2D.Double(-100.0, 35.0), 40.0)
        };

        Sensor[] sensors = new Sensor[movers.length];
        sensors[0] = new SimpleCookieCutterSensor(movers[0], 30.0);
        sensors[1] = new SimpleConstantTimeSensor(movers[1], 45.0, 0.2);
        sensors[2] = new SimpleConstantRateSensor(movers[2], 55.0, 0.25);

        Point2D[] path1 = new Point2D[]{
            new Point2D.Double(150.0, 0.0),
            new Point2D.Double(50.0, 100.0),
            new Point2D.Double(-100, 0.0),
            new Point2D.Double(0, -100.0)
        };
        SimplePatrolMoverManager moverManager1
                = new SimplePatrolMoverManager(movers[0], Arrays.asList(path1), true);
        SimpleMediator[] mediators = new SimpleMediator[]{new SimpleCookieCutterMediator(),
            new SimpleConstantTimeMediator(), new SimpleConstantRateMediator()};

        Point2D[] path2 = new Point2D[]{
            new Point2D.Double(-70, 0.0),
            new Point2D.Double(100.0, 0.0)
        };
        SimplePatrolMoverManager moverManager2
                = new SimplePatrolMoverManager(movers[1], Arrays.asList(path2), true);

        RandomVariate[] rv = new RandomVariate[]{
            RandomVariateFactory.getInstance("Uniform", -100.0, 150.0),
            RandomVariateFactory.getInstance("Uniform", -75, 175.0)
        };

        SimpleRandomMoverManager moverManager3
                = new SimpleRandomMoverManager(movers[2], rv, true);

        SimpleReferee simpleReferee = new SimpleReferee();
        simpleReferee.setTargets(new HashSet<>(Arrays.asList(movers)));
        simpleReferee.setSensors(new HashSet<>(Arrays.asList(sensors)));
        simpleReferee.setMediators(new HashSet<>(Arrays.asList(mediators)));

        System.out.println(simpleReferee);

        SandboxFrame frame = new SandboxFrame("Computer Assignment 7");
        frame.setSize(700, 650);
        frame.getSandbox().setOrigin(new Point2D.Double(300, 350));
        frame.getSandbox().setDrawAxes(true);
        
        PropertyChangeFrame  propertyChangeFrame = new PropertyChangeFrame();
        for (Sensor sensor: sensors) {
            frame.addSensor(sensor, Color.RED);
            frame.addMover(sensor.getMover(), Color.BLUE);
            sensor.addPropertyChangeListener(propertyChangeFrame);
        }
        

        frame.setVisible(true);
        propertyChangeFrame.setVisible(true);

        Schedule.addIgnoreOnDump("Ping");
        Schedule.reset();

    }

}
