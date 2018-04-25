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
 *
 * @author ahbuss
 */
public class ServerWithEfficiencies extends SimEntityBase {

    private Server[] allServers;

    protected SortedSet<CustomerWithServiceTime> queue;

    protected Set<Server> availableServers;

    protected double delayInQueue;

    protected double timeInSystem;

    public ServerWithEfficiencies() {
        super();
        this.queue = new TreeSet<>();
        this.availableServers = new LinkedHashSet<>();
    }

    public ServerWithEfficiencies(Server[] allServers) {
        this();
        this.setAllServers(allServers);
    }

    @Override
    public void reset() {
        super.reset();
        this.queue.clear();
        this.availableServers.clear();
        this.delayInQueue = NaN;
        this.timeInSystem = NaN;
    }

    public void doRun() {
        firePropertyChange("delayInQueue", getDelayInQueue());
        firePropertyChange("timeInSystem", getTimeInSystem());

        waitDelay("Init", 0.0, HIGHER, 0);
    }

    public void doInit(int i) {
        availableServers.add(allServers[i]);
        firePropertyChange("availableServers", getAvailableServers());

        if (i < allServers.length - 1) {
            waitDelay("Init", 0.0, HIGHER, i + 1);
        }
    }

    public void doArrival(CustomerWithServiceTime customer) {
        customer.stampTime();

        queue.add(customer);
        firePropertyChange("queue", getQueue());

        if (!availableServers.isEmpty()) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }

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
    
    public void doEndService(Server server, CustomerWithServiceTime customer) {
        timeInSystem = customer.getElapsedTime();
        firePropertyChange("timeInSystem", getTimeInSystem());
        
        availableServers.add(server);
        
        if (!queue.isEmpty()) {
            waitDelay("StartService", 0.0, HIGH);
        }
    }
    
    /**
     * @return the allServers
     */
    public Server[] getAllServers() {
        return allServers.clone();
    }

    /**
     * @param allServers the allServers to set
     */
    public void setAllServers(Server[] allServers) {
        this.allServers = allServers.clone();
    }

    /**
     * @return the queue
     */
    public SortedSet<CustomerWithServiceTime> getQueue() {
        return new TreeSet<>(queue);
    }

    /**
     * @return the availableServers
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
