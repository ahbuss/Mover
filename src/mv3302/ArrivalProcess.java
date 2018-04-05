package mv3302;

import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class ArrivalProcess extends SimEntityBase {
    
    private RandomVariate interarrivalTimeGenerator;
    
    protected int numberArrivals;

    @Override
    public void reset() {
        super.reset();
        numberArrivals = 0;
    }
    
    public ArrivalProcess() { }
    
    public ArrivalProcess(RandomVariate interarrivalTimeGenerator) {
        this.setInterarrivalTimeGenerator(interarrivalTimeGenerator);
    }
    
    public void doRun() {
        firePropertyChange("numberArrivals", getNumberArrivals());
        
        waitDelay("Arrival", interarrivalTimeGenerator);
    }
    
    /**
     * @return the interarrivalTimeGenerator
     */
    public RandomVariate getInterarrivalTimeGenerator() {
        return interarrivalTimeGenerator;
    }

    /**
     * @param interarrivalTimeGenerator the interarrivalTimeGenerator to set
     */
    public void setInterarrivalTimeGenerator(RandomVariate interarrivalTimeGenerator) {
        this.interarrivalTimeGenerator = interarrivalTimeGenerator;
    }

    /**
     * @return the numberArrivals
     */
    public int getNumberArrivals() {
        return numberArrivals;
    }

}
