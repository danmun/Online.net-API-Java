/**
 * Copyright (c) 2016, danmun
 */
package api.server.hardware;

/**
 *
 * @author danmun
 */
public abstract class RaidLevel {
    private String raid_level;
    private Number min;
    private Number max;

    public String getRaidLevel() {
        return raid_level;
    }

    public void setRaid_level(String raid_level) {
        this.raid_level = raid_level;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }
    
    
}
