package mv3302.run;

import mv3302.CustomerArrivalProcess;
import mv3302.Server;
import mv3302.ServerWithEfficiencies;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.stat.MultipleSimpleStatsTimeVarying;
import simkit.stat.SimpleStatsTally;
import simkit.util.SimplePropertyDumper;

/**
 *
 * @author ahbuss
 */
public class RunServerWithEfficiencies {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server[] allServers = new Server[3];
        allServers[0] = new Server(1.5);
        allServers[1] = new Server(1.5);
        allServers[2] = new Server(1.5);

        ServerWithEfficiencies serverWithEfficiencies =
                new ServerWithEfficiencies(allServers);
        System.out.println(serverWithEfficiencies);
        
        RandomVariate interarrivalTimeGenerator =
                RandomVariateFactory.getInstance("Uniform", 0.9, 2.2);
        RandomVariate serviceTimeGenerator =
                RandomVariateFactory.getInstance("Gamma", 1.7, 1.8);
        CustomerArrivalProcess arrivalProcess = 
                new CustomerArrivalProcess(interarrivalTimeGenerator, serviceTimeGenerator);
        System.out.println(arrivalProcess);
        
        SimplePropertyDumper simplePropertyDumper = new SimplePropertyDumper(true);
//        serverWithEfficiencies.addPropertyChangeListener(simplePropertyDumper);
        
        arrivalProcess.addSimEventListener(serverWithEfficiencies);
        
        SimpleStatsTally delayInQueueStat = new SimpleStatsTally("delayInQueue");
        serverWithEfficiencies.addPropertyChangeListener(delayInQueueStat);
        
        MultipleSimpleStatsTimeVarying serverBusyStat = new MultipleSimpleStatsTimeVarying("busy");
        serverWithEfficiencies.addPropertyChangeListener(serverBusyStat);
        
        Schedule.setVerbose(false);
        
        Schedule.stopAtTime(100000.0);
        
        Schedule.reset();
        Schedule.startSimulation();
        
        System.out.printf("Simluation ended at time %,.1f%n", Schedule.getSimTime());
        System.out.printf("Avg delayInQueue = %,.4f%n", delayInQueueStat.getMean());
        
//        System.out.println(serverBusyStat);
        for (int i = 1; i < serverBusyStat.getAllSampleStat().length; ++i) {
            System.out.printf("Avg Utilization of server %d: %.4f%n", i, serverBusyStat.getMean(i));
        }
//        System.out.println(new Customer(123.456));
//        new Customer(-1.0);
//        for (Server server : allServers) {
//            System.out.println(server);
//        }
        
//        new Server(-1.0);
//        new Server(0.0);
    }

}
