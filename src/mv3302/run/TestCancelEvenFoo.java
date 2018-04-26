package mv3302.run;

import mv3302.CancelEvenFoo;
import simkit.Schedule;

/**
 * This illustrates canceling events with arguments.
 * @author ahbuss
 */
public class TestCancelEvenFoo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Schedule Foo(0),...,Foo(9)
        int numberFoo = 10;
//        Cancel even Foo(i) where i ≤ 5
        int numberCancel = 5;
        
//        Instantiate CancelEvenFoo
        CancelEvenFoo cancelEvenFoo = new CancelEvenFoo(numberFoo, numberCancel);
//        Verify that the parameters have been received by the cancelEvenFoo object
        System.out.println(cancelEvenFoo);
        
//        Run the simulation in versbose mode. After Inits have finished,
//        there should be Foo(0),...,Foo(9) on the event list at time 2.0 and
//        Cancel(0) at time 1.0. Since the events will eventually all occur,
//        there is no need for Schedule.stopAtTime(...) since the simulation
//        ends when the event list is empty.
        Schedule.setVerbose(true);
        Schedule.reset();
        Schedule.startSimulation();
        
    }

}
