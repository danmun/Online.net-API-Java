/**
 * Copyright (c) 2016, danmun
 */
package api.server;

/**
 * 
 * @author danmun
 */
public class RescueCredentials {
    private String login;
    private String password;
    private String protocol;
    private String ip;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
