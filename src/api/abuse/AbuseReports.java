/**
 * Copyright (c) 2016, danmun
 */
package api.abuse;

import api.connection.Connection;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danmun
 */
public class AbuseReports {
    
    // another way is to do: public class AbuserReports extends ArrayList<AbuseReport>
    // probably cleaner that way
    private List<AbuseReport> reports;
    
    /**
     * Return a list of abuse reports received about the user's services. 
     * The parameters 'ip' and 'domain' can be used to filter the list.
     * @param status Status of the abuse reports: 'pending' or 'resolved' (leave empty or null for both)
     * @param ip 	IP address (can be used to filter list)
     * @param domain 	Domain name (can be used to filter list)
     * @param numberOfReports 	Pagination: Number of results
     * @param max_id Pagination: Maximum abuse id
     * @param since_id Pagination: Minimum abuse id
     * @return an abuse reports object containing a list of abuse reports
     * <DEV NOTE: leaving the ints empty might not work properly>
     */
    public static AbuseReports getAubuseReports(String status, String ip, String domain, Integer numberOfReports, Integer max_id, Integer since_id) throws IOException{
        String url = "/abuse";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("status", (status == null) ? "" : status);
        params.put("ip", (ip == null) ? "" : ip);
        params.put("domain", (domain == null) ? "" : domain);
        params.put("count", (numberOfReports == null) ? "" : String.valueOf(numberOfReports));
        params.put("max_id", (max_id == null) ? "" : String.valueOf(max_id));
        params.put("since_id", (since_id == null) ? "" : String.valueOf(since_id));      
        return (AbuseReports) Connection.get(url,params,AbuseReports.class);
    }
    
    public static AbuseReports getAubuseReports(String ip, String domain, int numberOfReports) throws IOException{
        return getAubuseReports(null,ip,domain,numberOfReports,null,null);
    }
    
    public static AbuseReports getAubuseReportsByDomain(String domain) throws IOException{
        return getAubuseReports(null,null,domain,null,null,null);
    }    
 
    public static AbuseReports getAubuseReportsByIP(String ip) throws IOException{
        return getAubuseReports(null,ip,null,null,null,null);
    }    
    
    public List<AbuseReport> getList(){
        return reports;
    }
}
