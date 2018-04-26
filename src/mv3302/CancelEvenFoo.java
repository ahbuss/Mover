package mv3302;

import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class CancelEvenFoo extends SimEntityBase {

    private int numberFoo;
    
    private int numberCancel;

    public CancelEvenFoo() {
        super();
    }
    
    public CancelEvenFoo(int numberFoo, int numberCancel) {
        this();
        this.setNumberFoo(numberFoo);
        this.setNumberCancel(numberCancel);
    }
    
    public void doRun() {
        waitDelay("Init", 0.0, 0);
        waitDelay("Cancel", 1.0, 0);
    }
    
    public void doInit(int i) {
        waitDelay("Foo", 2.0, i);
        if (i < numberFoo - 1) {
            waitDelay("Init", 0.0, i + 1);
        }
    }
    
    public void doFoo(int i) {
    }
    
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
     */
    public void setNumberFoo(int numberFoo) {
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
     */
    public void setNumberCancel(int numberCancel) {
        this.numberCancel = numberCancel;
    }
    
    
    
}
