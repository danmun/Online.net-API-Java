/**
 * Copyright (c) 2016, danmun
 */
package api.server.network;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

/**
 * 
 * @author danmun
 */
public class Network {
    @SerializedName("ip")
    private String[] publicIp;
    @SerializedName("private")
    private String[] privateIp;
    @SerializedName("ipfo")
    private String[] failoverIp;

    public String[] getPublicIPs() {
        return publicIp;
    }

    public void setPublicIPs(String[] publicIp) {
        this.publicIp = publicIp;
    }

    public String[] getPrivateIPs() {
        return privateIp;
    }

    public void setPrivateIPs(String[] privateIp) {
        this.privateIp = privateIp;
    }

    public String[] getFailoverIPs() {
        return failoverIp;
    }

    public void setFailoverIPs(String[] ipfo) {
        this.failoverIp = ipfo;
    }
    
    public String toString(){
        return "Network {[ getPublicIPs=" + Arrays.toString(getPublicIPs()) + ", getPrivateIPs=" + Arrays.toString(getPrivateIPs()) + ", "
                + "getFailoverIPs=" + Arrays.toString(getFailoverIPs()) + " ]}";
    }
}
