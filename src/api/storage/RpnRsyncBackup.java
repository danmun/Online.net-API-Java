/**
 * Copyright (c) 2016, danmun
 */
package api.storage;

import api.connection.Connection;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author danmun
 */
public class RpnRsyncBackup {
    private String name;
    private String address;
    private String login;
    private String password;
    private boolean active;
    @SerializedName("quota_space")
    private Number spaceQuota;
    @SerializedName("quota_space_used")
    private Number spaceQuotaUsed;
    @SerializedName("quota_files_used")
    private Number filesQuotaUsed;

    /**
     * Updates password of an RPN Rsync target, returns true on success.
     * @deprecated 
     * NOTE: for higher security, this method might be changed later to use char[] for handling password
     * @param name name of the rpn rsync target
     * @param password the new password to set
     * @return true on success
     */
    public static boolean updatePassword(String name, String password) throws IOException{
        String url = "/storage/rpn/rsync/edit";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("name",name);      
        params.put("password",password);      
        return (Boolean) Connection.post(url, params, Boolean.class);
    }
    
    /**
     * Update password of this RPN Rsync target.
     * @deprecated 
     * NOTE: for higher security, this method might be changed later to use char[] for handling password
     * @param password the new password to set
     * @return true on success
     */
    public boolean updatePassword(String password) throws IOException{
        return updatePassword(this.name,password);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Number getSpaceQuota() {
        return spaceQuota;
    }

    public void setSpaceQuota(Number spaceQuota) {
        this.spaceQuota = spaceQuota;
    }

    public Number getSpaceQuotaUsed() {
        return spaceQuotaUsed;
    }

    public void setSpaceQuotaUsed(Number spaceQuotaUsed) {
        this.spaceQuotaUsed = spaceQuotaUsed;
    }

    public Number getFilesQuotaUsed() {
        return filesQuotaUsed;
    }

    public void setFilesQuotaUsed(Number filesQuotaUsed) {
        this.filesQuotaUsed = filesQuotaUsed;
    }
    
    
    
}
