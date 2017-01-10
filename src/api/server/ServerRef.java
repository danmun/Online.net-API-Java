/**
 * Copyright (c) 2016, danmun
 */
package api.server;

/**
 * 
 * @author danmun
 */
public class ServerRef extends Ref{
    public String getServerReference() {
        return super.getRef();
    }

    public void setServerReference(String serverReference) {
        super.setRef(serverReference);
    }
    
    public String toString(){
        return "ServerRef {[ getServerReference=" + getServerReference() + " ]}";
    }    
}
