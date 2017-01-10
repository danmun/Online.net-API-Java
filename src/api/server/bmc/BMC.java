/**
 * Copyright (c) 2016, danmun
 */
package api.server.bmc;

/**
 * 
 * @author danmun
 */
public class BMC {
    private String session_key;

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
    
    public String toString(){
        return "BMC {[ getSession_key=" + getSession_key() + " ]}";
    }
}
