package mv3302.run;

import mv3302.CancelEvenFoo;
import simkit.Schedule;

/**
 *
 * @author ahbuss
 */
public class TestCancelEvenFoo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numberFoo = 10;
        int numberCancel = 5;
        
        CancelEvenFoo cancelEvenFoo = new CancelEvenFoo(numberFoo, numberCancel);
        System.out.println(cancelEvenFoo);
        
        Schedule.setVerbose(true);
        Schedule.reset();
        Schedule.startSimulation();
        
    }

}
