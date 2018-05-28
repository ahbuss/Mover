package mv3302.sensor.run;

import java.awt.geom.Point2D;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import mv3302.mover.SimpleMover;
import mv3302.mover.SimpleRandomMoverManager;
import mv3302.sensor.Seeker;
import mv3302.sensor.SimpleConstantRateMediator;
import mv3302.sensor.SimpleConstantRateSensor;
import mv3302.sensor.SimpleCookieCutterMediator;
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
            RandomVariateFactory.getInstance("Uniform", -25.0, 25.0),
            RandomVariateFactory.getInstance("Uniform", -25.0, 25.0),};
        int numberTargets = 5;
        Mover[] targets = new Mover[numberTargets];
        for (int i = 0; i < targets.length; ++i) {
            targets[i] = new SimpleMover(new Point2D.Double(rv[0].generate(), rv[1].generate()), 0.0);
        }

//        rv = new RandomVariate[]{
//            RandomVariateFactory.getInstance("Uniform", -50.0, 50.0),
//            RandomVariateFactory.getInstance("Uniform", -50.0, 50.0),};
        
        Mover uav = new SimpleMover(new Point2D.Double(-50.0, -50.0), 60.0);
        SimpleRandomMoverManager moverManager = new SimpleRandomMoverManager(uav, rv, true);

        Sensor sensor = new SimpleConstantRateSensor(uav, 5.0, 0.01);

        double criterion = 25.0;
        Seeker seeker = new Seeker(sensor, moverManager, numberTargets, criterion);

        Set<SimpleMediator> mediator = new HashSet<>();
        mediator.add(new SimpleConstantRateMediator());

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
//        seeker.addPropertyChangeListener(timeToFindAllStat);
        
        SimpleStatsTally probMeetsCriterionStat = new SimpleStatsTally("meetsCriterion");

        int numberReplications = 50;

        for (int replication = 1; replication <= numberReplications; ++replication) {
            Schedule.reset();
            Schedule.startSimulation();
            timeToFindAllStat.newObservation(Schedule.getSimTime());
            probMeetsCriterionStat.newObservation(Schedule.getSimTime() <= seeker.getCriterion());
        }

        System.out.printf("Avg time to find all %d targets, based on %d replications: %,.3f%n",
                numberTargets, timeToFindAllStat.getCount(), timeToFindAllStat.getMean());
        System.out.printf("\u00BD-width: %.3f%n", StudentT.getQuantile(0.975, numberReplications - 1) *
                timeToFindAllStat.getStandardDeviation() / (sqrt(numberReplications)));
        System.out.printf("Prob meets criterion of %.2f: %.4f \u00B1 %.4f%n",
                seeker.getCriterion(),
                probMeetsCriterionStat.getMean(),
                probMeetsCriterionStat.getStandardDeviation() / sqrt(probMeetsCriterionStat.getCount()) *
                        StudentT.getQuantile(0.975, probMeetsCriterionStat.getCount() - 1));

    }

}
