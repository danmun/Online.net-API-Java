/**
 * Copyright (c) 2016, danmun
 */
package api.server;

/**
 *
 * @author danmun
 */
public class FtpBackup {
    private String login;
    private String server;
    private Boolean active;
    private Boolean acl_enabled;
    private Boolean autologin;
    private Number quota_space;
    private Number quota_space_used;
    private Number quota_files;
    private Number quota_files_used;    

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isAclEnabled() {
        return acl_enabled;
    }

    public void setAclEnabled(Boolean acl_enabled) {
        this.acl_enabled = acl_enabled;
    }

    public Boolean isAutologin() {
        return autologin;
    }

    public void setAutologin(Boolean autologin) {
        this.autologin = autologin;
    }

    public Number getSpaceQuota() {
        return quota_space;
    }

    public void setSpaceQuota(Number quota_space) {
        this.quota_space = quota_space;
    }

    public Number getSpaceQuotaUsed() {
        return quota_space_used;
    }

    public void setSpaceQuotaUsed(Number quota_space_used) {
        this.quota_space_used = quota_space_used;
    }

    public Number getFilesQuota() {
        return quota_files;
    }

    public void setFilesQuota(Number quota_files) {
        this.quota_files = quota_files;
    }

    public Number getFilesQuotaUsed() {
        return quota_files_used;
    }

    public void setFilesQuotaUsed(Number quota_files_used) {
        this.quota_files_used = quota_files_used;
    }
    
    public String toString(){
        return "FtpBackup {[ getLogin="+ getLogin() + ", getServer=" + getServer() + ", isActive=" + isActive() + ", "
                + "isAclEnabled=" + isAclEnabled() + ", isAutologin=" + isAutologin() + ", getSpaceQuota=" + getSpaceQuota() + ", "
                + "getSpaceQuotaUsed=" + getSpaceQuotaUsed() + ", getFilesQuota=" + getFilesQuota() + ", getFilesQuotaUsed=" + getFilesQuotaUsed() + "s ]}";
    }
    
}
