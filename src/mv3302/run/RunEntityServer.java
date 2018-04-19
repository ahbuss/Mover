package mv3302.run;

import mv3302.EntityCreator;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunEntityServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate interarrivalTimeGenerator =
                RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
        EntityCreator entityCreator = new EntityCreator(interarrivalTimeGenerator);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        entityCreator.addPropertyChangeListener(simplePropertyDumper);
        
        System.out.println(entityCreator);
        
        Schedule.setVerbose(true);
        
        Schedule.stopAtTime(6.0);
        
        Schedule.reset();
        Schedule.startSimulation();
        
    }

}
