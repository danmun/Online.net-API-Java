/**
 * Copyright (c) 2016, danmun
 */
package api.abuse;

import api.connection.Connection;
import api.server.network.ServerIP;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danmun
 */
public class AbuseReport {
    
    private Number id;
    private Date date; // Date of the abuse 
    private String type; // Type of abuse (example: 'SPAM')
    private String status; // Status of the abuse report 
    private String service; // Name of the service
    private Date sent_date; // Date of the report 
    private String sender; // Reporter's e-mail address 
    private String description; // Full report 
    private Date resolved_date; // Date when the situation was resolved (optional) 
    private String resolver; // Resolver's login (optional) 
    private String answer; // Answer sent to the reporter (optional) 
    private String solution; // Solution for the situation (optional)
    private ServerIP details;
    /**
     * Fetch the specified abuse report.
     * @param id the id of the abuse report to fetch
     * @return the abuse report
     * @throws IllegalStateException
     * @throws IOException 
     */
    public static AbuseReport getAbuseReport(int id) throws IllegalStateException, IOException{
        String url = "/abuse/info/" + id;
        return (AbuseReport) Connection.get(url,AbuseReport.class);
    }
    
    /**
     * Reply to the specified abuse report.
     * @param abuse_id the id of the abuse report to reply to
     * @param answer the response
     * @param solution the proposed solution
     * @return true if successfully replied
     */
    public static boolean reply(int abuse_id, String answer, String solution){
        String url = "/abuse/reply";
        Map<String,String> params = new LinkedHashMap<>();
        params.put("abuse_id", String.valueOf(abuse_id));
        params.put("answer", answer);
        params.put("solution", solution);
        try {
            return (Boolean) Connection.post(url,params,Boolean.class);
        } catch (IOException ex) {
            Logger.getLogger(AbuseReport.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Reply to this abuse report.
     * @param answer the response
     * @param solution the proposed solution
     * @return true if successfully replied
     */
    public boolean reply(String answer, String solution){
        return reply(id.intValue(),answer,solution);
    }
    
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Date getSentDate() {
        return sent_date;
    }

    public void setSentDate(Date sent_date) {
        this.sent_date = sent_date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getResolvedDate() {
        return resolved_date;
    }

    public void setResolvedDate(Date resolved_date) {
        this.resolved_date = resolved_date;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public ServerIP getDetails() {
        return details;
    }

    public void setDetails(ServerIP details) {
        this.details = details;
    }
    
    public String toString(){
        return "AbuseReport {[ getId=" + getId() + ", getDate=" + getDate().toString() + ", getType=" + getType() + ", getStatus=" + getStatus() + ", getService=" + getService() + ", "
                + "getSentDate=" + getSentDate().toString() + ", getSender=" + getSender() + ", getDescription=" + getDescription() + ", getResolvedDate= " + getResolvedDate().toString() + ", "
                + "getResolver=" + getResolver() + ", getAnswer=" + getAnswer() + ", getSolution=" + getSolution() + ", "
                + "getDetails=" + getDetails() + " ]}";
    }
}
