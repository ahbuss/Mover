package mv3302;

import simkit.random.RandomVariate;

/**
 *
 * @author ahbuss
 */
public class CustomerArrivalProcess extends ArrivalProcess {

    private RandomVariate serviceTimeGenerator;
    
    public CustomerArrivalProcess() {
        super();
    }
    
    public CustomerArrivalProcess(RandomVariate interarrivalTimeGenerator,
            RandomVariate serviceTimeGenerator) {
        this();
        setInterarrivalTimeGenerator(interarrivalTimeGenerator);
        setServiceTimeGenerator(serviceTimeGenerator);
    }

    public void doArrival() {
        super.doArrival();
        CustomerWithServiceTime customer = new CustomerWithServiceTime(serviceTimeGenerator.generate());
        waitDelay("Arrival", 0.0, customer);
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
    
}
