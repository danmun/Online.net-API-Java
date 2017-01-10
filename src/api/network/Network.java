/**
 * Copyright (c) 2016, danmun
 */
package api.network;

import api.connection.Connection;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author danmun
 */
public class Network {
    public static SecondaryDnsServerEntry[] getSecondaryDnsEntries() throws IOException{
        String url = "/network/secondaryDns";
        return (SecondaryDnsServerEntry[]) Connection.get(url,SecondaryDnsServerEntry[].class);
    }
    
    public static String addSecondaryDnsEntry(String domain, String ip) throws IOException{
        String url = "/network/secondaryDns";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("domain",domain);
        params.put("ip",ip);        
        return (String) Connection.post(url,params,String.class);
    }
    
    public static void deleteSecondaryDnsEntry(String domain) throws IOException{
        String url = "/network/secondaryDns/" + domain;   
        Connection.delete(url);
    }    
}
