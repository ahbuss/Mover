package mv3302;

import static java.lang.Double.NaN;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import static simkit.Priority.HIGH;
import static simkit.Priority.HIGHER;
import simkit.SimEntityBase;

/**
 * Implementation of the multiple server queue where Customer objects carry in
 * their serviceTimes (which are the amount of "work" needed for service). The
 * Servers each have their own "efficiency" that is multiplied by a customer's
 * serviceTime to give the actual processing time. Efficiency &lt; 1 means the
 * server works faster than "normal"; efficiency &gt; 1 means the server works
 * slower than "normal", and efficiency of 1 results in the customer's service
 * time being the processing time.
 * @author ahbuss
 */
public class ServerWithEfficiencies extends SimEntityBase {

    private Server[] allServers;

    protected SortedSet<CustomerWithServiceTime> queue;

    protected Set<Server> availableServers;

    protected double delayInQueue;

    protected double timeInSystem;

    /**
     * Instantiate queue and availableServers
     */
    public ServerWithEfficiencies() {
        super();
        this.queue = new TreeSet<>();
        this.availableServers = new LinkedHashSet<>();
    }

    /**
     * Sets allServers to given array
     * @param allServers Given array of Servers
     */
    public ServerWithEfficiencies(Server[] allServers) {
        this();
        this.setAllServers(allServers);
    }

    /**
     * Clear queue and availableServers (will be set in Init event);
     * Initialize delayInQueue and timeInSystem to NaN
     */
    @Override
    public void reset() {
        super.reset();
        this.queue.clear();
        this.availableServers.clear();
        this.delayInQueue = NaN;
        this.timeInSystem = NaN;
    }

    /**
     * Schedule Init(0) with HIGHER priority and 0.0 delay
     */
    public void doRun() {
        firePropertyChange("delayInQueue", getDelayInQueue());
        firePropertyChange("timeInSystem", getTimeInSystem());

        waitDelay("Init", 0.0, HIGHER, 0);
    }

    /**
     * Add allServers[i] to availableServers; <br>
     * If i &lt; allServers.length - 1, schedule Init(i + 1) with HIGHER
     * priority and 0.0 delay
     * @param i Given index
     */
    public void doInit(int i) {
        availableServers.add(allServers[i]);
        firePropertyChange("availableServers", getAvailableServers());

        if (i < allServers.length - 1) {
            waitDelay("Init", 0.0, HIGHER, i + 1);
        }
    }

    /**
     * Stamp customer's time (for use in computing delayInQueue and timeInSystem);
     * Add customer to queue;<br>
     * If availableServers is not empty, schedule StartService with HIGH
     * priority and 0.0 delay
     * @param customer Arriving Customer
     */
    public void doArrival(CustomerWithServiceTime customer) {
        customer.stampTime();

        queue.add(customer);
        firePropertyChange("queue", getQueue());

        if (!availableServers.isEmpty()) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }

    /**
     * Get first Customer in queue; delayInQueue is their elapsedTime;
     * Remove customer from queue; Remove first Server from availableServers;<br>
     * Schedule EndService(server,customer) with delay customer's service time
     * * server's efficiency
     */
    public void doStartService() {
        CustomerWithServiceTime customer = queue.first();
        
        delayInQueue = customer.getElapsedTime();
        firePropertyChange("delayInQueue", getDelayInQueue());
        
        queue.remove(customer);
        firePropertyChange("queue", getQueue());
        
        Server server = availableServers.iterator().next();
        availableServers.remove(server);
        firePropertyChange("availableServers", getAvailableServers());
        
        double actualServiceTime = customer.getServiceTime() * server.getEfficiency();
        waitDelay("EndService", actualServiceTime, server, customer);
    }
    
    /**
     * Completes service on given Customer with given Server.<br>
     * timeInSystem is customer's elapsedTime; add server to availableServers.<br>
     * If queue is not empty, schedule StartService with HIGH priority and 
     * delay 0.0.
     * @param server  Server completing service
     * @param customer Customer completing service
     */
    public void doEndService(Server server, CustomerWithServiceTime customer) {
        timeInSystem = customer.getElapsedTime();
        firePropertyChange("timeInSystem", getTimeInSystem());
        
        availableServers.add(server);
        
        if (!queue.isEmpty()) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }
    
    /**
     * @return (copy of)  allServers
     */
    public Server[] getAllServers() {
        return allServers.clone();
    }

    /**
     * Sets a copy
     * @param allServers the allServers to set
     */
    public void setAllServers(Server[] allServers) {
        this.allServers = allServers.clone();
    }

    /**
     * @return copy of queue
     */
    public SortedSet<CustomerWithServiceTime> getQueue() {
        return new TreeSet<>(queue);
    }

    /**
     * @return copy of availableServers
     */
    public Set<Server> getAvailableServers() {
        return new LinkedHashSet<>(availableServers);
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
