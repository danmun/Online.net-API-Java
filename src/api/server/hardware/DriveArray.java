/**
 * Copyright (c) 2016, danmun
 */
package api.server.hardware;

import java.util.Arrays;

/**
 * 
 * @author danmun
 */
public class DriveArray {
    private String raid_level;
    private Disk[] disks;
    private RaidControllerRef raid_controller;

    public String getRaid_level() {
        return raid_level;
    }

    public void setRaid_level(String raid_level) {
        this.raid_level = raid_level;
    }

    public Disk[] getDisks() {
        return disks;
    }

    public void setDisks(Disk[] disks) {
        this.disks = disks;
    }

    public RaidControllerRef getRaid_controller() {
        return raid_controller;
    }

    public void setRaid_controller(RaidControllerRef raid_controller) {
        this.raid_controller = raid_controller;
    }
    
    public String toString(){
        String info = (raid_level == null) ? "null" : "getRaid_level=" + getRaid_level() + ", "
                                                    + "getDisks=" + Arrays.toString(getDisks()) + ", "
                                                    + "getRaid_controller=" + getRaid_controller();
        return "DriveArray {[ " + info + " ]}";
    }
}
