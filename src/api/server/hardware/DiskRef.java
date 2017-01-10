/**
 * Copyright (c) 2016, danmun
 */
package api.server.hardware;

import api.server.Ref;

/**
 * 
 * @author danmun
 */
public class DiskRef extends Ref{
    
    public String getDiskReference() {
        return super.getRef();
    }

    public void setDiskReference(String serverReference) {
        super.setRef(serverReference);
    }    
    
    public String toString(){
        return "DiskRef {[ getDiskRef=" + getDiskReference() + " ]}";
    }
    
}
