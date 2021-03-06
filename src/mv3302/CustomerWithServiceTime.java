package mv3302;

import simkit.Entity;

/**
 *
 * @author ahbuss
 */
public class CustomerWithServiceTime extends Entity {

    private double serviceTime;

    public CustomerWithServiceTime() {
        super("Customer");
    }
    
    public CustomerWithServiceTime(double serviceTime) {
        this();
        this.setServiceTime(serviceTime);
    }
    
    /**
     * @return the serviceTime
     */
    public double getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime the serviceTime to set
     */
    public void setServiceTime(double serviceTime) {
        if (serviceTime <= 0.0) {
            throw new IllegalArgumentException(
                "serviceTime must be > 0.0: " + serviceTime);
        }
        this.serviceTime = serviceTime;
    }
    
    @Override
    public String toString() {
        return String.format("%s %,.3f", super.toString(), getServiceTime());
    }
    
}
