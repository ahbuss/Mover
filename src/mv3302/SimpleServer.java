package mv3302;

import static simkit.Priority.HIGH;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class SimpleServer extends SimEntityBase {
    
    private int totalNumberServers;
    
    private RandomVariate serviceTimeGenerator;
    
    protected int numberInQueue;
    
    protected int numberAvailableServers;
    
    protected int numberServed;
    
    public SimpleServer() { }
    
    public SimpleServer(int totalNumberServers, RandomVariate serviceTimeGenerator) {
        this.setTotalNumberServers(totalNumberServers);
        this.setServiceTimeGenerator(serviceTimeGenerator);
    }
    
    @Override
    public void reset() {
        super.reset();
        this.numberInQueue = 0;
        this.numberAvailableServers = getTotalNumberServers();
        this.numberServed = 0;
    }
    
    public void doRun() {
        firePropertyChange("numberInQueue", getNumberInQueue());
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        firePropertyChange("numberServed", getNumberServed());
    }
    
    public void doArrival() {
        int oldNumberInQueue = getNumberInQueue();
        numberInQueue += 1;
        firePropertyChange("numberInQueue", oldNumberInQueue, getNumberInQueue());
        
        if (numberAvailableServers > 0) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }

    /**
     * @return the totalNumberServers
     */
    public int getTotalNumberServers() {
        return totalNumberServers;
    }

    /**
     * @param totalNumberServers the totalNumberServers to set
     */
    public void setTotalNumberServers(int totalNumberServers) {
        this.totalNumberServers = totalNumberServers;
    }

    /**
     * @return the serviceTimeGenerator
     */
    public RandomVariate getServiceTimeGenerator() {
        return serviceTimeGenerator;
    }

    /**
     * @param serviceTimeGenerator the serviceTimeGenerator to set
     */
    public void setServiceTimeGenerator(RandomVariate serviceTimeGenerator) {
        this.serviceTimeGenerator = serviceTimeGenerator;
    }

    /**
     * @return the numberInQueue
     */
    public int getNumberInQueue() {
        return numberInQueue;
    }

    /**
     * @return the numberAvailableServers
     */
    public int getNumberAvailableServers() {
        return numberAvailableServers;
    }

    /**
     * @return the numberServed
     */
    public int getNumberServed() {
        return numberServed;
    }

}
