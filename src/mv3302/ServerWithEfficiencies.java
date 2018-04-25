package mv3302;

import simkit.SimEntityBase;

/**
 *
 * @author ahbuss
 */
public class ServerWithEfficiencies extends SimEntityBase {
    
    private Server[] allServers;
    
    public ServerWithEfficiencies() {
        super();
    }
    
    public ServerWithEfficiencies(Server[] allServers) {
        this();
        this.setAllServers(allServers);
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
    
    

}
