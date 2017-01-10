/**
 * Copyright (c) 2016, danmun
 */
package api.server;

import java.util.Date;

/**
 * 
 * @author danmun
 */
public class OperatingSystem {
    // name and version are always available
    private String name;
    private String version;
    // these are only available if requested via /server/operatingSystems/serverid
    private String type;
    private String arch;
    private Date release;
    private Date end_of_life;
    
    // when this OS is the result of Server info, it is included as the installed one, 
    // and thus some fields will not be available
    // when this OS is the result of getAvailableOperatingSystems() most of the fields are available
    // see getters for additional info
    private final transient String NOT_AVAILABLE = "not available";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return (type != null) ? type : NOT_AVAILABLE;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the architecture of the OS.
     * e.g. 32 bits
     * @return string showing the architecture
     */
    public String getArchitecture() {
        return (arch != null) ? arch : NOT_AVAILABLE;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public Date getReleaseDate() {
        return release;
    }

    public void setReleaseDate(Date release) {
        this.release = release;
    }

    /**
     * Get the projected end-of-life date.
     * NOTE that not all operating systems have this date,
     * so for those systems, a null will be returned
     * @return end-of-life date or null if the OS doesn't have this characteristic
     */
    public Date getEndOfLife() {
        return end_of_life;
    }

    public void setEndOfLife(Date end_of_life) {
        this.end_of_life = end_of_life;
    }
    
    
    public String toString(){
        return "OperatingSystem {[ getName=" + getName() + ", getVersion=" + getVersion() + ", "
                + "getType=" + getType() + ", getArch=" + getArchitecture() + ", getReleaseDate=" + getReleaseDate().toString() + ", "
                + "getEndOfLife=" + getEndOfLife().toString() + " ]}";
    }
}
