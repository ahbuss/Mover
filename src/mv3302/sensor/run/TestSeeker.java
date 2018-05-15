package mv3302.sensor.run;

import java.awt.geom.Point2D;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import mv3302.mover.SimpleMover;
import mv3302.mover.SimpleRandomMoverManager;
import mv3302.sensor.Seeker;
import mv3302.sensor.SimpleCookieCutterMediator;
import mv3302.sensor.SimpleCookieCutterSensor;
import mv3302.sensor.SimpleMediator;
import mv3302.sensor.SimpleReferee;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.smd.Mover;
import simkit.smd.Sensor;
import simkit.stat.SimpleStatsTally;
import simkit.stat.StudentT;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class TestSeeker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate[] rv = new RandomVariate[]{
            RandomVariateFactory.getInstance("Uniform", -50.0, 200.0),
            RandomVariateFactory.getInstance("Uniform", -50.0, 100.0),};
        int numberTargets = 100;
        Mover[] targets = new Mover[numberTargets];
        for (int i = 0; i < targets.length; ++i) {
            targets[i] = new SimpleMover(new Point2D.Double(rv[0].generate(), rv[1].generate()), 0.0);
        }

        rv = new RandomVariate[]{
            RandomVariateFactory.getInstance("Uniform", -200.0, 200.0),
            RandomVariateFactory.getInstance("Uniform", -100.0, 100.0),};
        
        Mover uav = new SimpleMover(new Point2D.Double(0.0, 0.0), 30.0);
        SimpleRandomMoverManager moverManager = new SimpleRandomMoverManager(uav, rv, true);

        Sensor sensor = new SimpleCookieCutterSensor(uav, 10.0);

        Seeker seeker = new Seeker(sensor, moverManager, numberTargets);

        Set<SimpleMediator> mediator = new HashSet<>();
        mediator.add(new SimpleCookieCutterMediator());

        Set<Mover> targetSet = new HashSet<>(Arrays.asList(targets));
        Set<Sensor> sensors = new HashSet<>();
        sensors.add(sensor);

        SimpleReferee referee = new SimpleReferee();
        referee.setSensors(sensors);
        referee.setTargets(targetSet);
        referee.setMediators(mediator);

        System.out.println(seeker);
        System.out.println(referee);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);

//        seeker.addPropertyChangeListener(simplePropertyDumper);
//        sensor.addPropertyChangeListener(simplePropertyDumper);
//        uav.addPropertyChangeListener(simplePropertyDumper);
//        Schedule.setVerbose(true);
        SimpleStatsTally timeToFindAllStat = new SimpleStatsTally("timeToFindAll");
        seeker.addPropertyChangeListener(timeToFindAllStat);

        int numberReplications = 50;

        for (int replication = 1; replication <= numberReplications; ++replication) {
            Schedule.reset();
            Schedule.startSimulation();
        }

        System.out.printf("Avg time to find all %d targets, based on %d replications: %,.3f%n",
                numberTargets, timeToFindAllStat.getCount(), timeToFindAllStat.getMean());
        System.out.printf("\u00BD-width: %.3f%n", StudentT.getQuantile(0.975, numberReplications - 1) *
                timeToFindAllStat.getStandardDeviation() / (sqrt(numberReplications)));

    }

}
