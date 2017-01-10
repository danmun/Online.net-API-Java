/**
 * Copyright (c) 2016, danmun
 */
package api.network;

import api.connection.Connection;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author danmun
 */
public class DdosAlert {
    
    private Number id;
    private String target;
    private Date start;
    private Date end;
    private String mitigation;
    private String type;
    private Number max_pps;
    private Number max_bps;
    private Timeline timeline;
    
    /**
     * Return information on a DDoS alert that affects or affected the user's servers.
     * @param alert_id
     * @return
     * @throws IOException 
     */
    public static DdosAlert getDdosAlert(int alert_id) throws IOException{
        String url = "/network/ddos/info/" + alert_id;
        return (DdosAlert) Connection.get(url,DdosAlert.class);
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getMitigation() {
        return mitigation;
    }

    public void setMitigation(String mitigation) {
        this.mitigation = mitigation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getMax_pps() {
        return max_pps;
    }

    public void setMax_pps(Number max_pps) {
        this.max_pps = max_pps;
    }

    public Number getMax_bps() {
        return max_bps;
    }

    public void setMax_bps(Number max_bps) {
        this.max_bps = max_bps;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
    
    
    
    
    public class Timeline{
        private Number timestamp;
        private Number pps;
        private Number bps;

        public Number getTimestamp() {
            return timestamp;
        }

        protected void setTimestamp(Number timestamp) {
            this.timestamp = timestamp;
        }

        public Number getPps() {
            return pps;
        }

        protected void setPps(Number pps) {
            this.pps = pps;
        }

        public Number getBps() {
            return bps;
        }

        protected void setBps(Number bps) {
            this.bps = bps;
        }
        
        
    }
}
