package mv3302.run;

import mv3302.CustomerArrivalProcess;
import mv3302.Server;
import mv3302.ServerWithEfficiencies;
import simkit.Schedule;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class RunServerWithEfficiencies {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server[] allServers = new Server[2];
        allServers[0] = new Server(1.0);
        allServers[1] = new Server(0.9);

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
        
        Schedule.setVerbose(true);
        
        Schedule.stopAtTime(10.0);
        
        Schedule.reset();
        Schedule.startSimulation();
        
//        System.out.println(new Customer(123.456));
//        new Customer(-1.0);
//        for (Server server : allServers) {
//            System.out.println(server);
//        }
        
//        new Server(-1.0);
//        new Server(0.0);
    }

}
