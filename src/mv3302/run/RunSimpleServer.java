package mv3302.run;

import mv3302.ArrivalProcess;
import mv3302.SimpleServer;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.SimpleStatsTimeVarying;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunSimpleServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        RandomVariate interarrivalTimeGenerator = 
                RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
        ArrivalProcess arrivalProcess = new ArrivalProcess(interarrivalTimeGenerator);
        
        int totalNumberServers = 2;
        RandomVariate serviceTimeGenerator = 
                RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);
        
        SimpleServer simpleServer = new SimpleServer( totalNumberServers, serviceTimeGenerator);
        
        arrivalProcess.addSimEventListener(simpleServer);
        
        System.out.println(arrivalProcess);
        System.out.println(simpleServer);

        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
        simpleServer.addPropertyChangeListener(simplePropertyDumper);
        arrivalProcess.addPropertyChangeListener(simplePropertyDumper);
        
        SimpleStatsTimeVarying numberInQueueStat = new SimpleStatsTimeVarying("numberInQueue");
        simpleServer.addPropertyChangeListener(numberInQueueStat);
        
        Schedule.setVerbose(true);
        Schedule.stopAtTime(20.0);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("At time %,.1f there were %d served%n", Schedule.getSimTime(), simpleServer.getNumberServed());
        System.out.printf("Average # in queue: %,.3f%n", numberInQueueStat.getMean());
        
    }

}
