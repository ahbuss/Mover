package mv3302.run;

import mv3302.EntityCreator;
import mv3302.EntityServer;
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
        
        RandomVariate serviceTimeGenerator = RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);
        int totalNumberServers = 2;
        
        EntityServer entityServer = new EntityServer();
        entityServer.setTotalNumberServers(totalNumberServers);
        entityServer.setServiceTimeGenerator(serviceTimeGenerator);
        
        entityCreator.addSimEventListener(entityServer);
        
        entityServer.addPropertyChangeListener(simplePropertyDumper);
        
        System.out.println(entityCreator);
        System.out.println(entityServer);
        
        Schedule.setVerbose(true);
        
        Schedule.stopAtTime(6.0);
        
        Schedule.reset();
        Schedule.startSimulation();
        
    }

}
