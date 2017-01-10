/**
 * Copyright (c) 2016, danmun
 */
package api.storage;

import api.connection.Connection;
import api.server.ServerRef;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author danmun
 */
public class RpnSan {
    private String server;
    private String iqn_suffix;
    private Number size;
    private String status;
    private ServerRef[] allowed_servers;

    /**
     * Allow a server to use a given RPN SAN, returns true on success
     * @param iqn_suffix
     * @param id
     * @return 
     */
    public static boolean addServer(String iqn_suffix, int id) throws IOException{
        String url = "/storage/rpn/san/addServer";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("iqn_suffix",iqn_suffix);      
        params.put("id",String.valueOf(id));           
        return (Boolean) Connection.post(url,params,Boolean.class);
    }
    
    /**
     * Allow a server to use this RPN SAN, returns true on success
     * NOTE: if server is added via this method, the allowed_server field of this
     * object will not be an accurate representation of this modified RPN SAN
     * (an additional call would need to be made to get an up-to-date RpnSan object, 
     * with the up-to-date allowed_servers field containing the newly added server)
     * @param id
     * @return
     * @throws IOException 
     */
    public boolean addServer(int id) throws IOException{
        return addServer(this.iqn_suffix,id);
    }
    
    /**
     * Disallow a server from using the given RPN SAN, returns true on success
     * @param iqn_suffix
     * @param id
     * @return 
     */
    public static boolean removeServer(String iqn_suffix, int id) throws IOException{
        String url = "/storage/rpn/san/removeServer";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("iqn_suffix",iqn_suffix);      
        params.put("id",String.valueOf(id));           
        return (Boolean) Connection.post(url,params,Boolean.class);
    }
    
    /**
     * Disallow a server from using this RPN SAN, returns true on success
     * NOTE: if server is removed via this method, the allowed_server field of this
     * object will not be an up-to-date representation of this modified RPN SAN
     * (an additional call would need to be made to get an up-to-date RpnSan object, 
     * with the up-to-date allowed_servers field no longer containing the removed server)
     * @param id
     * @return
     * @throws IOException 
     */
    public boolean removeServer(int id) throws IOException{
        return addServer(this.iqn_suffix,id);
    }    
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getIqn_suffix() {
        return iqn_suffix;
    }

    public void setIqn_suffix(String iqn_suffix) {
        this.iqn_suffix = iqn_suffix;
    }

    public Number getSize() {
        return size;
    }

    public void setSize(Number size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServerRef[] getAllowed_servers() {
        return allowed_servers;
    }

    public void setAllowed_servers(ServerRef[] allowed_servers) {
        this.allowed_servers = allowed_servers;
    }
    
    
}
