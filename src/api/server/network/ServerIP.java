/**
 * Copyright (c) 2016, danmun
 */
package api.server.network;

import api.connection.Connection;
import api.server.ServerRef;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author danmun
 */
public class ServerIP {
    private String address;
    private String type;
    private String reverse;
    private String mac;
    private String destination;
    private String status;
    private String switch_port_state;
    private ServerRef server;
    

    public static ServerIP serverIpFromAddress(String address) throws IllegalStateException, IOException{
        String url = "/server/ip/" + address;
        return (ServerIP) Connection.get(url, ServerIP.class);
    }
    
    public static String DEBUG_PRINTS_JSON_RESPONSE(String address) throws IllegalStateException, IOException{
        String url = "/server/ip/" + address;
        return Connection.DEBUG_GET_JSON_RESPONSE(url);
    }      
    
    /**
     * Edit a ServerIP address.
     * You can set the reverse of a ServerIP. 
     * Note: failover IPs that belong to a MAC group can only change destination as a group. 
     * To edit multiple IPs, please use a comma-separated list, e.g. address = "0.0.0.0,1.1.1.1,1.1.3.4" etc..
     * @param address the address(es) to edit
     * @param reverse the new reverse to set (use "false" to reset to default)
     * @return true if the edit was successful, false otherwise
     * @throws java.io.IOException
     */
    public static boolean edit(String address, String reverse) throws IOException{
        String url = "/server/ip/edit";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("address", address);
        params.put("reverse", reverse);
        return (Boolean) Connection.post(url,params,Boolean.class);
    }
    
    /**
     * Edit this ServerIP address.
     * You can set the reverse of a ServerIP. 
     * Note: failover IPs that belong to a MAC group can only change destination as a group.  
     * @param reverse the new reverse to set (use "false" to reset to default)
     * @return true if the edit was successful, false otherwise
     * @throws IOException 
     */
    public boolean edit(String reverse) throws IOException{
        boolean edited = edit(this.address,reverse);
        if(edited){
            this.reverse = reverse;
        }
        return edited;
    }
    
    
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReverse() {
        return reverse;
    }

    public void setReverse(String reverse) {
        this.reverse = reverse;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSwitch_port_state() {
        return switch_port_state;
    }

    public void setSwitch_port_state(String switch_port_state) {
        this.switch_port_state = switch_port_state;
    }

    public ServerRef getServerRef() {
        return server;
    }

    public void setServer(ServerRef server) {
        this.server = server;
    }
    
    public String toString(){
        return "ServerIP {[ getAddress="+ getAddress() + ", getType=" + getType() + ", getReverse=" + getReverse() + ", "
                + "getMac=" + getMac() + ", getDestination=" + getDestination() + ", getStatus=" + getStatus() + ", "
                + "getSwitch_port_state=" + getSwitch_port_state() + ", getServerRef=" + getServerRef() + " ]}";
    }
}
