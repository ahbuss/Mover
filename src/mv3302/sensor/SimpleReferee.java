package mv3302.sensor;

import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import static java.lang.Double.max;
import static java.lang.Double.min;
import java.util.HashSet;
import java.util.Set;
import static simkit.Priority.LOW;
import simkit.SimEntityBase;
import simkit.smd.Mover;
import simkit.smd.Sensor;

/**
 *
 * @author ahbuss
 */
public class SimpleReferee extends SimEntityBase {

    private static final double EPSILON = 1.0E-7;

    private final Set<Mover> targets;

    private final Set<Sensor> sensors;

    private final Set<SimpleMediator> mediators;

    public SimpleReferee() {
        targets = new HashSet<>();
        sensors = new HashSet<>();
        mediators = new HashSet<>();
    }

    @Override
    public void reset() {
        super.reset();
        for (Sensor sensor : sensors) {
            sensor.addSimEventListener(this);
        }
        for (Mover target : targets) {
            target.addSimEventListener(this);
        }
        for (SimpleMediator mediator : mediators) {
            this.addSimEventListener(mediator);
        }
    }

    public void doStartMove(Mover mover) {
        for (Sensor sensor : sensors) {
            if (mover != sensor.getMover()) {
                waitDelay("DetermineInteraction", 0.0, LOW, mover, sensor);
            }
        }
    }

    public void doStartMove(Sensor sensor) {
        for (Mover mover : targets) {
            if (mover != sensor.getMover()) {
                waitDelay("DetermineInteraction", 0.0, LOW, mover, sensor);
            }
        }
    }

    public void doStop(Mover mover) {
        for (Sensor sensor : sensors) {
            if (mover != sensor.getMover()) {
                waitDelay("DetermineInteraction", 0.0, LOW, mover, sensor);
            }
        }
    }

    public void doStop(Sensor sensor) {
        for (Mover mover : targets) {
            if (mover != sensor.getMover()) {
                waitDelay("DetermineInteraction", 0.0, LOW, mover, sensor);
            }
        }
    }

    public void doDetermineInteraction(Mover target, Sensor sensor) {

        Point2D targetLocation = target.getCurrentLocation();
        Point2D targetVelocity = target.getVelocity();

        Point2D sensorLocation = sensor.getCurrentLocation();
        Point2D sensorVelocity = sensor.getVelocity();
        Point2D relativeLocation = new Point2D.Double(targetLocation.getX() - sensorLocation.getX(),
                targetLocation.getY() - sensorLocation.getY()); // x_0
        Point2D relativeVelocity = new Point2D.Double(targetVelocity.getX() - sensorVelocity.getX(),
                targetVelocity.getY() - sensorVelocity.getY()); // v_0
        double distanceSq = sensorLocation.distanceSq(targetLocation); // ||x_0||^2
        double relativeSpeedSq = sensorVelocity.distanceSq(targetVelocity); // ||v_0||^2
        double innerProduct = relativeLocation.getX() * relativeVelocity.getX()
                + relativeLocation.getY() * relativeVelocity.getY();

//            The target is traveling at the same velocity as the sensor
//            It has stopped relative to the sensor, so interrupt pending
//            EnterRange or ExitRange events
        if (relativeSpeedSq < EPSILON) {
            if (distanceSq < sensor.getMaxRange() * sensor.getMaxRange()) {
                interrupt("ExitRange", target, sensor);
            } else {
                interrupt("EnterRange", target, sensor);
            }
        }

        double[] coeff = new double[3];
        coeff[0] = distanceSq - sensor.getMaxRange() * sensor.getMaxRange();
        coeff[1] = 2.0 * innerProduct;
        coeff[2] = relativeSpeedSq;

        double[] times = new double[2];
        int numberRoots = QuadCurve2D.solveQuadratic(coeff, times);
        if (numberRoots == 2) {
            double minTime = min(times[0], times[1]);
            double maxTime = max(times[0], times[1]);
            if (0.0 < minTime) {
                interrupt("EnterRange", target, sensor);
                waitDelay("EnterRange", minTime, target, sensor);
            } else if (0.0 < maxTime) {
                interrupt("ExitRange", target, sensor);
                waitDelay("ExitRange", maxTime, target, sensor);
            }
        } else {
            interrupt("EnterRange", target, sensor);
        }
    }

    public void doEnterRange(Mover target, Sensor sensor) {
        Point2D targetLocation = target.getCurrentLocation();
        Point2D targetVelocity = target.getVelocity();
        Point2D sensorLocation = sensor.getCurrentLocation();
        Point2D sensorVelocity = sensor.getVelocity();
        Point2D relativeLocation = new Point2D.Double(targetLocation.getX() - sensorLocation.getX(),
                targetLocation.getY() - sensorLocation.getY()); // x_0
        Point2D relativeVelocity = new Point2D.Double(targetVelocity.getX() - sensorVelocity.getX(),
                targetVelocity.getY() - sensorVelocity.getY()); // v_0
        double distanceSq = sensorLocation.distanceSq(targetLocation); // ||x_0||^2
        double relativeSpeedSq = sensorVelocity.distanceSq(targetVelocity); // ||v_0||^2
        double innerProduct = relativeLocation.getX() * relativeVelocity.getX()
                + relativeLocation.getY() * relativeVelocity.getY();

        double[] coeff = new double[3];
        coeff[0] = distanceSq - sensor.getMaxRange() * sensor.getMaxRange();
        coeff[1] = 2.0 * innerProduct;
        coeff[2] = relativeSpeedSq;

        double[] times = new double[2];
        int numberRoots = QuadCurve2D.solveQuadratic(coeff, times);
        if (numberRoots == 2) {
            double maxTime = max(times[0], times[1]);
            interrupt("ExitRange", target, sensor);
            waitDelay("ExitRange", maxTime, target, sensor);
        }
    }

    /**
     * @return the targets
     */
    public Set<Mover> getTargets() {
        return new HashSet<>(targets);
    }

    /**
     * @return the sensors
     */
    public Set<Sensor> getSensors() {
        return new HashSet<>(sensors);
    }

    /**
     * @param targets the targets to set
     */
    public void setTargets(Set<Mover> targets) {
        this.targets.addAll(targets);
    }

    /**
     * @param sensors the sensors to set
     */
    public void setSensors(Set<Sensor> sensors) {
        this.sensors.addAll(sensors);
    }

    /**
     * @return the mediators
     */
    public Set<SimpleMediator> getMediators() {
        return new HashSet<>(mediators);
    }

    /**
     * @param mediators the mediators to set
     */
    public void setMediators(Set<SimpleMediator> mediators) {
        this.mediators.addAll(mediators);
    }

}
