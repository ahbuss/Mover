package mv3302;

import static java.lang.Double.NaN;
import java.util.SortedSet;
import java.util.TreeSet;
import simkit.Entity;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class EntityServer extends SimEntityBase {

    private int totalNumberServers;
    
    private RandomVariate serviceTimeGenerator;
    
    protected int numberAvailableServers;
    
    protected SortedSet<Entity> queue;
    
    protected double delayInQueue;
    
    protected double timeInSystem;
    
//    TODO: constructors
    
    public EntityServer() {
        this.queue = new TreeSet<>();
    }
    
    public void reset() {
        super.reset();
        this.numberAvailableServers = getTotalNumberServers();
        this.queue.clear();
        this.delayInQueue = NaN;
        this.timeInSystem = NaN;
    }
    
    public void doRun() {
        firePropertyChange("numberAvailableServers", getNumberAvailableServers());
        firePropertyChange("queue", getQueue());
        firePropertyChange("delayInQueue", getDelayInQueue());
        firePropertyChange("timeInSystem", getTimeInSystem());
    }
    
    public void doArrival(Entity entity) {
        entity.stampTime();
        
        SortedSet<Entity> oldQueue = getQueue();
        queue.add(entity);
        firePropertyChange("queue", oldQueue, getQueue());
    }

    /**
     * @return the totalNumberServers
     */
    public int getTotalNumberServers() {
        return totalNumberServers;
    }

    /**
     * TODO: throw exception if less than or equal to 0
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
     * @return the numberAvailableServers
     */
    public int getNumberAvailableServers() {
        return numberAvailableServers;
    }

    /**
     * @return the queue
     */
    public SortedSet<Entity> getQueue() {
        return new TreeSet<>(queue);
    }

    /**
     * @return the delayInQueue
     */
    public double getDelayInQueue() {
        return delayInQueue;
    }

    /**
     * @return the timeInSystem
     */
    public double getTimeInSystem() {
        return timeInSystem;
    }
    
}
