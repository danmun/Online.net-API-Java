/**
 * Copyright (c) 2016, danmun
 */
package api.server.bmc;

import java.util.Date;

/**
 *
 * @author danmun
 */
public class BmcSessionDetails {
    private String url;
    private String login;
    private String password;
    private Date expiration;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    
    public String toString(){
        return "BmcSession {[ getUrl=" + getUrl() + ", getLogin=" + getLogin() + ", "
                + "getPassword=" + getPassword() + ", getExpiration=" + getExpiration().toString() + " ]}";
    }
}
