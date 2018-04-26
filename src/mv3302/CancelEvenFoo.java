package mv3302;

import simkit.SimEntityBase;

/**
 * This is a simple illustration of how canceling events in Simkit are
 * implemented. Canceling is done via a call to the "interrupt" method. The
 * require first parameter is the name of the event to cancel, and the optional
 * additional parameter(s) specify the exact event to cancel. Only the first
 * matching event is canceled - where "matching" means both the name of the
 * event (case-sensitive) as well as the exact parameter value that the event
 * had been scheduled with.
 *
 * @author ahbuss
 */
public class CancelEvenFoo extends SimEntityBase {

    /**
     * Schedule this many Foo(i) events
     */
    private int numberFoo;

    /**
     * This is actually the upper bound on which even "Foo" events are to be
     * canceled.
     */
    private int numberCancel;

    public CancelEvenFoo() {
        super();
    }

    /**
     *
     * @param numberFoo Given number for Foo events to schedule initially
     * @param numberCancel Given upper bound on the even Foo events to cancel
     */
    public CancelEvenFoo(int numberFoo, int numberCancel) {
        this();
        this.setNumberFoo(numberFoo);
        this.setNumberCancel(numberCancel);
    }

    /**
     * Schedule Init(0) with zero delay and "Cancel(0) with delay of 1.0
     */
    public void doRun() {
        waitDelay("Init", 0.0, 0);
        waitDelay("Cancel", 1.0, 0);
    }

    /**
     * Schedule Foo(i) with delay of 2.0; if i &lt; numberFoo - 1, schedule
     * Foo(i+1) with zero delay
     *
     * @param i Argument for scheduled Foo(i)
     */
    public void doInit(int i) {
        waitDelay("Foo", 2.0, i);
        if (i < numberFoo - 1) {
            waitDelay("Init", 0.0, i + 1);
        }
    }

    /**
     * Does nothing
     *
     * @param i Given i
     */
    public void doFoo(int i) {
    }

    /**
     * Cancel Foo(i) event; if i &lt; numberToCancel, schedule Cancel(i+2) with
     * zero delay
     *
     * @param i Given parameter to be matched with the Foo event to cancel
     */
    public void doCancel(int i) {
        interrupt("Foo", i);
        if (i < numberCancel) {
            waitDelay("Cancel", 0.0, i + 2);
        }
    }

    /**
     * @return the numberFoo
     */
    public int getNumberFoo() {
        return numberFoo;
    }

    /**
     * @param numberFoo the numberFoo to set
     * @throws IllegalArgumentException if numberFoo \u2264 0
     */
    public void setNumberFoo(int numberFoo) {
        if (numberFoo <= 0) {
            throw new IllegalArgumentException("numberFoo must be > 0: " + numberFoo);
        }
        this.numberFoo = numberFoo;
    }

    /**
     * @return the numberCancel
     */
    public int getNumberCancel() {
        return numberCancel;
    }

    /**
     * @param numberCancel the numberCancel to set
     * @throws IllegalArgumentException if numberCancel \u2264 0
     */
    public void setNumberCancel(int numberCancel) {
        if (numberCancel <= 0) {
            throw new IllegalArgumentException("numberCancel must be > 0: " + numberCancel);
        }

        this.numberCancel = numberCancel;
    }

}
