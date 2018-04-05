package mv3302.run;

import mv3302.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class RunArrivalProcess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate rv = RandomVariateFactory.getInstance("Exponential", 1.7);
        ArrivalProcess arrivalProcess = new ArrivalProcess(rv);
//        arrivalProcess.setInterarrivalTimeGenerator(rv);
        System.out.println(arrivalProcess);
    }

}
