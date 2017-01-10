/**
 * Copyright (c) 2016, danmun
 */
package api.network;

/**
 *
 * @author danmun
 */
public class SecondaryDnsServerEntry {
    private String domain;
    private String ip;
    private String seconday_dns_server_ip;
    private String secondary_dns_server_hostname;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSeconday_dns_server_ip() {
        return seconday_dns_server_ip;
    }

    public void setSeconday_dns_server_ip(String seconday_dns_server_ip) {
        this.seconday_dns_server_ip = seconday_dns_server_ip;
    }

    public String getSecondary_dns_server_hostname() {
        return secondary_dns_server_hostname;
    }

    public void setSecondary_dns_server_hostname(String secondary_dns_server_hostname) {
        this.secondary_dns_server_hostname = secondary_dns_server_hostname;
    }
    
    
}
