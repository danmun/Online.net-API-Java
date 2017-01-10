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
public class RaidController {
    private Number id;
    private String model;
    private Disk[] disks;
    private RaidLevel[] supported_raid_levels;

    public static RaidController raidControllerFromId(int raid_controller_id) throws IllegalStateException, IOException{
        String url = "/server/hardware/raidController/" + raid_controller_id;
        return (RaidController) Connection.get(url, RaidController.class);
    }
    
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Disk[] getDisks() {
        return disks;
    }

    public void setDisks(Disk[] disks) {
        this.disks = disks;
    }

    public RaidLevel[] getSupportedRaidLevels() {
        return supported_raid_levels;
    }

    public void setSupportedRaidLevels(RaidLevel[] supported_raid_levels) {
        this.supported_raid_levels = supported_raid_levels;
    }
}
