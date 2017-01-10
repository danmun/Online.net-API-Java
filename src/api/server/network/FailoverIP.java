/**
 * Copyright (c) 2016, danmun
 */
package api.server.network;

import api.connection.Connection;
import api.server.Contacts;
import api.server.ServerRef;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author danmun
 */
public class FailoverIP {
    private String source;
    private String destination;
    private String mac;
    private String status;
    private ServerRef server;
    private Contacts contacts;

    /**
     * Get the FailoverIPs associated with the account of the API key holder.
     * @return
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static FailoverIP[] getFailoverIPs() throws IllegalStateException, IOException{
        String url = "/server/failover";
        return (FailoverIP[]) Connection.get(url,FailoverIP[].class);
    }    
    
    public static String DEBUG_PRINTS_JSON_RESPONSE(String url,Map<String,String> params,String mode) throws IllegalStateException, IOException{
        return Connection.DEBUG_GET_JSON_RESPONSE(url,params,mode);
    }       
    
    public static boolean deleteMac(String failover) throws IOException{
        String url = "/server/failover/deleteMac";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("address", failover);
        return (Boolean) Connection.post(url,params,Boolean.class);
    }
    
    /**
     * Delete the MAC address of this failover IP.
     * @return true iff the MAC address was deleted successfully
     * @throws IOException 
     */
    public boolean deleteMac() throws IOException{
        return deleteMac(this.source);
    }

    /**
     * Duplicates a failover IP's MAC address for use on another failover IP (that has the same destination IP).
     * @param address
     * @param target
     * @return  returns true on success (If a MAC address already exists for the target failover IP, returns an error with the existing MAC address
     * @throws java.io.IOException
     */
    public static String duplicateMac(String address, String target) throws IOException{
        String url = "/server/failover/duplicateMac";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("address", address);    
        params.put("target", target);    
        return (String) Connection.post(url,params,String.class);
    }    
    
    /**
     * Duplicates this failover IP's MAC address for use on another failover IP (that has the same destination IP).
     * @param target
     * @return  returns true on success (If a MAC address already exists for the target failover IP, returns an error with the existing MAC address
     * @throws java.io.IOException
     */    
    public String duplicateMac(String target) throws IOException{
        return duplicateMac(this.source,target);
    }
    
   /**
     * Edit a ServerIP address.
     * Note: failover IPs that belong to a MAC group can only change destination as a group. To edit multiple IPs, please use a comma-separated list.
     * To edit multiple IPs, please use a comma-separated list, e.g. address = "0.0.0.0,1.1.1.1,1.1.3.4" etc..
     * @param failover the address(es) to edit
     * @param destination the new destination to set for the specified failover ip (leave empty to null-route failover IP)
     * @return true if the edit was successful, false otherwise
     * @throws java.io.IOException
     */
    public static boolean edit(String failover, String destination) throws IOException{
        String url = "/server/failover/edit";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("source", failover);
        params.put("destination", destination);
        return (Boolean) Connection.post(url,params,Boolean.class);
    }
    
    /**
     * Edit this ServerIP address.
     * You can set the reverse of a ServerIP. 
     * Note: failover IPs that belong to a MAC group can only change destination as a group. 
     * To edit multiple IPs, please use a comma-separated list.  
     * @param destination the new destination to set for the specified failover ip (leave empty to null-route failover IP)
     * @return true if the edit was successful, false otherwise
     * @throws IOException 
     */
    public boolean edit(String destination) throws IOException{
        boolean edited = edit(this.source,destination);
        if(edited){
            this.destination = destination;
        }
        return edited;
    }   
    
    /**
     * Generates a MAC address for a failover IP, returns the MAC address on success. 
     * (If a MAC address already exists for this failover IP, returns an error with the existing MAC address.)
     * @param address the address of the failover ip
     * @param type  Type of prefix for the MAC address: 'vmware' (00:50:56), 'xen' (00:16:3e), or 'kvm' (52:54:00)
     * @return
     * @throws IOException 
     */
    public static String generateMac(String address, String type) throws IOException{
        String url = "/server/failover/generateMac";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("address", address);
        params.put("type", type);
        return (String) Connection.post(url,params,String.class);
    }
    
    /**
     * Generates a MAC address for a failover IP, returns the MAC address on success. 
     * (If a MAC address already exists for this failover IP, returns an error with the existing MAC address.)
     * @param type  Type of prefix for the MAC address: 'vmware' (00:50:56), 'xen' (00:16:3e), or 'kvm' (52:54:00)
     * @return
     * @throws IOException 
     */
    public String generateMac(String type) throws IOException{
        return generateMac(this.source,type);
    }
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServerRef getServerRef() {
        return server;
    }

    public void setServer(ServerRef server) {
        this.server = server;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
    
    public String toString(){
        return "FailoverIP {[ getSource=" + getSource() + ", getDestination=" + getDestination() + ", "
                + "getMac=" + getMac() + ", getStatus=" + getStatus() + ", getServerRef=" + getServerRef() + ", "
                + "getContacts=" + getContacts() + " ]}";
    }
}
