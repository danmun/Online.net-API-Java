/**
 * Copyright (c) 2016, danmun
 */
package api.server.hardware;

import api.server.Ref;

/**
 * 
 * @author danmun
 */
public class RaidControllerRef extends Ref{

    public String getRaidControllerRef() {
        return super.getRef();
    }

    public void setRaidControllerRef(String raidControllerRef) {
        super.setRef(raidControllerRef);
    }
    
    public String toString(){
        return "RaidControllerRef {[ getRaidControllerRef=" + getRaidControllerRef() + " ]}";
    }
}
