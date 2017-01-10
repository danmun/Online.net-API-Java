/**
 * Copyright (c) 2016, danmun
 */
package api.server.hardware;

import api.connection.Connection;
import java.io.IOException;

/**
 * 
 * @author danmun
 */
public class Disk {
    private Number id;
    private String connector;
    private String type;
    private Number capacity;
    private RaidControllerRef raid_controller;

    public static Disk diskFromId(int id) throws IllegalStateException, IOException{
        String url = "/server/hardware/disk/" + id;
        return (Disk) Connection.get(url, Disk.class);
    }
    
    public static String DEBUG_PRINTS_JSON_RESPONSE(int id) throws IllegalStateException, IOException{
        String url = "/server/hardware/disk/" + id;
        return Connection.DEBUG_GET_JSON_RESPONSE(url);
    }         
    
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getCapacity() {
        return capacity;
    }

    public void setCapacity(Number capacity) {
        this.capacity = capacity;
    }

    public RaidControllerRef getRaid_controller() {
        return raid_controller;
    }

    public void setRaid_controller(RaidControllerRef raid_controller) {
        this.raid_controller = raid_controller;
    }
    
    public String toString(){
        String info = (getId() == null) ? "null" : "getId=" + ((getId() != null) ? getId().intValue() : "-1") + ", "
                                                    + "getConnector=" + getConnector() + ", getType=" + getType() + ", "
                                                    + "getCapacity=" + getCapacity() + ", getRaid_controller=" + getRaid_controller(); 
        return "Disk {[ " + info + " ]}";
    }
}
