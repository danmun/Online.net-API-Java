/**
 * Copyright (c) 2016, danmun
 */
package api.server;

/**
 * 
 * @author danmun
 */
public class Location {
    private String datacenter;
    private String room;
    private String zone;
    private String line;
    private Number rack;
    private String block;
    private Number position;

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Number getRack() {
        return rack;
    }

    public void setRack(Number rack) {
        this.rack = rack;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Number getPosition() {
        return position;
    }

    public void setPosition(Number position) {
        this.position = position;
    }
    
    public String toString(){
        return "Location {[ getDatacenter=" + getDatacenter() + ", getRoom=" + getRoom() + ", "
                + "getZone=" + getZone() + ", getLine=" + getLine() + ", getRack=" + getRack().intValue() + ", "
                + "getBlock=" + getBlock() + ", getPosition=" + getPosition().intValue() + " ]}";
    }
}
